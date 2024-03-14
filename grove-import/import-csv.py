import gc
import math
import re

import numpy as np
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


def nature_str_to_int(nature):
    if nature == "GRAND JEU":
        return 3
    if nature == "JEU / JOUET":
        return 2
    return 0


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
df5.code_stat = df5.code_stat.str.lower().str.strip().fillna("None")
df5.nature = df5.nature.str.strip().fillna("None").apply(nature_str_to_int)

df5.location = df5.location.str.title().str.strip().fillna("None")

df5.wear_condition = df5.wear_condition.str.strip().fillna("None")
# wear condition string to enum
df5.wear_condition = df5.wear_condition.apply(wear_str_to_int)

df5.general_state = df5.general_state.str.strip().fillna("None")
# general state string to enum
df5.general_state = df5.general_state.apply(state_str_to_int)

df5.date_of_purchase = pd.to_datetime(df5.date_of_purchase, dayfirst=True)
df5.date_of_purchase = df5.date_of_purchase.dt.date
df5.publisher = df5.publisher.str.title().str.strip().fillna("None")

# #####################################################################################################################
# ARTISTS OPERATIONS
#######################################################################################################################
df_authors = pd.concat([df5.author, df5.coauthor]).to_frame("name").replace("None", np.NAN).dropna().drop_duplicates()
df_illustrators = df5["illustrator"].to_frame("name").replace("None", np.NAN).dropna().drop_duplicates()

auth_ill = pd.Series(list(set(df_illustrators.name).intersection(set(df_authors.name))))
exclusively_author = pd.Series(list(set(df_authors.name).difference(set(auth_ill))))
exclusively_illustrator = pd.Series(list(set(df_illustrators.name).difference(set(auth_ill))))

df_both_author_and_illustrator = auth_ill.to_frame("name")
df_both_author_and_illustrator.insert(1, "role", 3)
df_exclusively_author = exclusively_author.to_frame("name")
df_exclusively_author.insert(1, "role", 0)
df_exclusively_illustrator = exclusively_illustrator.to_frame("name")
df_exclusively_illustrator.insert(1, "role", 2)

df_distinct_artists = pd.concat([df_both_author_and_illustrator, df_exclusively_author, df_exclusively_illustrator])

# #####################################################################################################################
# NEW DATAFRAMES WITH SPECIFIC COLUMNS
#######################################################################################################################
df_games = df5[["title", "nb_p_min", "nb_p_max", "age_min", "nature", "author", "coauthor", "illustrator"]].drop_duplicates().drop_duplicates(subset="title",
                                                                                                         keep="first")
df_copy = df5[["code", "title", "location", "wear_condition", "general_state", "date_of_purchase", "nature"]]
df_artist = df5[["author", "coauthor", "illustrator", "title"]].drop_duplicates().drop_duplicates(subset="title",
                                                                                                  keep="first")
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
# INSERTING NEW GAME
#######################################################################################################################
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM game_sequence;")
game_id = int(cursor.fetchone()[0])

cursor.execute("""
CREATE OR REPLACE FUNCTION public.insert_game(g_id INT, g_title TEXT, g_lower_case_title TEXT, g_min_age INT, g_min_month INT,
    g_max_age INT, g_min_number_of_player INT, g_max_number_of_player INT, g_nature INT)
RETURNS BOOLEAN LANGUAGE plpgsql AS
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
        (game_id, row["title"], row["title"].lower(), min_year,
         min_month, 0, row["nb_p_min"], row["nb_p_max"], row["nature"]))

    wasInserted: bool = False
    for r in cursor.fetchone():
        wasInserted = r

    if wasInserted:
        print("Successfully inserted : {} of id={}".format(row["title"], game_id))

    else:
        print("Ignored entry : {} of id={}".format(row["title"], game_id))
        game_id += -1

cursor.execute("SELECT setval('game_sequence', %s, true);", [game_id])
conn.commit()
cursor.close()

#######################################################################################################################
# INSERTING NEW COPY
#######################################################################################################################
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM game_copy_sequence;")
copy_id = int(cursor.fetchone()[0])

cursor.execute("""
CREATE OR REPLACE FUNCTION public.insert_copy(c_id INT, c_code TEXT,c_fk_game INT, c_date_of_purchase DATE,
                                        c_general_state INT, c_location TEXT, c_wear_condition INT, c_available BOOL)
RETURNS BOOLEAN LANGUAGE plpgsql AS
$$
DECLARE 
    v_operation bool := false;
BEGIN
   WITH ins AS (
        INSERT INTO game_copy(id, object_code, fk_game, date_of_purchase, date_of_registration, general_state, location, wear_condition, is_available_for_loan)
        VALUES (c_id, c_code, c_fk_game, c_date_of_purchase, CURRENT_DATE, c_general_state, c_location, c_wear_condition, c_available)
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

    cursor.execute("SELECT public.insert_copy(%s,%s,%s,%s,%s,%s,%s,%s);",
                   (copy_id, str(row["code"]), fk_game, row["date_of_purchase"], row["general_state"], row["location"],
                    row["wear_condition"], False if row["nature"] == 0 else True))

    wasInserted: bool = False
    for r in cursor.fetchone():
        wasInserted = r

    if wasInserted:
        print("Successfully inserted : {} of id={}".format(row["code"], copy_id))

    else:
        print("Entry ignored : {} of id={}".format(row["code"], copy_id))
        copy_id += -1

cursor.execute("SELECT setval('game_copy_sequence', %s, true);", [copy_id])
conn.commit()
cursor.close()

#######################################################################################################################
# INSERTING DISTINCT ARTIST
#######################################################################################################################
cursor = conn.cursor()
cursor.execute("SELECT last_value FROM creator_sequence;")
artist_id = int(cursor.fetchone()[0])

cursor.execute("""
CREATE OR REPLACE FUNCTION public.insert_artist(c_id INT, c_last_name VARCHAR, c_role INT)
RETURNS BOOLEAN LANGUAGE plpgsql AS
$$
DECLARE 
    v_operation bool := false;
BEGIN
   WITH ins AS (
        INSERT INTO creator(id, first_name, last_name, lower_case_first_name, lower_case_last_name, role)
        VALUES (c_id, '', c_last_name, '', lower(c_last_name), c_role)
        ON CONFLICT ON CONSTRAINT unique_name DO NOTHING
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
for index, row in df_distinct_artists.iterrows():
    artist_id += 1

    cursor.execute("SELECT public.insert_artist(%s,%s,%s);",
                   (artist_id, row["name"], row["role"]))

    wasInserted: bool = False
    for r in cursor.fetchone():
        wasInserted = r

    if wasInserted:
        print("Successfully inserted : {} of id={}".format(row["name"], artist_id))

    else:
        print("Entry ignored : {} of id={}".format(row["name"], artist_id))
        artist_id += -1

cursor.execute("SELECT setval('creator_sequence', %s, true);", [artist_id])
conn.commit()
cursor.close()

#######################################################################################################################
# GAME <- MANY TO MANY -> ARTIST
#######################################################################################################################
cursor = conn.cursor()
for index, row in df_games.iterrows():
    cursor.execute("SELECT id FROM game WHERE title LIKE %s", (row["title"],))
    record = cursor.fetchone()
    fk_game = record[0]

    cursor.execute("""
    SELECT id FROM creator 
    WHERE last_name IN (%s, %s, %s)""", [row["author"], row["coauthor"], row["illustrator"]])
    for value in cursor.fetchall():
        if value is not None:
            cursor.execute("""
                INSERT INTO game_creator(fk_game, fk_creator)
                VALUES (%s, %s)
                ON CONFLICT DO NOTHING
                """, (fk_game, value))

conn.commit()
cursor.close()
conn.close()
