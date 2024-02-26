package org.motoc.gamelibrary.technical.csv.object.value;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ArtistValue {

    private String name;
    private Integer objectCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistValue that = (ArtistValue) o;

        if (!Objects.equals(name, that.name)) return false;
        return objectCode.equals(that.objectCode);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + objectCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ArtistValue{" +
               "name='" + name + '\'' +
               ", objectCode=" + objectCode +
               '}';
    }
}
