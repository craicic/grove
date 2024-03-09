import gc
import math
import re

import pandas as pd
import psycopg2 as ps
from pandas import DataFrame


def config():
    pd.set_option("display.max_columns", None)
    pd.set_option("display.width", None)
    pd.set_option("display.max_colwidth", 20)

    pd.set_option("mode.copy_on_write", True)
    # if more than 50 rows, display only 20 of them
    pd.set_option("display.max_rows", 50)
    pd.set_option("display.min_rows", 40)


# edits the header
def replace_header(dataframe):
    header = ["code", "title", "unknown1", "nature", "location", "code_stat", "wear_condition",
              "general_state", "date_of_purchase", "price", "publisher", "seller", "unknown2",
              "range", "players", "author2", "author1", "illustrator"]
    dataframe.columns = header
    return dataframe


def rewrite(name):
    if isinstance(name, float) and math.isnan(name):
        return "no_value"

    name = name.title().strip()

    multi = re.split(r"(&)|(et)", name, flags=re.IGNORECASE)
    if len(multi) > 1:
        return "multiple_values"

    if name == "Sans Objet":
        return "no_value"

    name = name.replace(".", " ")

    name_parts = re.split(r"\s+", name)
    # if len(name_parts) > 2:
    #     return "ignored_value"
    name_parts = [j.strip() for j in name_parts]
    return " ".join(str(part) for part in name_parts)


def rewrite_age(range):
    if isinstance(range, float) and math.isnan(range):
        return "no_value"

    if "MOIS" in range:
        ages = re.findall(r"\d+", range)
        if len(ages) == 1:
            return ages[0] + "M"
        else:
            return "ignored_value"

    ages = re.findall(r"\d+", range)

    if len(ages) >= 1:
        return ages[0] + "A"

    return "ignored_value"


df1 = pd.read_csv("input.csv", sep=";", encoding="UTF-8")
config()
df1 = replace_header(df1)

# only keep nature GRAND JEU
# df1 = df1.loc[df1.nature == 'GRAND JEU']

# removes two apparently useless columns
df1.drop(columns=['unknown1', 'unknown2', "price", "seller"], inplace=True)

# artist's name cleaning and filtering
a2 = df1.author2.apply(rewrite)
df_a2 = (a2
         .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
         .to_frame("coauthor"))

a1 = df1.author1.apply(rewrite)
df_a1 = (a1
         .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
         .to_frame("author"))

ill = df1.illustrator.apply(rewrite)
df_ill = (ill
          .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
          .to_frame("illustrator"))

df1.drop(columns=["author1", "author2", "illustrator"], inplace=True)
df2 = pd.concat([df1, df_a1, df_a2, df_ill], axis=1)

del [df1, df_a1, df_a2, df_ill, a1, a2, ill]
gc.collect()

# age operations
age = df2.range.apply(rewrite_age)
age = age.str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
df_age = pd.DataFrame({"age_min": age})
df_age.info()

df3 = pd.concat([df2, df_age], axis=1)
df3.drop(columns="range", inplace=True)

# number of players
df_player: DataFrame = (df3.players
                        .str.replace(r"(À)|(A)", "-", regex=True)
                        .str.replace(r"(ET)\s+(PLUS)", "-0", regex=True)
                        .str.replace(r"[^0-9+-]", "", regex=True)
                        .to_frame("player"))

df_player.where(~df_player.player.str.contains(r"^\d+$", regex=True, na=True),
                df_player.player + "-" + df_player.player, axis=0, inplace=True)

# split each row of the series 'player' on "-" or "+" char
df_nb_players = df_player.player.str.rsplit("-", expand=True).fillna("0")
df_nb_players.columns = ["nb_p_min", "nb_p_max"]

df4 = pd.concat([df3, df_nb_players], axis=1)
df4.drop(columns=["players"], inplace=True)

del [df2, df3, age, df_age, df_player, df_nb_players]
gc.collect()

df4.rename(columns={"title": "old_title"}, inplace=True)

# title processing
df_title: DataFrame = (df4.old_title
                       .str.strip()
                       .str.replace(r"\s+", " ", regex=True)
                       .str.title()
                       .to_frame("title"))

df_title.where(~df_title.title.str.contains(r",\S", regex=True, na=True),
               df_title.title.str.replace(",", ", ").to_frame("title"), inplace=True)

df5 = pd.concat([df4, df_title], axis=1)
df5.drop(columns=["old_title"], inplace=True)
df5.nature = df5.nature.str.lower().str.strip().fillna("None")
df5.location = df5.location.str.title().str.strip().fillna("None")
df5.code_stat = df5.code_stat.str.lower().str.strip().fillna("None")
df5.wear_condition = df5.wear_condition.str.title().str.strip().fillna("None")
df5.general_state = df5.general_state.str.title().str.strip().fillna("None")
df5.date_of_purchase = pd.to_datetime(df5.date_of_purchase, dayfirst=True)
df5.publisher = df5.publisher.str.title().str.strip().fillna("None")

# Both author and illustrator
# df_author1 = df.author1.dropna()
# df_author2 = df.author2.dropna()
# authors = pd.concat([df_author1, df_author2])
# authors = authors.drop_duplicates()
#
# illustrators = df.illustrator.dropna()
# illustrators = illustrators.drop_duplicates()
#
# both_auth_ill = pd.Series(list(set(illustrators).intersection(set(authors))))
# only_auth = pd.Series(list(set(authors).difference(set(both_auth_ill))))
# only_ill = pd.Series(list(set(illustrators).difference(set(both_auth_ill))))

# New dataframe with specific columns
df_games = df5[["title", "nb_p_min", "nb_p_max", "age_min"]].drop_duplicates().drop_duplicates(subset="title",
                                                                                               keep="first")

df_publisher = df5[["code", "publisher"]]

df_publisher.to_csv("output/publisher.csv", sep=";")
df_games.to_csv("output/games.csv", sep=";")
df5.to_csv("output/full_frame.csv", sep=";")

pg_usr = ""
pg_pwd = ""

f = open("../grove-service/src/main/resources/secrets.properties")
lines = f.readlines()
for line in lines:
    if line.startswith("spring.datasource.username"):
        pg_usr = line.split("=")[1].strip()
    if line.startswith("spring.datasource.password"):
        pg_pwd = line.split("=")[1].strip()

conn = ps.connect("dbname=game-library-dev-db user=" + pg_usr + " password=" + pg_pwd)
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM game_sequence;")

game_id = int(cursor.fetchone()[0])

print(str(game_id))

cursor.execute("""
CREATE OR REPLACE FUNCTION insert_game(g_id INT, g_title TEXT, g_lower_case_title TEXT, g_min_age INT, g_min_month INT,
    g_max_age INT, g_min_number_of_player INT, g_max_number_of_player INT, g_nature INT)
RETURNS TEXT LANGUAGE plpgsql AS
$$
DECLARE 
    v_operation bool := false;
BEGIN
   WITH ins AS (
        INSERT INTO game(id, title, lower_case_title, min_age, min_month, max_age, min_number_of_player, max_number_of_player, nature)
        VALUES (g_id , g_title, g_lower_case_title, g_min_age, g_min_month, g_max_age, g_min_number_of_player, g_max_number_of_player, g_nature)
        ON CONFLICT(lower_case_title) DO NOTHING
        RETURNING 'INSERTED'
    )
    SELECT EXISTS(SELECT * FROM ins) INTO v_operation;

    RETURN v_operation;
END
$$;
""")
conn.commit()
cursor.close()

cursor = conn.cursor()
min_month = 0
min_year = 0
for index, row in df_games.iterrows():
    game_id += 1
    if row["age_min"] == "None":
        min_month = 0
        min_year = 0
    elif row["age_min"].endswith("M"):
        min_month = int(row["age_min"].replace("M", ""))
        min_year = 0
    else:
        min_month = 0
        min_year = int(row["age_min"].replace("A", ""))

    cursor.execute(
        "SELECT insert_game(%s, %s, %s, %s, %s, %s, %s, %s, %s);",
        (game_id, row["title"], row["title"].lower(), min_year, min_month, 0, row["nb_p_min"], row["nb_p_max"], 0))

    insert_result: str = ""
    for r in cursor.fetchone():
        if str(r).startswith("true"):
            insert_result = str(r)
        else:
            print(r)

    if insert_result.startswith("true"):
        print("Successfully inserted : " + row["title"] + " of id=" + str(game_id))

    if insert_result == "":
        print("Ignored entry : " + row["title"] + " of id=" + str(game_id))
        game_id += -1

cursor.execute("SELECT setval('game_sequence', " + str(game_id) + ", true);")
conn.commit()
cursor.close()
conn.close()
