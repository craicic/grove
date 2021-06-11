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

    @NotBlank(message = "User uuid cannot be null or blank")
    @Size(max = 50, message = "User uuid cannot exceed 50 characters")
    private String userUuid;

    @NotBlank(message = "Membership Number cannot be null or blank")
    @Size(max = 50, message = "Membership Number cannot exceed 50 characters")
    private String membershipNumber;

    private LocalDate renewalDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Contact contact;
}
