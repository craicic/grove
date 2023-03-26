package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.motoc.gamelibrary.domain.enumeration.GameNature;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentAgeRange;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentNumberOfPlayer;
import org.motoc.gamelibrary.technical.validation.annotation.SelectYearOrMonth;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Game's DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConsistentAgeRange
@ConsistentNumberOfPlayer
@SelectYearOrMonth
public class GameDto {
    private Long id;

    @NotBlank(message = "Name cannot be null or blank")
    private String title;

    @Size(max = 2000, message = "Description should not exceed 1000 characters")
    private String description;

    @Size(max = 255, message = "Play time should not exceed 20 characters")
    private String playTime;

    @Range(min = 1, max = 100, message = "Min number of players must be between 1 and 100")
    private short minNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Max number of players must be between 0 and 100")
    private short maxNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Min age must be between 0 and 100")
    private short minAge;

    @Range(min = 0, max = 100, message = "Max age must be between 0 and 100")
    private short maxAge;

    @Range(min = 0, max = 100, message = "Min months must be between 0 and 100")
    private short minMonth;

    @Size(max = 2000, message = "Material should not exceed 1000 characters")
    private String material;

    @Size(max = 1000, message = "Goal should not exceed 1000 characters")
    private String goal;

    private String rules;

    @Size(max = 15000, message = "Variant should not exceed 15000 characters")
    private String variant;

    private GameNature nature;
    private String size;

    @Valid
    private Set<CategoryDto> categories = new HashSet<>();

    @Valid
    private Set<MechanismDto> mechanisms = new HashSet<>();

    @Valid
    private Set<CreatorWithoutContactDto> creators = new HashSet<>();

    @Valid
    private Set<GameCopyIdAndCodeDto> copies = new HashSet<>();

    private Set<Long> imageIds = new HashSet<>();

}
