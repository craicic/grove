package org.motoc.gamelibrary.repository;

import org.motoc.gamelibrary.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
