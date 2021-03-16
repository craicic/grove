package org.motoc.gamelibrary.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Game overview's DTO, image are define by their id
 * The goal is to have a quick presentation with only one image, name, etc. They are fetch by page of 5 to 20 approx
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameOverviewDto {

    private Long id;

    /**
     * The quantity of copy the game library has
     */
    private long gameCopyCount;

    private GameNameAndIdDto coreGame;

    private Set<GameNameAndIdDto> expansions = new HashSet<>();

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

    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CategoryDto> categories = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CreatorWithoutContactDto> creators = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Long> imageIds;
}
