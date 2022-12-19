package org.motoc.gamelibrary.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * Contain some detail about the account
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(name = "account_seq_gen", sequenceName = "account_sequence", initialValue = 1)
    private Long id;

    @Size(max = 127, message = "User's firstname cannot exceed 127 characters")
    @Column(name = "first_name", length = 127)
    private String firstName;

    @Size(max = 127, message = "User's lastname cannot exceed 127 characters")
    @Column(name = "last_name", length = 127)
    private String lastName;

    @NotBlank(message = "Username cannot be null or blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Membership Number cannot be null or blank")
    @Size(max = 50, message = "Membership Number cannot exceed 50 characters")
    @Column(name = "membership_number", nullable = false, length = 50)
    private String membershipNumber;

    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    @Embedded
    private Contact contact;

    @OneToMany(mappedBy = "account")
    private Set<Loan> loans;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", membershipNumber='" + membershipNumber + '\'' +
                ", renewalDate=" + renewalDate +
                ", contact=" + contact +
                '}';
    }

    @PrePersist
    public void prePersist() {
        this.renewalDate = LocalDate.now();
        this.membershipNumber = UUID.randomUUID().toString();
    }

}
