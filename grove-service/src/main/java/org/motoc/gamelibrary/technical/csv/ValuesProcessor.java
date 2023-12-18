package org.motoc.gamelibrary.technical.csv;

import org.motoc.gamelibrary.domain.enumeration.CreatorRole;
import org.motoc.gamelibrary.technical.csv.object.dto.ArtistDto;
import org.motoc.gamelibrary.technical.csv.object.value.ArtistValue;
import org.motoc.gamelibrary.technical.csv.object.value.GameCopyValues;
import org.motoc.gamelibrary.technical.csv.object.value.GameValues;
import org.motoc.gamelibrary.technical.csv.object.value.PublisherValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ValuesProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    public void processArtistValues(List<ArtistValue> authorValueList, List<ArtistValue> illustratorValueList) {
        int nbOfRemovedAuthor = filterDuplicates(authorValueList);
        int nbOfRemovedIllustrator = filterDuplicates(illustratorValueList);
        log.info("Duplication : removed " + nbOfRemovedAuthor + " author entries and " + nbOfRemovedIllustrator + " illustrator entries");

        // We remove entries where name is too complex to extract for exemple when it contains : '/', '&' or 'ET'
        nbOfRemovedAuthor = filterByPattern(authorValueList);
        nbOfRemovedIllustrator = filterByPattern(illustratorValueList);
        log.info("Complex name pattern : removed " + nbOfRemovedAuthor + " author entries and " + nbOfRemovedIllustrator + " illustrator entries");

        // Now we get rid of ArtistValue that have a name==null
        nbOfRemovedAuthor = filterByNullName(authorValueList);
        nbOfRemovedIllustrator = filterByNullName(illustratorValueList);
        log.info("Null name : removed " + nbOfRemovedAuthor + " author entries and " + nbOfRemovedIllustrator + " illustrator entries");

        // We remove process entries to perform operations on name
        Map<String, String> authorMap = new HashMap<>();
        Map<String, String> illustratorMap = new HashMap<>();
        nbOfRemovedAuthor = processNames(authorValueList, authorMap);
        nbOfRemovedIllustrator = processNames(illustratorValueList, illustratorMap);
        log.info("Name split into more than 2 parts : removed " + nbOfRemovedAuthor + " author entries and " + nbOfRemovedIllustrator + " illustrator entries");


        // Now we have to normalize the case. Upper case the first letter, lower case the rest.
        normalizeCase(authorMap);
        normalizeCase(illustratorMap);

        // We determine artists role based on the presence of the same author in the two maps we store this information
        // into a new mapped ArtistDto.

        List<ArtistDto> artistDtoList = mapToArtistDTO(authorMap, illustratorMap);
        artistDtoList.forEach(e -> log.info(e.toString()));

    }

    private List<ArtistDto> mapToArtistDTO(Map<String, String> authorMap, Map<String, String> illustratorMap) {
        List<ArtistDto> artistDtoList = new ArrayList<>();
        for (String key : authorMap.keySet()) {
            ArtistDto artistDto = new ArtistDto();
            artistDto.setLastName(key);
            artistDto.setFirstName(authorMap.get(key));
            artistDto.setRole(CreatorRole.AUTHOR);
            artistDtoList.add(artistDto);
        }
        // Intersection of 2 maps give us all the artist that has both role.
        Map<String,String> intersection = new HashMap<>(authorMap);
        intersection.keySet().retainAll(illustratorMap.keySet());
        for (String key: intersection.keySet()) {
            ArtistDto artistDto = new ArtistDto();
            artistDto.setLastName(key);
            artistDto.setFirstName(intersection.get(key));
            artistDto.setRole(CreatorRole.AUTHOR_ILLUSTRATOR);
            artistDtoList.add(artistDto);
        }
        illustratorMap.keySet().removeAll(intersection.keySet());
        for (String key : illustratorMap.keySet()) {
            ArtistDto artistDto = new ArtistDto();
            artistDto.setLastName(key);
            artistDto.setFirstName(illustratorMap.get(key));
            artistDto.setRole(CreatorRole.ILLUSTRATOR);
            artistDtoList.add(artistDto);
        }

        for (ArtistDto artistDto : artistDtoList) {
            if (artistDto.getFirstName() == null) {
                artistDto.setFirstName("");
            }
        }
        return artistDtoList;
    }

    private void normalizeCase(Map<String, String> map) {
        Set<String> lastNames = map.keySet();
        Map<String, String> temp = new HashMap<>();
        for (String lastName : lastNames) {
            String normalizedLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
            String normalizedFirstName = map.get(lastName);
            if (normalizedFirstName != null) {
                normalizedFirstName = normalizedFirstName.substring(0, 1).toUpperCase() + normalizedFirstName.substring(1).toLowerCase();
            }
            temp.put(normalizedLastName, normalizedFirstName);
        }
        map.clear();
        map.putAll(temp);
    }

    /**
     * Foreach artist
     * - split names into several parts using space or dot separator.
     * Now we have parts.
     * - if 1 part : store it into map as key with null value.
     * - if 2 parts : store second part as key, first as value.
     * - if more parts : ignore and log the entry. Remove the artistValue form lists
     */
    private int processNames(List<ArtistValue> list, Map<String, String> map) {
        int nbOfRejectedEntries = 0;
        for (int i = 0; i < list.size(); i++) {
            ArtistValue artist = list.get(i);
            String name = artist.getName();
            if (name == null) {
                throw new IllegalStateException("Unexpected null name value");
            }
            name = name.replace('.', ' ');
            name = name.replace("  ", " ");
            name = name.stripTrailing();
            String[] parts = name.split(" ");
            if (parts.length == 1) {
                map.put(parts[0], null);
            } else if (parts.length == 2) {
                map.put(parts[1], parts[0]);
            } else {
                log.debug("Name value : " + name + " has been rejected because it contains more than 2 parts");
                list.remove(i);
                nbOfRejectedEntries++;
                i--;
            }
        }
        return nbOfRejectedEntries;
    }


    public void processGameValues(List<GameValues> gameValuesList) {
    }

    public void processPublisherValue(List<PublisherValues> publisherValuesList) {
    }

    public void processGameCopyValue(List<GameCopyValues> gameCopyValuesList) {
    }

    public int filterByPattern(List<ArtistValue> artists) {
        int nbOfRemovedEntries = 0;
        for (int i = 0; i < artists.size(); i++) {
            String name = artists.get(i).getName();
            if (name != null && name.matches(".*(&|/|(?i) ET ).*")) {
                logAndNullifyValue(artists, i, name);
                nbOfRemovedEntries++;
            }
        }
        return nbOfRemovedEntries;
    }

    private int filterByNullName(List<ArtistValue> list) {
        int initialSize = list.size();
        list.removeIf(artistValue -> artistValue.getName() == null);
        int arrivalSize = list.size();
        return initialSize - arrivalSize;
    }

    private void logAndNullifyValue(List<ArtistValue> artists, int i, String value) {
        log.debug("Name value : " + value + " has been rejected because it contains a '&' or a 'ET' or '/'");
        artists.get(i).setName(null);
    }

    public int filterDuplicates(List<ArtistValue> list) {
        int initialSize = list.size();
        List<ArtistValue> temp = new ArrayList<>(new ArrayList<>(new HashSet<>(list)));
        int arrivalSize = temp.size();
        list.clear();
        list.addAll(temp);
        return initialSize - arrivalSize;
    }
}
