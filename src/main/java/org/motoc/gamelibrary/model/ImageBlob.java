package org.motoc.gamelibrary.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@Getter
@Setter
@Entity(name = "image_blob")
public class ImageBlob {

    @Id
    private Long id;

    @Column(name = "content", nullable = false)
    @Lob
    private Blob content;

    @OneToOne
    @MapsId
    private Image image;

}
