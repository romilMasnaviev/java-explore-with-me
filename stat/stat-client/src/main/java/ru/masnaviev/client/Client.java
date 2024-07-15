package ru.masnaviev.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.masnaviev.dto.StatEntityGetResponse;
import ru.masnaviev.dto.StatEntityPostRequest;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Клиент
 */
@RestController
@Slf4j
public class Client {
    private static final String startStr = "start";
    private static final String endStr = "end";
    private static final String urisStr = "uris";
    private static final String uniqueStr = "unique";
    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private final String url;
    private final RestTemplate restTemplate;

    public Client(RestTemplate restTemplate, @Value("${stat-server.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    /**
     * Создание сущности статистики
     */
    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StatEntityPostRequest request) {
        log.debug("StatClient. Post request, request = {}", request);
        return restTemplate.exchange(url + "/hit", HttpMethod.POST, new HttpEntity<>(request), HttpStatus.class);
    }

    /**
     * Получение сущности статистики
     */
    @GetMapping("/stats")
    public List<StatEntityGetResponse> get(@RequestParam(startStr) @DateTimeFormat(pattern = localDateTimePattern) @PastOrPresent LocalDateTime start,
                                           @RequestParam(endStr) @DateTimeFormat(pattern = localDateTimePattern) @PastOrPresent LocalDateTime end,
                                           @RequestParam(name = urisStr, required = false) List<String> uris,
                                           @RequestParam(name = uniqueStr, required = false, defaultValue = "false") boolean unique) {
        log.debug("StatClient. Get request, Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        UriComponentsBuilder builder = buildRequest(start, end, uris, unique);
        return sendResponse(builder);
    }

    private UriComponentsBuilder buildRequest(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/stats")
                .queryParam(startStr, start.toString())
                .queryParam(endStr, end.toString())
                .queryParam(uniqueStr, unique);

        if (uris != null && !uris.isEmpty()) {
            builder.queryParam(urisStr, String.join(",", uris));
        }

        return builder;
    }

    private List<StatEntityGetResponse> sendResponse(UriComponentsBuilder builder) {
        HttpEntity<StatEntityGetResponse[]> response = restTemplate.getForEntity(
                builder.encode().toUriString(),
                StatEntityGetResponse[].class
        );

        StatEntityGetResponse[] responses = response.getBody();

        assert responses != null;
        return Arrays.asList(responses);
    }


}
