package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.validation.annotation.EitherChildOrParent;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Category's DTO
 *
 * @author RouzicJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EitherChildOrParent
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, message = "Name cannot exceed 50")
    private String name;

    @Valid
    private CategoryDto parent;

    @Valid
    private Set<CategoryDto> children = new HashSet<>();
}
