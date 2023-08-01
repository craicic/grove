package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Mechanism's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MechanismDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    private String title;
}
