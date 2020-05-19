package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
