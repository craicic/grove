import pandas as pd


def df_editor(df):
    header = ["code", "game_title", "unknown1", "nature", "location", "code_stat", "wear_condition",
              "general_state", "date_of_purchase", "price", "publisher", "seller", "unknown2",
              "age_range", "nb_players", "author2", "author1", "illustrator"]

    pd.set_option("display.max_columns", None)
    pd.set_option("display.max_colwidth", 20)
    pd.set_option("display.width", None)
    pd.set_option("display.show_dimensions", True)
    pd.set_option("display.max_rows", 50)
    pd.set_option("display.min_rows", 20)
    df.columns = header

    df = df.drop(columns=['unknown1', 'unknown2'])

    print(df)


def rep_info(df):
    print(df.info())


dataframe = pd.read_csv("Liste_OBJET_2.csv", sep=";")
df_editor(dataframe)
rep_info(dataframe)
print(dataframe)
