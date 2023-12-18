package org.motoc.gamelibrary.technical.csv.object.dto;

import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;

import java.util.Objects;

@Setter
@Getter
public class ArtistDto {

    private String firstName;
    private String lastName;
    private CreatorRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistDto artistDto = (ArtistDto) o;

        if (!Objects.equals(firstName, artistDto.firstName)) return false;
        return lastName.equals(artistDto.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", role=" + role +
               '}';
    }
}
