package ru.masnaviev.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.masnaviev.dto.StatEntityGetResponse;
import ru.masnaviev.model.StatEntity;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class StatEntityRepositoryTest {

    @Autowired
    StatEntityRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    public void getStatistics_whenValidData_thenReturnEntityList() {
        StatEntity entity = new StatEntity();
        entity.setApp("test");
        entity.setUri("test");
        entity.setIp("test");
        entity.setTimestamp(now());

        StatEntityGetResponse summary = new StatEntityGetResponse("test", "test", 1);

        repository.save(entity);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(1), now().plusMinutes(1), null, false);
        assertEquals(summary, statSummaries.get(0));
        assertEquals(1, statSummaries.size());
    }

    @Test
    public void getStatistics_whenWithStatistics_thenReturnEntityList() {
        StatEntity entity = new StatEntity();
        entity.setApp("test");
        entity.setUri("test");
        entity.setIp("test");
        entity.setTimestamp(now());

        StatEntityGetResponse summary = new StatEntityGetResponse("test", "test", 1);

        repository.save(entity);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(1), now().plusMinutes(1), List.of("test"), false);
        assertEquals(summary, statSummaries.get(0));
        assertEquals(1, statSummaries.size());
    }

    @Test
    public void getStatistics_whenWrongUri_thenReturnEmptyList() {
        StatEntity entity = new StatEntity();
        entity.setApp("test");
        entity.setUri("test");
        entity.setIp("test");
        entity.setTimestamp(now());

        repository.save(entity);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(1), now().plusMinutes(1), List.of("wrong"), false);
        assertTrue(statSummaries.isEmpty());
    }

    @Test
    public void getStatistics_whenWrongData_thenReturnEmptyList() {
        StatEntity entity = new StatEntity();
        entity.setApp("test");
        entity.setUri("test");
        entity.setIp("test");
        entity.setTimestamp(now());

        repository.save(entity);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().minusMinutes(2), null, false);
        assertTrue(statSummaries.isEmpty());
    }


    @Test
    public void getStatistics_whenUnique_thenReturnEmptyList() {
        StatEntity entity1 = new StatEntity();
        entity1.setApp("test");
        entity1.setUri("test");
        entity1.setIp("test");
        entity1.setTimestamp(now());

        StatEntity entity2 = new StatEntity();
        entity2.setApp("test");
        entity2.setUri("test");
        entity2.setIp("test");
        entity2.setTimestamp(now());

        repository.save(entity1);
        repository.save(entity2);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().plusMinutes(2), null, true);
        assertEquals(1, statSummaries.size());
    }

    @Test
    public void getStatistics_whenUniqueAndValidUris_thenReturnEmptyList() {
        StatEntity entity1 = new StatEntity();
        entity1.setApp("test");
        entity1.setUri("test");
        entity1.setIp("test");
        entity1.setTimestamp(now());

        StatEntity entity2 = new StatEntity();
        entity2.setApp("test");
        entity2.setUri("test");
        entity2.setIp("test");
        entity2.setTimestamp(now());

        repository.save(entity1);
        repository.save(entity2);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().plusMinutes(2), List.of("test"), true);
        assertEquals(1, statSummaries.size());
    }

    @Test
    public void getStatistics_whenUniqueAndValidUrisV2_thenReturnEmptyList() {
        StatEntity entity1 = new StatEntity();
        entity1.setApp("test");
        entity1.setUri("test");
        entity1.setIp("test");
        entity1.setTimestamp(now());

        StatEntity entity2 = new StatEntity();
        entity2.setApp("test");
        entity2.setUri("test");
        entity2.setIp("test");
        entity2.setTimestamp(now());

        StatEntity entity3 = new StatEntity();
        entity3.setApp("test");
        entity3.setUri("wrong");
        entity3.setIp("test");
        entity3.setTimestamp(now());

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().plusMinutes(2), List.of("test"), true);
        assertEquals(1, statSummaries.size());
    }

    @Test
    public void getStatistics_whenUniqueAndValidUrisV3_thenReturnEmptyList() {
        StatEntity entity1 = new StatEntity();
        entity1.setApp("test");
        entity1.setUri("test");
        entity1.setIp("test");
        entity1.setTimestamp(now());

        StatEntity entity2 = new StatEntity();
        entity2.setApp("test");
        entity2.setUri("test");
        entity2.setIp("test");
        entity2.setTimestamp(now());

        StatEntity entity3 = new StatEntity();
        entity3.setApp("test");
        entity3.setUri("test");
        entity3.setIp("test");
        entity3.setTimestamp(now());

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().plusMinutes(2), List.of("test"), true);
        assertEquals(1, statSummaries.size());
        assertEquals(1, statSummaries.get(0).getHits());
    }


    @Test
    public void getStatistics_whenValidUris_thenReturnList() {
        StatEntity entity1 = new StatEntity();
        entity1.setApp("test");
        entity1.setUri("test");
        entity1.setIp("test");
        entity1.setTimestamp(now());

        StatEntity entity2 = new StatEntity();
        entity2.setApp("test");
        entity2.setUri("test");
        entity2.setIp("test");
        entity2.setTimestamp(now());

        StatEntity entity3 = new StatEntity();
        entity3.setApp("test");
        entity3.setUri("test");
        entity3.setIp("test");
        entity3.setTimestamp(now());

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<StatEntityGetResponse> statSummaries = repository.getStatistics(now().minusMinutes(3), now().plusMinutes(2), List.of("test"), false);
        assertEquals(3, statSummaries.get(0).getHits());
    }


}