package org.motoc.gamelibrary.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * A game, it describe a copy
 *
 * @author RouzicJ
 */
@Data
public class GameCopy {

    private long objectCode;
    private double price;
    private String location;
    private LocalDate dateOfPurchase;
    private LocalDate registerDate;
    private String wearCondition;

}
