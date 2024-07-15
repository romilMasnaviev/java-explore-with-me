package ru.masnaviev.converter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.masnaviev.dto.StatEntityPostRequest;
import ru.masnaviev.model.StatEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class StatEntityConverterTest {

    @Autowired
    private StatEntityConverter converter;

    @Test
    public void testStatEntityConvertToPostDto_whenValidData_thenReturnStatEntityPostRequest() {
        StatEntity entity = new StatEntity();
        entity.setTimestamp(LocalDateTime.now());
        entity.setApp("test");
        entity.setUri("test");
        entity.setIp("test");

        StatEntityPostRequest request = converter.statEntityConvertToPostDto(entity);

        assertEquals(entity.getApp(), request.getApp());
        assertEquals(entity.getIp(), request.getIp());
        assertEquals(entity.getUri(), request.getUri());
    }

    @Test
    public void testPostDtoConvertToStatEntity_whenValidData_thenReturnStatEntity() {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setTimestamp(LocalDateTime.now());
        request.setApp("test");
        request.setUri("test");
        request.setIp("test");

        StatEntity entity = converter.postDtoConvertToStatEntity(request);

        assertEquals(request.getApp(), entity.getApp());
        assertEquals(request.getIp(), entity.getIp());
        assertEquals(request.getUri(), entity.getUri());
    }

    @Test
    public void testPostDtoConvertToStatEntity_whenItsNull_thenReturnNull() {
        StatEntityPostRequest request = null;
        StatEntity entity = converter.postDtoConvertToStatEntity(request);

        assertNull(entity);
    }

    @Test
    public void testStatEntityConvertToPostDto_whenItsNull_thenReturnNull() {
        StatEntity entity = null;
        StatEntityPostRequest request = converter.statEntityConvertToPostDto(entity);

        assertNull(request);
    }
}