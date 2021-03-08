package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Theme's DTO
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    private String name;
}
