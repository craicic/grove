package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "SELECT g.id, g.name, g.description, g.play_time,g.min_number_of_player, g.max_number_of_player, " +
            "g.min_age, g.max_age, g.min_month, c.id, c.name, " +
            "i.id, i.file_path, gcopy.id, c2.id, c2.first_name, c2.last_name, c2.role " +
            " FROM (game as g " +
            " LEFT JOIN game_category gc on g.id = gc.fk_game " +
            " LEFT JOIN category c on c.id = gc.fk_category " +
            " LEFT JOIN game_image gi on g.id = gi.fk_game " +
            " LEFT JOIN image i on i.id = gi.fk_image " +
            " LEFT JOIN game_copy gcopy on g.id = gcopy.fk_game " +
            " LEFT JOIN game_creator gc2 on g.id = gc2.fk_game " +
            " LEFT JOIN creator c2 on c2.id = gc2.fk_creator) " +
            " WHERE (g.lower_case_name like concat('%', :keyword, '%')) " +
            " ORDER BY g.name " +
            " --/n #pageable/n ",
            countQuery = "SELECT count(*) FROM (game g) ",
            nativeQuery = true)
    Page<Game> getFilteredGameOverviewNative(@Param("keyword") String keyword, Pageable pageable);

    Page<Game> findAllByLowerCaseNameContaining(@Param("keyword") String keyword, Pageable pageable);

}
