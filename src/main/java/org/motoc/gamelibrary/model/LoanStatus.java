package org.motoc.gamelibrary.model;

import lombok.Data;

/**
 * The status of a loan
 *
 * @author RouzicJ
 */
@Data
public class LoanStatus {

    private long id;
    private String tag;
    private String description;

}
