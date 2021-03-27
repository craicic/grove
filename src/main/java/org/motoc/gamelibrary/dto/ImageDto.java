package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * Image's DTO
 *
 * @author RouzicJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Long id;

    @NotNull
    private String data;
}
