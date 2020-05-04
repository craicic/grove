package org.motoc.gamelibrary.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * A game copy loan
 *
 * @author RouzicJ
 */
@Data
public class Loan {

    private String userUuid;
    private LocalDateTime loanStartTime;
    private LocalDateTime loanEndTime;

}
