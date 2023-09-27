package org.motoc.gamelibrary.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.motoc.gamelibrary.domain.enumeration.GeneralState;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCopyDto {

    private Long id;

    @Pattern(regexp = "^[0-9]{1,5}$")
    private String objectCode;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price value cannot be below 0.0")
    @Digits(integer = 10, fraction = 2, message = "Price maximum integer part is 10, maximum fractional part is 2")
    private BigDecimal price;

    /**
     * The location of the game in premises
     */
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    private LocalDate dateOfPurchase;

    private LocalDate dateOfRegistration;

    @NotBlank(message = "Wear condition cannot be null or blank")
    private String wearCondition;

    private String editionNumber;

    @NotNull(message = "General State cannot be null")
    private GeneralState generalState;

    private boolean isAvailableForLoan;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PublisherDto publisher;

    private Long gameId;
    private String gameTitle;

}
