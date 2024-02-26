package org.motoc.gamelibrary.technical.csv.object.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class GameDto {

    private String title;
    private String nature;
    private String stat;
    private String unknown;
    private String ageRange;
    private String nbOfPlayers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameDto gameDto = (GameDto) o;

        if (!Objects.equals(title, gameDto.title)) return false;
        if (!Objects.equals(nature, gameDto.nature)) return false;
        if (!Objects.equals(stat, gameDto.stat)) return false;
        if (!Objects.equals(unknown, gameDto.unknown)) return false;
        if (!Objects.equals(ageRange, gameDto.ageRange)) return false;
        return Objects.equals(nbOfPlayers, gameDto.nbOfPlayers);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (nature != null ? nature.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (unknown != null ? unknown.hashCode() : 0);
        result = 31 * result + (ageRange != null ? ageRange.hashCode() : 0);
        result = 31 * result + (nbOfPlayers != null ? nbOfPlayers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameDto{" +
               "title='" + title + '\'' +
               ", nature='" + nature + '\'' +
               ", stat='" + stat + '\'' +
               ", unknown='" + unknown + '\'' +
               ", ageRange='" + ageRange + '\'' +
               ", nbOfPlayers='" + nbOfPlayers + '\'' +
               '}';
    }
}
