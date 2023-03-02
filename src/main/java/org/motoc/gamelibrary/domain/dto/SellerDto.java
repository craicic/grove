package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The seller of a game copy dto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Valid
    private ContactDto contact;
}
