package ru.practicum.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StatServiceImplTest {

    @Autowired
    private StatServiceImpl service;

    @Test
    @DirtiesContext
    public void testCreate_whenValidData_thenReturnStatEntityPostRequest() {
        StatEntityPostRequest requestToAdd = new StatEntityPostRequest();
        requestToAdd.setTimestamp(LocalDateTime.now());
        requestToAdd.setIp("testIp");
        requestToAdd.setUri("testUri");
        requestToAdd.setApp("testApp");

        StatEntityPostRequest savedRequest = service.create(requestToAdd);

        assertEquals(requestToAdd.getIp(), savedRequest.getIp());
        assertEquals(requestToAdd.getApp(), savedRequest.getApp());
        assertEquals(requestToAdd.getTimestamp(), savedRequest.getTimestamp());
        assertEquals(requestToAdd.getUri(), savedRequest.getUri());

    }

    @Test
    @DirtiesContext
    public void testGet_whenValidData_thenReturnListStatEntityGetResponse() {
        StatEntityPostRequest requestToAdd = new StatEntityPostRequest();
        requestToAdd.setTimestamp(LocalDateTime.now());
        requestToAdd.setIp("testIp");
        requestToAdd.setUri("testUri");
        requestToAdd.setApp("testApp");

        StatEntityPostRequest requestToAdd2 = new StatEntityPostRequest();
        requestToAdd2.setTimestamp(LocalDateTime.now());
        requestToAdd2.setIp("testIp2");
        requestToAdd2.setUri("testUri2");
        requestToAdd2.setApp("testApp2");

        service.create(requestToAdd);
        service.create(requestToAdd2);

        List<StatEntityGetResponse> responses = service.get(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1), null, false);
        assertEquals(2, responses.size());

        assertEquals(1, responses.get(0).getHits());
        assertEquals(requestToAdd.getApp(), responses.get(0).getApp());
        assertEquals(requestToAdd.getUri(), responses.get(0).getUri());

        assertEquals(1, responses.get(1).getHits());
        assertEquals(requestToAdd2.getApp(), responses.get(1).getApp());
        assertEquals(requestToAdd2.getUri(), responses.get(1).getUri());
    }
}