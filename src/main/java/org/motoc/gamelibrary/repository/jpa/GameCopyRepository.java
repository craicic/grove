package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.GameCopy;
import org.motoc.gamelibrary.repository.fragment.GameCopyFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface GameCopyRepository extends JpaRepository<GameCopy, Long>, GameCopyFragmentRepository {

    GameCopy findByObjectCode(String code);

    @Query(value = "SELECT gc.id, gc.date_of_purchase, gc.general_state, gc.is_loanable, gc.location, gc.object_code, gc.price, " +
            "gc.register_date, gc.wear_condition, gc.fk_publisher, gc.fk_seller, gc.fk_game, g.id, g.name FROM game_copy AS gc " +
            "LEFT JOIN game AS g ON gc.fk_game = g.id " +
            "WHERE gc.is_loanable AND (SELECT (COUNT(l) = 0) " +
            "FROM loan l WHERE (l.fk_game_copy = gc.id AND l.is_closed = false )) " +
            "ORDER BY g.name --/n #pageable/n ",
            nativeQuery = true)
    Page<GameCopy> findPageByLoanability(Pageable pageable);

    @Query(value = "SELECT gc.id, gc.date_of_purchase, gc.general_state, gc.is_loanable, gc.location, gc.object_code, gc.price, " +
            "gc.register_date, gc.wear_condition, gc.fk_publisher, gc.fk_seller, gc.fk_game, g.id, g.name FROM game_copy AS gc " +
            "LEFT JOIN game AS g ON gc.fk_game = g.id " +
            "WHERE gc.is_loanable AND (SELECT (COUNT(l) = 0) " +
            "FROM loan l WHERE (l.fk_game_copy = gc.id AND l.is_closed = false )) " +
            "ORDER BY g.name ",
            nativeQuery = true)
    List<GameCopy> findByLoanability();
}
