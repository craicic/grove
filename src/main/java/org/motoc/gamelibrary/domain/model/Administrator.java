package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "administrator", schema = "public")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrator_seq_gen")
    @SequenceGenerator(name = "administrator_seq_gen", sequenceName = "administrator_sequence", initialValue = 1)
    private Long id;

    @Size(max = 255, message = "Firstname cannot exceed 255 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255, message = "Lastname cannot exceed 255 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Username cannot be null or blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be null or blank")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    @Column(nullable = false)
    private String password;
}
