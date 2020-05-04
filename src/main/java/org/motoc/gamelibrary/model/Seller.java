package org.motoc.gamelibrary.model;

import lombok.Data;

/**
 * The seller of a game copy
 *
 * @author RouzicJ
 */
@Data
public class Seller {

    private long id;
    private String name;
    private String website;

}
