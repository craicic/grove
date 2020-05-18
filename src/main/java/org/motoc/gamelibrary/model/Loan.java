package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.validation.annotation.ConsistentDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * A game copy loan
 *
 * @author RouzicJ
 */
@ConsistentDateTime
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid cannot exceed 50 characters")
    @Column(nullable = false)
    private String userUuid;

    @NotNull(message = "Loan start time cannot be null")
    @Column(nullable = false)
    private LocalDateTime loanStartTime;

    @NotNull(message = "Loan start time cannot be null")
    @Column(nullable = false)
    private LocalDateTime loanEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game_copy")
    private GameCopy gameCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_loan_status")
    private LoanStatus loanStatus;

    // Helper methods

    public void addLoanStatus(LoanStatus loanStatus) {
        this.setLoanStatus(loanStatus);
        loanStatus.getLoans().add(this);
    }

    public void removeLoanStatus(LoanStatus loanStatus) {
        this.setLoanStatus(null);
        loanStatus.getLoans().remove(loanStatus);
    }

}
