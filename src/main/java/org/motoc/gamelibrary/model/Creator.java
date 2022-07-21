package org.motoc.gamelibrary.model;

import lombok.*;
import org.motoc.gamelibrary.model.enumeration.CreatorRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The creator of a game
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creator", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"lower_case_first_name", "lower_case_last_name"}))
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    @Column(length = 50)
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String lastName;

    @ToString.Exclude
    @Column(name = "lower_case_first_name", nullable = false, length = 50)
    private String lowerCaseFirstName;

    @ToString.Exclude
    @Column(name = "lower_case_last_name", nullable = false, length = 50)
    private String lowerCaseLastName;

    @NotNull(message = "Role cannot be null")
    @Column(nullable = false, length = 50)
    private CreatorRole role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "creators")
    private Set<Game> games = new HashSet<>();

    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseFirstName = firstName != null ? firstName.toLowerCase() : null;
        this.lowerCaseLastName = lastName != null ? lastName.toLowerCase() : null;
    }

    public void addGame(Game game) {
        this.games.add(game);
        game.getCreators().add(this);
    }

    public void removeGame(Game game) {
        this.games.remove(game);
        game.getCreators().remove(this);
    }

    public void addContact(Contact contact) {
        this.setContact(contact);
        contact.setCreator(this);
    }

    /**
     * This helper method is to be used before deleting the contact of an creator.
     */
    public void removeContact(Contact contact) {
        this.setContact(null);
        contact.setCreator(null);
    }
}
