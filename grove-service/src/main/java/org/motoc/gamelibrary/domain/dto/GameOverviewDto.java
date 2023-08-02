package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Game overview's DTO, image are define by their id
 * The goal is to have a quick presentation with only one image, name, etc. They are fetch by page of 5 to 20 approx
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameOverviewDto {

    private Long id;

    /**
     * The quantity of copy the game library has
     */
    private long gameCopyCount;


    @NotBlank(message = "Title cannot be null or blank")
    private String title;

    @Size(max = 2000, message = "Description should not exceed 2000 characters")
    private String description;

    @Size(max = 255, message = "Play time should not exceed 255 characters")
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

    @Valid
    private Set<CategoryDto> categories = new HashSet<>();

    @Valid
    private Set<CreatorWithoutContactDto> creators = new HashSet<>();

    private Set<Long> imageIds;
}
