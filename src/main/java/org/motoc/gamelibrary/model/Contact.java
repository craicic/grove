package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Contact details for external actors
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Size(max = 50, message = "Postal code cannot exceed 50 characters")
    @Column(length = 50)
    private String postCode;

    @Size(max = 255, message = "Street cannot exceed 255 characters")
    private String street;

    @Size(max = 255, message = "City cannot exceed 255 characters")
    @Column(length = 255)
    private String city;

    @Size(max = 50, message = "Street number cannot exceed 50 characters")
    @Column(length = 50)
    private String houseNumber;

    @Size(max = 255, message = "Country cannot exceed 255 characters")
    @NotBlank(message = "Country cannot be null or blank")
    @Column(nullable = false, length = 255)
    private String country;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    @Column(length = 50)
    private String phoneNumber;

    @Size(max = 255, message = "Website cannot exceed 255 characters")
    @Column(length = 255)
    private String website;

    @Size(max = 320, message = "Mail address cannot exceed 320 characters")
    @Column(length = 320)
    private String mailAddress;
}
