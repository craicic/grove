package org.motoc.gamelibrary.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "administrator", schema = "public")
public class Administrator {

    @Id
    private Long id;

}
