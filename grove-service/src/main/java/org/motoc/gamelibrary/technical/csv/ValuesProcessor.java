package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.technical.csv.object.Row;
import org.motoc.gamelibrary.technical.csv.object.value.ArtistValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ValuesProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    public int cleanArtist(List<ArtistValue> artists) {
        int nbOfRemovedEntries = 0;
        for (int i = 0; i < artists.size(); i++) {
            String name  = artists.get(i).getName();
            if (name != null && name.matches(".*(&|/|(?i) ET ).*")) {
                logAndNullifyValue(artists, i, name);
                nbOfRemovedEntries++;
            }
        }
        return nbOfRemovedEntries;
    }
    private void logAndNullifyValue(List<ArtistValue> artists, int i, String value) {
        log.debug("Name value : " + value + " has been rejected because it contains a '&' or a 'ET' or '/'");
        artists.set(i, null);
    }

    public int removeDuplicateArtists(List<ArtistValue> list) {
        int startingSize = list.size();

        List<ArtistValue> temp = new ArrayList<>(new ArrayList<>(new HashSet<>(list)));
        int arrivalSize = temp.size();
        list.clear();
        list.addAll(temp);
        return startingSize - arrivalSize;
    }
}
