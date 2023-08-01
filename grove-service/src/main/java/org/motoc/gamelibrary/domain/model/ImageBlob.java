package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;

import java.sql.Types;

@Getter
@Setter
@Entity
@Table(name = "image_blob", schema = "public")
public class ImageBlob {

    @Id
    private Long id;

    @Column(name = "content", nullable = false)
    @Lob
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] content;

    @OneToOne
    @MapsId
    @JoinColumn(name = "fk_image")
    private Image image;

}
