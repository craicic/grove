package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Contain some detail about the account
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account", schema = "public")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String userUuid;

    @NotBlank(message = "Membership Number cannot be null or blank")
    @Size(max = 50, message = "Membership Number cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String membershipNumber;

    private LocalDate renewalDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                userUuid.equals(account.userUuid) &&
                membershipNumber.equals(account.membershipNumber) &&
                Objects.equals(renewalDate, account.renewalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userUuid, membershipNumber, renewalDate);
    }
}
