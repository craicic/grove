package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Game name and Id DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameNameAndIdDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    private String name;
}
