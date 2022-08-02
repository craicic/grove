package org.motoc.gamelibrary.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The status of a loan
 * Note will not be used before V1.5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_status", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_tag"))
public class LoanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_status_seq_gen")
    @SequenceGenerator(name = "loan_status_seq_gen", sequenceName = "loan_status_sequence", initialValue = 100)
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

    // Overridden accessors
    public void setTag(String tag) {
        this.tag = tag;
        this.lowerCaseTag = tag.toLowerCase();
    }

}
