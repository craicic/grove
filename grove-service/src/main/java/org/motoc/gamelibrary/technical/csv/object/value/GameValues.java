package org.motoc.gamelibrary.technical.csv.object.value;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameValues {

    private String title;

    private String nature;
    private String stat;
    private String unknown;
    private String ageRange;

    private String nbOfPlayers;

    private List<String> authorNames  = new ArrayList<>();

    private String IllustratorName;

    private String objectCodes;

}
