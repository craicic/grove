package org.motoc.gamelibrary.domain.dto;

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
public class GameTitleAndIdDto {

    private Long id;

    @NotBlank(message = "Title cannot be null or blank")
    private String title;
}
