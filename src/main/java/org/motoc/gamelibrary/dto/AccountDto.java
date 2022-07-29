package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String membershipNumber;

    private LocalDate renewalDate;

    @Valid
    private ContactDto contact;
}
