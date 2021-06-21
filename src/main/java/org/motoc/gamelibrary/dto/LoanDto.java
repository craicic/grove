package org.motoc.gamelibrary.dto;

import lombok.*;
import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.model.GameCopy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * Loan's DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    private Long id;

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid cannot exceed 50 characters")
    private String userUuid;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDateTime loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    private LocalDateTime loanEndTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameCopy gameCopy;

    //    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private LoanStatus loanStatus;
    @NotNull(message = "isClosed must have a value")
    private boolean isClosed;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Account account;

}
