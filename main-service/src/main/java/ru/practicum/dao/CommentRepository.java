package ru.practicum.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByEventId(Integer eventId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE " +
            "((:text) IS NULL OR lower(c.text) LIKE lower(concat('%', (:text), '%'))) " +
            "AND ((:events) IS NULL OR c.event.id IN (:events)) " +
            "AND ((:authors) IS NULL OR c.author.id IN (:authors)) " +
            "AND ((:ids) IS NULL OR c.id IN (:ids)) " +
            "AND ((:isEdited) IS NULL OR c.isEdited = (:isEdited)) " +
            "AND ((cast(:rangeStart as date) IS NULL AND cast(:rangeEnd as date) IS NULL) OR " +
            "(c.created BETWEEN (:rangeStart) AND (:rangeEnd)))")
    List<Comment> findByParameters(@Param("ids") List<Integer> ids,
                                   @Param("events") List<Integer> events,
                                   @Param("authors") List<Integer> authors,
                                   @Param("text") String text,
                                   @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd,
                                   @Param("isEdited") Boolean isEdited,
                                   Pageable pageable);
}
