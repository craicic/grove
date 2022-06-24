package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.dto.PublisherNameDto;

import java.util.List;

/**
 * This repository takes advantage of Criteria API / JPQL
 */
public interface PublisherFragmentRepository {

    /**
     * Removes a publisher and its associated contact
     */
    void remove(Long id);

    /**
     * Removes a contact from a publisher, then delete the contact.
     */
    void removeContact(Long publisherId, Long contactId);

    /**
     * Get all Publisher's name in a custom DTO
     */
    List<PublisherNameDto> findNames();
}
