package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


/**
 * Loan's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    private Long id;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDate loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDate loanEndTime;

    private Long gameCopyId;

    @NotNull(message = "isClosed must have a value")
    private boolean isClosed;

}
