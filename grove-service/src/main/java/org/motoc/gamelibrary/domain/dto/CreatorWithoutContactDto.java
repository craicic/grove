package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dto for creator, excluding contact informations
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatorWithoutContactDto {

    private Long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Role cannot be null")
    private CreatorRole role;
}
