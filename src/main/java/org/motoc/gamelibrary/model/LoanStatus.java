package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The status of a loan
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "tag"))
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Tag cannot be null or blank")
    @Size(max = 50, message = "Tag cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String tag;

    @NotBlank(message = "Description cannot be null or blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "loanStatus")
    private Set<Loan> loans;

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setLoanStatus(this);
    }

    public void removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setLoanStatus(null);
    }
}
