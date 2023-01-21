package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "image_blob")
public class ImageBlob {

    @Id
    private Long id;

    @Column(name = "content", nullable = false)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;

    @OneToOne
    @MapsId
    private Image image;

}
