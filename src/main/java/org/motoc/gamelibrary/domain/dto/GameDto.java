package org.motoc.gamelibrary.domain.dto;

import lombok.*;
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

    @EqualsAndHashCode.Exclude
    private GameNameAndIdDto coreGame;

    @EqualsAndHashCode.Exclude
    private Set<GameNameAndIdDto> expansions = new HashSet<>();

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @Size(max = 20, message = "Description should not exceed 20 characters")
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

    @Size(max = 1000, message = "Stuff should not exceed 1000 characters")
    private String stuff;

    @Size(max = 15000, message = "Preparation should not exceed 15000 characters")
    private String preparation;

    @Size(max = 1000, message = "Goal should not exceed 1000 characters")
    private String goal;

    @Size(max = 15000, message = "Core rules should not exceed 15000 characters")
    private String coreRules;

    @Size(max = 15000, message = "Variant should not exceed 15000 characters")
    private String variant;

    @Size(max = 15000, message = "Ending rules should not exceed 15000 characters")
    private String ending;

    private GameNature nature;
    private String size;


    private PublisherNameAndIdDto publisher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CategoryDto> categories = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<MechanismDto> mechanisms = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private Set<CreatorWithoutContactDto> creators = new HashSet<>();

    @Valid
    private Set<GameCopyIdAndCodeDto> copies = new HashSet<>();

    private Set<Long> imageIds = new HashSet<>();

}
