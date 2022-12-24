package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Publisher's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherNoIdDto {

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Valid
    private ContactDto contact;
}
