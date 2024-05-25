package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.masnaviev.explore.model.StatEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatEntityRepository extends JpaRepository<StatEntity, Integer> {

    @Query("select s " +
            "from StatEntity s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in (:uris) ")
    List<StatEntity> getByParameters(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select distinct s " +
            "from StatEntity s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in (:uris) " +
            "and s.ip not in (select t from StatEntity t where t.ip = s.ip and t.timestamp < s.timestamp)")
    List<StatEntity> getDistinctByParameters(LocalDateTime start, LocalDateTime end, List<String> uris);
}
