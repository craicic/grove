package org.motoc.gamelibrary.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCopyIdAndCodeDto {

    private Long id;

    @Pattern(regexp = "^[0-9]{1,5}$")
    private String objectCode;
}
