package org.motoc.gamelibrary.model.enumeration;

/**
 * An enumeration : Toy, board game, wooden game
 */
public enum GameNatureEnum {

    TOY("Jouet"),
    BOARD_GAME("Jeu de société"),
    BIG_GAME("Grand jeu"),
    OVERSIZE_GAME("Jeu surdimensionné"),
    WOODEN_GAME("Jeu en bois");

    GameNatureEnum(String frenchTranslation) {
    }
}
