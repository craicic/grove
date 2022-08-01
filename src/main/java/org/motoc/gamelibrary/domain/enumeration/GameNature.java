package org.motoc.gamelibrary.domain.enumeration;

/**
 * An enumeration : Toy, board game, wooden game
 */
public enum GameNature {

    TOY("Jouet"),
    BOARD_GAME("Jeu de société"),
    BIG_GAME("Grand jeu"),
    OVERSIZE_GAME("Jeu surdimensionné"),
    WOODEN_GAME("Jeu en bois");

    GameNature(String frenchTranslation) {
    }
}
