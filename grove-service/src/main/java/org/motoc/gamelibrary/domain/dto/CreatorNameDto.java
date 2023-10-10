package org.motoc.gamelibrary.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto for creator's name
 */
@Getter
@Setter
@NoArgsConstructor
public class CreatorNameDto {

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    public CreatorNameDto(
            @Size(max = 50, message = "First name should not exceed 50 characters")
                    String firstName,
            @NotBlank(message = "Last name cannot be null or blank")
            @Size(max = 50, message = "Last name should not exceed 50 characters")
                    String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
