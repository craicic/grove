package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

// TODO REPLACE this by CategoryDto.java once child & parents are remove

/**
 * Category name and Id DTO
 *
 * @author RouzicJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNameAndIdDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

}
