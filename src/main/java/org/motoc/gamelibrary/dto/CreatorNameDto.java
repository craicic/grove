package org.motoc.gamelibrary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto for creator's name
 *
 * @author RouzicJ
 */
@Data
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
