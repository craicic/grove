package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;

    @Size(max = 50, message = "Postal code cannot exceed 50 characters")
    private String postalCode;

    @Size(max = 255, message = "Street cannot exceed 255 characters")
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

    @Size(max = 320, message = "Mail address cannot exceed 320 characters")
    private String mailAddress;

}
