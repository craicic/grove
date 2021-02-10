package org.motoc.gamelibrary.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Article's image : store a file path
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "file_path"))
public class Image {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</a>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotBlank(message = "File path cannot be null or blank")
    @Size(max = 4251, message = "File path cannot exceed 4251 characters")
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game")
    private Game game;

}
