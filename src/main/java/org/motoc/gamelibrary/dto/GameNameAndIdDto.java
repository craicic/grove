package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Game name and Id DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameNameAndIdDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    private String name;
}
