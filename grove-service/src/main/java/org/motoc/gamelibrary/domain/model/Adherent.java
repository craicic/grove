package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "adherent", schema = "public")
public class Adherent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adherent_seq_gen")
    @SequenceGenerator(name = "adherent_seq_gen", sequenceName = "adherent_sequence", initialValue = 1)
    private Long id;

    @Size(max = 255, message = "Firstname cannot exceed 255 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255, message = "Lastname cannot exceed 255 characters")
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 320, message = "Mail address cannot exceed 320 characters")
    @Column(name = "mail_address", length = 320)
    private String mailAddress;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @OneToMany(mappedBy = "adherent")
    private Set<Reservation> reservations;
}
