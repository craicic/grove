package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
