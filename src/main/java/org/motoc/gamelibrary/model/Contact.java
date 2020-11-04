package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.validation.annotation.UniqueContactHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Contact details for external actors
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@UniqueContactHolder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Size(max = 50, message = "Postal code cannot exceed 50 characters")
    @Column(length = 50)
    private String postalCode;

    @Size(max = 255, message = "Street cannot exceed 255 characters")
    private String street;

    @Size(max = 50, message = "City cannot exceed 50 characters")
    @Column(length = 50)
    private String city;

    @Size(max = 50, message = "Country cannot exceed 50 characters")
    @NotBlank(message = "Country cannot be null or blank")
    @Column(nullable = false, length = 50)
    private String country;

    @Size(max = 10, message = "Street number cannot exceed 10 characters")
    @Column(length = 10)
    private String streetNumber;

    @Size(max = 50, message = "Phone number cannot exceed 50 characters")
    @Column(length = 50)
    private String phoneNumber;

    @Size(max = 75, message = "Website cannot exceed 75 characters")
    @Column(length = 75)
    private String website;

    @Size(max = 320, message = "Mail address cannot exceed 320 characters")
    @Column(length = 320)
    private String mailAddress;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Creator creator;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Publisher publisher;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Seller seller;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id &&
                Objects.equals(postalCode, contact.postalCode) &&
                Objects.equals(street, contact.street) &&
                Objects.equals(city, contact.city) &&
                country.equals(contact.country) &&
                Objects.equals(streetNumber, contact.streetNumber) &&
                Objects.equals(phoneNumber, contact.phoneNumber) &&
                Objects.equals(website, contact.website) &&
                Objects.equals(mailAddress, contact.mailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postalCode, street, city, country, streetNumber, phoneNumber, website, mailAddress);
    }
}
