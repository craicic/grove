package org.motoc.gamelibrary.dto;

import lombok.*;
import org.motoc.gamelibrary.model.enumeration.GeneralStateEnum;

import javax.validation.Valid;
import javax.validation.constraints.*;
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

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    @NotNull(message = "Date of purchase cannot be null")
    private LocalDate registerDate;

    @NotBlank(message = "Wear condition cannot be null or blank")
    private String wearCondition;

    @NotNull(message = "General State cannot be null")
    private GeneralStateEnum generalState;

    private boolean isLoanable;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private SellerDto seller;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Valid
    private PublisherDto publisher;

    private Long gameId;
    private String gameName;

}
