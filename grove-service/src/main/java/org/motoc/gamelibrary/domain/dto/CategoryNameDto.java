package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Category name's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNameDto {

    @NotBlank(message = "Title cannot be null or blank")
    @Size(max = 50, message = "Title cannot exceed 50")
    private String title;
}
