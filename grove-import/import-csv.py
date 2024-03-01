import math
import re

import pandas as pd


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
    header = ["code", "game_title", "unknown1", "nature", "location", "code_stat", "wear_condition",
              "general_state", "date_of_purchase", "price", "publisher", "seller", "unknown2",
              "age_range", "nb_players", "author2", "author1", "illustrator"]
    dataframe.columns = header
    return dataframe


def exclude_complex_naming(name):
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
    name_parts = [j.strip() for j in name_parts]
    return " ".join(str(part) for part in name_parts)


df = pd.read_csv("Liste_OBJET_2.csv", sep=";", encoding="UTF-8")
config()
df = replace_header(df)

# removes two apparently useless columns
df.drop(columns=['unknown1', 'unknown2'], inplace=True)

# df.set_index(keys=["code"], inplace=True)

# get information on the dataframe
# df.info()

# name cleaning and filtering
# author1_df = df.author1.str.title().str.rsplit(" ", expand=True)
# a = pd.concat([df, author1_df], axis=1)

author2_series = df.author2.apply(exclude_complex_naming)

# age extraction
# fusion_df = df.age_range.str.extract(r'((\d+) (MOIS|ANS))')
# df.age_range = fusion_df[0]

# only keep nature GRAND JEU
# df = df.loc[df.nature == 'GRAND JEU']


df_author1 = df.author1.dropna()
df_author2 = df.author2.dropna()
authors = pd.concat([df_author1, df_author2])
authors = authors.drop_duplicates()

illustrators = df.illustrator.dropna()
illustrators = illustrators.drop_duplicates()

both_auth_ill = pd.Series(list(set(illustrators).intersection(set(authors))))
only_auth = pd.Series(list(set(authors).difference(set(both_auth_ill))))
only_ill = pd.Series(list(set(illustrators).difference(set(both_auth_ill))))

# New dataframe with specific columns
df_games = df[["game_title", "nature", "code_stat", "age_range", "nb_players"]].drop_duplicates()

df_games.to_csv("output/games.csv", sep=";")
df.to_csv("output/objects.csv", sep=";")
