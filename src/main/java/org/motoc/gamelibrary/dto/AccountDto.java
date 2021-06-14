package org.motoc.gamelibrary.dto;

import lombok.*;
import org.motoc.gamelibrary.model.Contact;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @Size(max = 127, message = "User's firstname cannot exceed 127 characters")
    private String firstName;

    @Size(max = 127, message = "User's lastname cannot exceed 127 characters")
    private String lastName;

    @NotBlank(message = "Username cannot be null or blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String username;

    @NotBlank(message = "Membership Number cannot be null or blank")
    @Size(max = 50, message = "Membership Number cannot exceed 50 characters")
    private String membershipNumber;

    private LocalDate renewalDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Contact contact;
}
