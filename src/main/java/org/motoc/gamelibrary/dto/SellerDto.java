package org.motoc.gamelibrary.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The seller of a game copy dto
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {

    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ContactDto contact;
}
