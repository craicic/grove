package org.motoc.gamelibrary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Article's image : store a file path
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

    /**
     * <p>Chosen strategy is SEQUENCE</p>
     * <a href>https://thoughts-on-java.org/jpa-generate-primary-keys/</href>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false, unique = true)
    private String filePath;

    @ManyToMany(mappedBy = "keywords")
    private Set<Article> articles = new HashSet<Article>();

}
