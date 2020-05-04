package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A blog article
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</href>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;


    @Column(nullable = false)
    private LocalDateTime publicationTime;

    private LocalDateTime lastEditTime;

    @Lob
    @Column(nullable = false)
    private byte[] htmlContent;

    private String shortDescription;

    @ManyToMany
    @JoinTable(
            name = "article_keyword",
            joinColumns = { @JoinColumn(name = "fk_article") },
            inverseJoinColumns = { @JoinColumn(name = "fk_keyword") })
    private Set<Keyword> keywords = new HashSet<Keyword>();

    @ManyToMany
    @JoinTable(
            name = "article_image",
            joinColumns = { @JoinColumn(name = "fk_article") },
            inverseJoinColumns = { @JoinColumn(name = "fk_image") })
    private Set<Image> images = new HashSet<Image>();

}
