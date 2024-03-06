import gc
import math
import re

import pandas as pd
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
df1.drop(columns=['unknown1', 'unknown2'], inplace=True)

# artist's name cleaning and filtering
a2 = df1.author2.apply(rewrite)
df_a2 = (a2
         .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
         .str.rsplit(" ", n=1, expand=True))
df_a2.columns = ["aut2_firstname", "auth2_lastname"]

a1 = df1.author1.apply(rewrite)
df_a1 = (a1
         .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
         .str.rsplit(" ", n=1, expand=True))
df_a1.columns = ["aut1_firstname", "auth1_lastname"]

ill = df1.illustrator.apply(rewrite)
df_ill = (ill
          .str.replace(r"(no_value)|(multiple_values)|(ignored_value)", "None", regex=True)
          .str.rsplit(" ", n=1, expand=True))
df_ill.columns = ["ill_firstname", "ill_lastname"]

df2 = pd.concat([df1, df_a1, df_a2, df_ill], axis=1)
df2.drop(columns=["author1", "author2", "illustrator"], inplace=True)

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

df_test = df_player.where(df_player.player.str.contains(r"^\d+$", regex=True, na=False))
df_test.player.fillna('')

df_player.player = df_player.player + '-' + df_test.player.fillna('')
df_player.player = df_player.player.str.rstrip('-')
# split each row of the series 'player' on "-" or "+" char
df_nb_players = df_player.player.str.rsplit("-", expand=True).fillna("None")
df_nb_players.columns = ["nb_p_min", "nb_p_max"]

df4 = pd.concat([df3, df_nb_players], axis=1)
df4.drop(columns=["players"], inplace=True)

del [df2, df3, age, df_age, df_test, df_player, df_nb_players]
gc.collect()

df_title: DataFrame = (df4.title
                       .str.strip()
                       .str.replace(r"\s+", " ", regex=True)
                       .to_frame("title"))

df_test = df_title.where(df_title.title.str.contains(r",\S"))

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
df_games = df4.drop_duplicates()

df_games.to_csv("output/games.csv", sep=";")
df4.to_csv("output/a.csv", sep=";")
