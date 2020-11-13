package org.motoc.gamelibrary.repository.criteria;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
public interface PublisherRepositoryCustom {
    void remove(Long id);

    void removeContact(Long publisherId, Long contactId);
}
