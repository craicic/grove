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
 * The seller of a game copy
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @OneToMany(mappedBy = "seller")
    private Set<GameCopy> gameCopies = new HashSet<>();

    public void addGameCopy(GameCopy gameCopy) {
        this.gameCopies.add(gameCopy);
        gameCopy.setSeller(this);
    }

    public void removeGameCopy(GameCopy gameCopy) {
        this.gameCopies.remove(gameCopy);
        gameCopy.setSeller(null);
    }

    public void addContact(Contact contact) {
        this.setContact(contact);
        contact.setSeller(this);
    }

    /**
     * This helper method is to use before deleting the contact of a publisher.
     */
    public void removeContact(Contact contact) {
        this.setContact(null);
        contact.setSeller(null);
    }
}
