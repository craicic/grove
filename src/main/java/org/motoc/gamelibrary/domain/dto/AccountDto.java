package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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

    private List<LoanDto> loans;

    @Valid
    private ContactDto contact;
}
