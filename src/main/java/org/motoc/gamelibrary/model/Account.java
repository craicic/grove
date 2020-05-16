package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Contain some detail about the account
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid cannot exceed 50 characters")
    @Column(nullable = false)
    private String userUuid;

    @NotBlank(message = "Membership Number cannot be null or blank")
    @Size(max = 50, message = "Membership Number cannot exceed 50 characters")
    @Column(nullable = false)
    private String membershipNumber;

    private LocalDate renewalDate;

    @OneToOne
    @JoinColumn(name = "fk_contact")
    private Contact contact;
}
