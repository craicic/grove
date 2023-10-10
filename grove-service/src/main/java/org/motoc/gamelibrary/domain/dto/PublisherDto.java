package org.motoc.gamelibrary.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Publisher's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Valid
    private ContactDto contact;
}
