package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Game name's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameTitleDto {

    @NotBlank(message = "Title cannot be null or blank")
    private String title;
}
