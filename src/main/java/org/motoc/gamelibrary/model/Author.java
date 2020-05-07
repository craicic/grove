package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The author of a game
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    @Column(length = 50)
    private String firstName;


    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @ManyToMany(mappedBy = "authors")
    private Set<Game> games = new HashSet<>();

}
