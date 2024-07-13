package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.explore.model.Request;
import ru.masnaviev.explore.model.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Integer> {

    List<Request> findAllByEventId(int eventId);

    List<Request> findAllByIdInAndEventId(List<Integer> requestIds, int eventId);

    List<Request> findAllByEventIdAndStatus(int eventId, Status status);

    boolean existsByUserIdAndEventId(int userId, int eventId);

}
