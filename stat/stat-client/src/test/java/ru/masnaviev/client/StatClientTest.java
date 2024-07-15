package ru.masnaviev.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.masnaviev.dto.StatEntityGetResponse;
import ru.masnaviev.dto.StatEntityPostRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Client.class)
class StatClientTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testCreate_whenValidData_thenReturnBadRequest() throws Exception {
        StatEntityPostRequest request = new StatEntityPostRequest();
        request.setApp("test");
        request.setIp("test");
        request.setTimestamp(LocalDateTime.now().minusHours(1));
        request.setUri("test");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(request))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet_whenValidData_thenReturnBadRequest() throws Exception {
        when(restTemplate.getForEntity(anyString(), eq(StatEntityGetResponse[].class)))
                .thenReturn(new ResponseEntity<>(new StatEntityGetResponse[0], HttpStatus.OK));

        mvc.perform(get("/stats")
                        .param("start", LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("end", LocalDateTime.now().minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet_whenValidDataWithUri_thenReturnBadRequest() throws Exception {
        when(restTemplate.getForEntity(anyString(), eq(StatEntityGetResponse[].class)))
                .thenReturn(new ResponseEntity<>(new StatEntityGetResponse[0], HttpStatus.OK));

        mvc.perform(get("/stats")
                        .param("start", LocalDateTime.now().minusMinutes(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("end", LocalDateTime.now().minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .param("uris", new ArrayList<>(List.of("test")).toString())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}