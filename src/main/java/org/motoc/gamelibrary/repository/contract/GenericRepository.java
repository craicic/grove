package org.motoc.gamelibrary.repository.contract;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T> extends JpaRepository<T, Long> {
}
