package org.motoc.gamelibrary.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * A blog article
 *
 * @author RouzicJ
 */
@Data
public class Article {

    private long id;
    private LocalDateTime publicationTime;
    private LocalDateTime lastEditTime;
    private byte[] htmlContent;
    private String shortDescription;

}
