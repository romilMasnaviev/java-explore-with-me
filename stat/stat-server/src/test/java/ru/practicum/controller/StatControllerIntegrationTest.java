package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.service.StatServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatController.class)
class StatControllerIntegrationTest {

    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimePattern);
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;
    @MockBean
    StatServiceImpl service;

    @Test
    public void testCreate_whenValidData_thenReturnHttpStatusOk() throws Exception {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setApp("test");
        request.setIp("test");
        request.setTimestamp(now().minusHours(1));
        request.setUri("test");

        when(service.create(request)).thenReturn(new ResponseEntity<>(request, HttpStatus.CREATED));

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreate_whenEmptyApp_thenReturnHttpStatusBadRequest() throws Exception {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setApp(null);
        request.setIp("test");
        request.setTimestamp(now().minusHours(1));
        request.setUri("test");

        when(service.create(request)).thenReturn(new ResponseEntity<>(request, HttpStatus.CREATED));

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreate_whenEmptyBody_thenReturnHttpStatusBadRequest() throws Exception {
        mvc.perform(post("/hit")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGet_whenValidData_thenReturnListResponses() throws Exception {
        List<StatEntityGetResponse> entities = new ArrayList<>();

        StatEntityGetResponse response = new StatEntityGetResponse("test", "test", 0);
        entities.add(response);


        when(service.get(any(LocalDateTime.class), any(LocalDateTime.class), any(), anyBoolean())).thenReturn(entities);

        mvc.perform(get("/stats")
                        .param("start", now().minusMinutes(1).format(formatter))
                        .param("end", now().plusMinutes(1).format(formatter))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].app", is("test")))
                .andExpect(jsonPath("$.[0].uri", is("test")))
                .andExpect(jsonPath("$.[0].hits", is(0)));
    }

    @Test
    public void testGet_whenNullEnd_thenReturnHttpStatusBadRequest() throws Exception {
        mvc.perform(get("/stats")
                        .param("start", now().minusMinutes(1).toString())
                        .param("end", "null")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGet_whenEmptyParams_thenReturnHttpStatusBadRequest() throws Exception {
        mvc.perform(get("/stats")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testCreate_whenServiceThrowEx_thenReturnHttpStatusBadRequest() throws Exception {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setApp("test");
        request.setIp("test");
        request.setTimestamp(now().minusHours(1));
        request.setUri("test");

        when(service.create(any())).thenThrow(new RuntimeException());

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGet_whenServiceThrowEx_thenReturnHttpStatusBadRequest() throws Exception {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setApp("test");
        request.setIp("test");
        request.setTimestamp(now().minusHours(1));
        request.setUri("test");

        List<StatEntityGetResponse> entities = new ArrayList<>();

        when(service.get(any(LocalDateTime.class), any(LocalDateTime.class), any(), anyBoolean())).thenReturn(entities);

        mvc.perform(get("/stats")
                        .param("start", now().minusMinutes(1).format(formatter))
                        .param("end", now().plusMinutes(1).format(formatter))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}