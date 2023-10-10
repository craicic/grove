package org.motoc.gamelibrary.domain.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCopyIdAndCodeDto {

    private Long id;

    @Pattern(regexp = "^[0-9]{1,5}$")
    private String objectCode;
}
