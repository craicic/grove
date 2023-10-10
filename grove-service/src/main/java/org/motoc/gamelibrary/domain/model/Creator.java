package org.motoc.gamelibrary.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creator_seq_gen")
    @SequenceGenerator(name = "creator_seq_gen", sequenceName = "creator_sequence", initialValue = 1)
    private Long id;


    @Size(max = 50, message = "First name should not exceed 50 characters")
    @Column(length = 50)
    private String firstName;


    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String lastName;

    @Embedded
    private Contact contact;

    @ToString.Exclude
    @Column(name = "lower_case_first_name", length = 50)
    private String lowerCaseFirstName;

    @ToString.Exclude
    @Column(name = "lower_case_last_name", nullable = false, length = 50)
    private String lowerCaseLastName;

    @NotNull(message = "Role cannot be null")
    @Column(nullable = false, length = 50)
    private CreatorRole role;

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
}
