package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

    @Size(max = 50, message = "Postal code cannot exceed 50 characters")
    private String postalCode;

    private String street;

    @Size(max = 50, message = "City cannot exceed 50 characters")
    private String city;

    @Size(max = 50, message = "Country cannot exceed 50 characters")
    @NotBlank(message = "Country cannot be null or blank")
    private String country;

    @Size(max = 10, message = "Street number cannot exceed 10 characters")
    private String streetNumber;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    private String phoneNumber;

    @Size(max = 75, message = "Website cannot exceed 75 characters")
    private String website;

    @OneToOne(mappedBy = "contact")
    private Creator creator;

    @OneToOne(mappedBy = "contact")
    private Publisher publisher;

    @OneToOne(mappedBy = "contact")
    private Seller seller;

    @OneToOne(mappedBy = "contact")
    private Account account;

}
