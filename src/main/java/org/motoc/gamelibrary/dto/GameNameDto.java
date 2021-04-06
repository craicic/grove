package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Game name's DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameNameDto {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;
}
