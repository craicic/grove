package org.motoc.gamelibrary.model;

import lombok.*;

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
@Table(name = "loan_status", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_tag"))
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Tag cannot be null or blank")
    @Size(max = 50, message = "Tag cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String tag;

    @ToString.Exclude
    @Column(name = "lower_case_tag", nullable = false, length = 50)
    private String lowerCaseTag;

    @NotBlank(message = "Description cannot be null or blank")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Column(nullable = false)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "loanStatus")
    private Set<Loan> loans;

    // Overridden accessors
    public void setTag(String tag) {
        this.tag = tag;
        this.lowerCaseTag = tag.toLowerCase();
    }

    // Helper methods
    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setLoanStatus(this);
    }

    public void removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setLoanStatus(null);
    }
}
