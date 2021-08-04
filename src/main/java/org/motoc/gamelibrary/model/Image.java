package org.motoc.gamelibrary.model;


import lombok.*;

import javax.persistence.*;

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
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data", nullable = false)
    @Lob
    private byte[] data;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game")
    private Game game;

}
