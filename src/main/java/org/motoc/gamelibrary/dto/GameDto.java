package org.motoc.gamelibrary.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.motoc.gamelibrary.model.enumeration.GameNatureEnum;
import org.motoc.gamelibrary.validation.annotation.ConsistentAgeRange;
import org.motoc.gamelibrary.validation.annotation.ConsistentNumberOfPlayer;
import org.motoc.gamelibrary.validation.annotation.SelectYearOrMonth;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Game's DTO
 *
 * @author RouzicJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConsistentAgeRange
@ConsistentNumberOfPlayer
@SelectYearOrMonth
public class GameDto {
    private long id;

    @EqualsAndHashCode.Exclude
    private GameDto coreGame;

    @EqualsAndHashCode.Exclude
    private Set<GameDto> expansions = new HashSet<>();

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @Size(max = 20, message = "Description should not exceed 20 characters")
    private String playTime;

    @Range(min = 1, max = 100, message = "Min number of players must be between 1 and 100")
    private short minNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Max number of players must be between 1 and 100")
    private short maxNumberOfPlayer;

    @Range(min = 0, max = 100, message = "Min age must be between 1 and 100")
    private short minAge;

    @Range(min = 0, max = 100, message = "Max age must be between 1 and 100")
    private short maxAge;

    @Range(min = 0, max = 100, message = "Min months must be between 1 and 100")
    private short minMonth;

    private GameNatureEnum nature;
    private String size;
    private String editionNumber;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CategoryDto> categories = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<ThemeDto> themes = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CreatorDto> creators = new HashSet<>();

    // TODO images && gameCopies

}
