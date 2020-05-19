package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.model.enumeration.CreatorRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The creator of a game
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Creator {

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

    @NotNull(message = "Role cannot be null")
    @Column(nullable = false, length = 50)
    private CreatorRole role;

    @OneToOne
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @ManyToMany(mappedBy = "creators")
    private Set<Game> games = new HashSet<>();

    // Helper methods
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
