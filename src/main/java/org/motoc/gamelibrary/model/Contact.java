package org.motoc.gamelibrary.model;

import lombok.*;
import org.motoc.gamelibrary.validation.annotation.UniqueContactHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Contact details for external actors
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@UniqueContactHolder
@Table(name = "contact", schema = "public")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)

    private Creator creator;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)

    private Publisher publisher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Seller seller;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY)
    private Account account;
}
