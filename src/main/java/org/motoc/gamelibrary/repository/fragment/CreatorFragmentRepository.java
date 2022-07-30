package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.dto.CreatorNameDto;

import java.util.List;

/**
 * Creator custom repository, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
public interface CreatorFragmentRepository {

//    /**
//     * Removes a contact from a creator, then delete the contact.
//     */
//    void removeContact(Long creatorId, Long contactId);
//
//    /**
//     * Removes a creator and its associated contact
//     */
//    void remove(Long id);

    /**
     * Get all Creator's name in a custom DTO
     */
    List<CreatorNameDto> findNames();
}
