import pandas as pd


def config():
    pd.set_option("display.max_columns", None)
    pd.set_option("display.width", None)
    pd.set_option("display.max_colwidth", 20)

    # if more than 50 rows, display only 20 of them
    pd.set_option("display.max_rows", 50)
    pd.set_option("display.min_rows", 40)


# edits the header
def replace_header(df):
    header = ["code", "game_title", "unknown1", "nature", "location", "code_stat", "wear_condition",
              "general_state", "date_of_purchase", "price", "publisher", "seller", "unknown2",
              "age_range", "nb_players", "author2", "author1", "illustrator"]
    df.columns = header
    return df


# removes two apparently useless columns
def remove_columns(df):
    df = df.drop(columns=['unknown1', 'unknown2'])
    return df


dataframe = pd.read_csv("Liste_OBJET_2.csv", sep=";")
config()
replace_header(dataframe)
remove_columns(dataframe)

# prints information on the dataframe
print(dataframe.info())

to_remove = list()

for index in range(0, len(dataframe)):
    row = dataframe.iloc[index]
    if row['nature'] != 'GRAND JEU':
        to_remove.append(index)

dataframe = dataframe.drop(to_remove)

print(dataframe)
