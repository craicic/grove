package org.motoc.gamelibrary.domain.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * Article's image : store a file path
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image", schema = "public")
public class Image {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href="https://thoughts-on-java.org/jpa-generate-primary-keys"></a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_gen")
    @SequenceGenerator(name = "image_seq_gen", sequenceName = "image_sequence", initialValue = 1)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game")
    private Game game;

}
