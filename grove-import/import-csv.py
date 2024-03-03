import gc
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

    if isinstance(age_range, str):
        age_range = age_range.replace("À", "A")

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


df = pd.read_csv("Liste_OBJET_2.csv", sep=";", encoding="UTF-8")
config()
df = replace_header(df)

# removes two apparently useless columns
df.drop(columns=['unknown1', 'unknown2'], inplace=True)

# get information on the dataframe
df.info()

# name cleaning and filtering
a2 = df.author2.apply(rewrite)
df_a2 = (a2
         .str.replace(r"(no_value)|(multiple_value)|(ignored_value)", "None", regex=True)
         .str.rsplit(" ", n=1, expand=True))
df_a2.columns = ["aut2_firstname", "auth2_lastname"]

a1 = df.author1.apply(rewrite)
df_a1 = (a1
         .str.replace(r"(no_value)|(multiple_value)|(ignored_value)", "None", regex=True)
         .str.rsplit(" ", n=1, expand=True))
df_a1.columns = ["aut1_firstname", "auth1_lastname"]

ill = df.author2.apply(rewrite)
df_ill = (ill
          .str.replace(r"(no_value)|(multiple_value)|(ignored_value)", "None", regex=True)
          .str.rsplit(" ", n=1, expand=True))
df_ill.columns = ["ill_firstname", "ill_lastname"]

df_a = pd.concat([df, df_a1, df_a2, df_ill], axis=1)
df_a.drop(columns=["author1", "author2", "illustrator"], inplace=True)

del [df, df_a1, df_a2, df_ill, a1, a2, ill]
gc.collect()

# age operations
age = df_a.age_range.apply(rewrite_age)
age = age.str.replace(r"(no_value)|(multiple_value)|(ignored_value)", "None", regex=True)
df_age = pd.DataFrame({"min_age": age})
df_age.info()

df_b = pd.concat([df_a, df_age], axis=1)
df_b.drop(columns="age_range", inplace=True)

# only keep nature GRAND JEU
# df = df.loc[df.nature == 'GRAND JEU']

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
df_games = df_a[["game_title", "nature", "code_stat", "age_range", "nb_players"]].drop_duplicates()

df_games.to_csv("output/games.csv", sep=";")
df_a.to_csv("output/a.csv", sep=";")
