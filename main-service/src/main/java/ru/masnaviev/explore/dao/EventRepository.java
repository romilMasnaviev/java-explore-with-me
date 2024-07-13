package ru.masnaviev.explore.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.masnaviev.explore.model.Event;
import ru.masnaviev.explore.model.State;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT * FROM events e WHERE e.initiator_id = :id  LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Event> getUserEvents(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("id") Integer userId);


    @Query("SELECT e FROM Event e WHERE " +
            "((:users) is null or e.initiator.id in (:users)) " +
            "and ((:states) is null or e.state in (:states)) " +
            "and ((:categories) is null or e.category.id in (:categories)) " +
            "and ((cast(:rangeStart as date) is null and cast(:rangeEnd as date) is null) " +
            "or (e.eventDate < :rangeEnd and  e.eventDate > :rangeStart))")
    List<Event> getAllByParameters(@Param("users") List<Integer> users,
                                   @Param("states") List<State> states,
                                   @Param("categories") List<Integer> categories,
                                   @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd,
                                   Pageable pageable);

    @Query("SELECT e FROM Event e WHERE " +
            "(coalesce(:text, '') = '' OR lower(e.annotation) LIKE lower(concat('%', :text, '%')) " +
            "OR lower(e.description) LIKE lower(concat('%', :text, '%'))) " +
            "AND (coalesce(:categories, null) IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (cast(:rangeStart as date) IS NULL OR e.eventDate > :rangeStart) " +
            "AND (cast(:rangeEnd as date) IS NULL OR e.eventDate < :rangeEnd) " +
            "AND e.state = 'PUBLISHED' " +
            "AND (:onlyAvailable IS NULL OR e.confirmedRequests < e.participantLimit)")
    List<Event> getAllByTextAndParameters(@Param("text") String text,
                                          @Param("categories") List<Integer> categories,
                                          @Param("paid") Boolean paid,
                                          @Param("rangeStart") LocalDateTime rangeStart,
                                          @Param("rangeEnd") LocalDateTime rangeEnd,
                                          @Param("onlyAvailable") Boolean onlyAvailable,
                                          Pageable pageable);

    boolean existsByIdAndState(Integer id, State state);

}

