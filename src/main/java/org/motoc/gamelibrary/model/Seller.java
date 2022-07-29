package org.motoc.gamelibrary.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The seller of a game copy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name"))
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @Column(name = "lower_case_name", nullable = false)
    private String lowerCaseName;

    @Embedded
    private Contact contact;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "seller")
    private Set<GameCopy> gameCopies = new HashSet<>();


    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseName = name.toLowerCase();
    }

    public void addGameCopy(GameCopy gameCopy) {
        this.gameCopies.add(gameCopy);
        gameCopy.setSeller(this);
    }

    public void removeGameCopy(GameCopy gameCopy) {
        this.gameCopies.remove(gameCopy);
        gameCopy.setSeller(null);
    }
}
