package org.motoc.gamelibrary.domain.model;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A game mechanism
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mechanism", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_title"))
public class Mechanism {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mechanism_seq_gen")
    @SequenceGenerator(name = "mechanism_seq_gen", sequenceName = "mechanism_sequence", initialValue = 1)
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    @Column(nullable = false, length = 50)
    private String title;

    @ToString.Exclude
    @Column(name = "lower_case_title", nullable = false, length = 50)
    private String lowerCaseTitle;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "mechanisms")
    private Set<Game> games = new HashSet<>();

    // Other constructors
    public Mechanism(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseTitle = title != null ? title.toLowerCase() : null;
    }

    public void addGame(Game game) {
        games.add(game);
        game.getMechanisms().add(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.getMechanisms().remove(this);
    }
}

