import datetime
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


def rewrite_age(age_range):
    if isinstance(age_range, float) and math.isnan(age_range):
        return "no_value"

    if "MOIS" in age_range:
        ages = re.findall(r"\d+", age_range)
        if len(ages) == 1:
            return ages[0] + "M"
        else:
            return "ignored_value"

    ages = re.findall(r"\d+", age_range)

    if len(ages) >= 1:
        return ages[0] + "A"

    return "ignored_value"


def wear_str_to_int(wear_condition):
    if wear_condition == "BON":
        return 0
    elif wear_condition == "USAGÉ":
        return 1
    else:
        return 2


def state_str_to_int(state):
    if state == "ACTIF":
        return 0
    elif state == "HORS SERVICE":
        return 1
    elif state == "PIÈCE MANQUANTE":
        return 2
    elif state == "EN RÉPARATION":
        return 3
    elif state == "PRET A JOUER":
        return 4
    elif state == "A PLASTIFIER":
        return 5
    elif state == "PERDU":
        return 6
    elif state == "NON RESTITUÉ":
        return 7
    elif state == "RÉASSORT":
        return 8
    elif state == "DON À UNE ASSOCIATION":
        return 9
    elif state == "RÉSERVÉ AIR DE JEUX":
        return 10
    elif state == "CASSÉ":
        return 11
    else:
        return 12


#######################################################################################################################
# IMPORT CSS
#######################################################################################################################
df1 = pd.read_csv("input.csv", sep=";", encoding="UTF-8")
config()
df1 = replace_header(df1)

# only keep nature GRAND JEU
# df1 = df1.loc[df1.nature == 'GRAND JEU']


# removes two apparently useless columns
df1.drop(columns=['unknown1', 'unknown2', "price", "seller"], inplace=True)

#######################################################################################################################
# ARTIST'S NAME CLEANING AND FILTERING
#######################################################################################################################
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

#######################################################################################################################
# AGE OPERATIONS
#######################################################################################################################
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

#######################################################################################################################
# TITLE PROCESSING
#######################################################################################################################
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

df5.wear_condition = df5.wear_condition.str.strip().fillna("None")
# wear condition string to enum
df5.wear_condition = df5.wear_condition.apply(wear_str_to_int)

df5.general_state = df5.general_state.str.strip().fillna("None")
# general state string to enum
df5.general_state = df5.general_state.apply(state_str_to_int)

df5.date_of_purchase = pd.to_datetime(df5.date_of_purchase, dayfirst=True)
df5.date_of_purchase = df5.date_of_purchase.dt.date
df5.publisher = df5.publisher.str.title().str.strip().fillna("None")

# both_auth_ill = pd.Series(list(set(illustrators).intersection(set(authors))))
# only_auth = pd.Series(list(set(authors).difference(set(both_auth_ill))))
# only_ill = pd.Series(list(set(illustrators).difference(set(both_auth_ill))))

# #######################################################################################################################
# NEW DATAFRAME WITH SPECIFIC COLUMNS
#######################################################################################################################
df_games = df5[["title", "nb_p_min", "nb_p_max", "age_min"]].drop_duplicates().drop_duplicates(subset="title",
                                                                                               keep="first")
df_copy = df5[["code", "title", "location", "wear_condition", "general_state", "date_of_purchase"]]
df_publisher = df5[["code", "publisher"]]

df_games.to_csv("output/games.csv", sep=";")
df_copy.to_csv("output/copy.csv", sep=";")
df_publisher.to_csv("output/publisher.csv", sep=";")
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

#######################################################################################################################
# PERSISTENCE OPERATIONS
#######################################################################################################################
conn = ps.connect("dbname=game-library-dev-db user=" + pg_usr + " password=" + pg_pwd)

#######################################################################################################################
# INSERTING NEW GAME COPY
#######################################################################################################################
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM game_sequence;")
game_id = int(cursor.fetchone()[0])

cursor.execute("""
CREATE OR REPLACE FUNCTION public.insert_game(g_id INT, g_title TEXT, g_lower_case_title TEXT, g_min_age INT, g_min_month INT,
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
        "SELECT public.insert_game(%s, %s, %s, %s, %s, %s, %s, %s, %s);",
        (game_id, row["title"], row["title"].lower(), min_year, min_month, 0, row["nb_p_min"], row["nb_p_max"], 0))

    wasInserted: bool = False
    for r in cursor.fetchone():
        if str(r) == "true":
            wasInserted = True
        else:
            wasInserted = False

    if wasInserted:
        print("Successfully inserted : " + row["title"] + " of id=" + str(game_id))

    else:
        print("Ignored entry : " + row["title"] + " of id=" + str(game_id))
        game_id += -1

cursor.execute("SELECT setval('game_sequence', " + str(game_id) + ", true);")
conn.commit()
cursor.close()

#######################################################################################################################
# INSERTING NEW GAME
#######################################################################################################################
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM game_copy_sequence;")
copy_id = int(cursor.fetchone()[0])

cursor.execute("""
CREATE OR REPLACE FUNCTION public.insert_copy(c_id INT, c_code TEXT,c_fk_game INT, c_date_of_purchase DATE,
                                        c_general_state INT, c_location TEXT, c_wear_condition INT)
RETURNS TEXT LANGUAGE plpgsql AS
$$
DECLARE 
    v_operation bool := false;
BEGIN
   WITH ins AS (
        INSERT INTO game_copy(id, object_code, fk_game, date_of_purchase, date_of_registration, general_state, location, wear_condition, is_available_for_loan)
        VALUES (c_id, c_code, c_fk_game, c_date_of_purchase, CURRENT_DATE, c_general_state, c_location, c_wear_condition, true)
        ON CONFLICT(object_code) DO NOTHING
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
for index, row in df_copy.iterrows():
    copy_id += 1

    cursor.execute("SELECT id FROM game WHERE title LIKE %s", (row["title"],))
    record = cursor.fetchone()
    fk_game = record[0]

    cursor.execute("SELECT public.insert_copy(%s,%s,%s,%s,%s,%s,%s);",
                   (copy_id, str(row["code"]), fk_game, row["date_of_purchase"], row["general_state"], row["location"],
                    row["wear_condition"]))

    wasInserted: bool = False
    for r in cursor.fetchone():
        if str(r) == "true":
            wasInserted = True
        else:
            wasInserted = False

    if wasInserted:
        print("Successfully inserted : " + str(row["code"]) + " of id=" + str(copy_id))

    else:
        print("Ignored entry : " + str(row["code"]) + " of id=" + str(copy_id))
        copy_id += -1

cursor.execute("SELECT setval('game_copy_sequence', " + str(copy_id) + ", true);")
conn.commit()
cursor.close()
conn.close()
