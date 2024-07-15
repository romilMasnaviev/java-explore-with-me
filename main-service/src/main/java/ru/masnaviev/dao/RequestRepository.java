package ru.masnaviev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.model.Request;
import ru.masnaviev.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByEventId(int eventId);

    List<Request> findAllByIdInAndEventId(List<Integer> requestIds, int eventId);

    List<Request> findAllByEventIdAndStatus(int eventId, Status status);

    boolean existsByUserIdAndEventId(int userId, int eventId);

    List<Request> findByUserId(Integer userId);

    Optional<Request> findByIdAndUserId(Integer requestId, Integer userId);
}
