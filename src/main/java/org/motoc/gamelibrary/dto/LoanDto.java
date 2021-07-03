package org.motoc.gamelibrary.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


/**
 * Loan's DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    private Long id;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDate loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDate loanEndTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameCopyDto gameCopy;

    //    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private LoanStatus loanStatus;

    @NotNull(message = "isClosed must have a value")
    private boolean isClosed;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AccountDto account;

}
