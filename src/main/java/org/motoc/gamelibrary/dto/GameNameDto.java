package org.motoc.gamelibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Game name's DTO
 *
 * @author RouzicJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameNameDto {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameNameDto that = (GameNameDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
