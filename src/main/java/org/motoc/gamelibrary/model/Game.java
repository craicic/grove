package org.motoc.gamelibrary.model;

import lombok.Data;

/**
 * A game, it's a description of the item, not of a copy
 *
 * @author RouzicJ
 */
@Data
public class Game {

    private long id;
    private String name;
    private String description;
    private String numberOfPlayers;
    private short ageMin;
    private short ageMax;
    private String Stuff;
    private String preparation;
    private String goal;
    private byte[] coreRules;
    private byte[] variant;
    private byte[] ending;
    private String nature;
    private String size;
    private boolean isBoardGame;
    private int editionNumber;

}
