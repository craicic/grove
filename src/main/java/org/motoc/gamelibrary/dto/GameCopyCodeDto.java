package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCopyCodeDto {

    @Pattern(regexp = "^[0-9]{1,5}$")
    private String objectCode;

}
