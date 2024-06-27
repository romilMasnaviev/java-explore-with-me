package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.model.StatEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatEntityRepository extends JpaRepository<StatEntity, Integer> {

//    @Query("select new ru.masnaviev.explore.dto.StatEntityGetResponse(s.app, s.uri, count(s)) " +
//            "from StatEntity s " +
//            "where s.timestamp > :start and s.timestamp < :end " +
//            "and (:uris is null or s.uri in :uris) " +
//            "and (:unique = false or s.ip in (select t.ip from StatEntity t " +
//            "where t.timestamp > :start and t.timestamp < :end)) " +
//            "group by s.app, s.uri")
//    List<StatEntityGetResponse> getStatistics(@Param("start") LocalDateTime start,
//                                              @Param("end") LocalDateTime end,
//                                              @Param("uris") List<String> uris,
//                                              @Param("unique") boolean unique);

//    @Query("select new ru.masnaviev.explore.dto.StatEntityGetResponse(s.app, s.uri, count(s)) " +
//            "from StatEntity s " +
//            "where s.timestamp > :start and s.timestamp < :end " +
//            "and (:uris is null or :uris = '[]' or s.uri in (:uris)) " +
//            "and (:unique = false or s.ip in (select t.ip from StatEntity t " +
//            "where t.timestamp > :start and t.timestamp < :end " +
//            "group by t.ip)) " +
//            "group by s.app, s.uri")
//    List<StatEntityGetResponse> getStatistics(@Param("start") LocalDateTime start,
//                                              @Param("end") LocalDateTime end,
//                                              @Param("uris") List<String> uris,
//                                              @Param("unique") boolean unique);

    @Query("select new ru.masnaviev.explore.dto.StatEntityGetResponse(s.app, s.uri, " +
            "count(distinct (case when :unique = true then s.ip else concat(s.uri, s.ip,s.timestamp) end))) " +
            "from StatEntity s " +
            "where s.timestamp > :start and s.timestamp < :end " +
            "and (:#{#uris == null || #uris.isEmpty()} = true OR s.uri IN :uris) " +
            "group by s.app, s.uri")
    List<StatEntityGetResponse> getStatistics(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end,
                                              @Param("uris") List<String> uris,
                                              @Param("unique") boolean unique);

}
