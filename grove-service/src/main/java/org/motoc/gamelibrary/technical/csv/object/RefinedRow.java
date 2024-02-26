package org.motoc.gamelibrary.technical.csv.object;

import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.technical.csv.object.dto.ArtistDto;
import org.motoc.gamelibrary.technical.csv.object.dto.GameDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RefinedRow {

    private Integer objectCode;
    private GameDto games;
    private List<ArtistDto> artists = new ArrayList<>();
    // Other Processed values
}
