package org.motoc.gamelibrary.domain.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The publisher of a game
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "publisher", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "lower_case_name"))
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publisher_seq_gen")
    @SequenceGenerator(name = "publisher_seq_gen", sequenceName = "publisher_sequence", initialValue = 1)
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @Column(name = "lower_case_name", nullable = false)
    private String lowerCaseName;

    @ToString.Exclude
    @OneToMany(mappedBy = "publisher")
    private Set<GameCopy> copies = new HashSet<>();

    @Embedded
    private Contact contact;

    // Helper methods

    /**
     * Adding a case-insensitive entry in database
     */
    @PrePersist
    @PreUpdate
    public void toLowerCase() {
        this.lowerCaseName = name.toLowerCase();
    }

    public void addCopy(GameCopy copy) {
        this.copies.add(copy);
        copy.setPublisher(this);
    }
}
