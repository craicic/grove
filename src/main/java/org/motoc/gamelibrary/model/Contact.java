package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Contact details for external actors
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String postalCode;
    private String street;
    private String city;
    private String country;
    private String streetNumber;
    private String phoneNumber;
    private String website;

    @OneToOne(mappedBy = "contact")
    private Author author;

    @OneToOne(mappedBy = "contact")
    private Publisher publisher;

    @OneToOne(mappedBy = "contact")
    private Seller seller;
}
