package org.motoc.gamelibrary.repository.fragment;

import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.model.Publisher;

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
     * Removes a contact from a publisher.
     *
     * @Return Publisher for service logging.
     */
    Publisher removeContact(Long publisherId);

    /**
     * Get all Publisher's name in a custom DTO
     */
    List<PublisherNameDto> findNames();

    Publisher savePublisher(Publisher p);
}
