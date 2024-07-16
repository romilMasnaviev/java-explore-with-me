package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Клиент
 */
@Service
@Slf4j
public class Client {
    private static final String startStr = "start";
    private static final String endStr = "end";
    private static final String urisStr = "uris";
    private static final String uniqueStr = "unique";
    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimePattern);

    private final String url;
    private final RestTemplate restTemplate;

    @Autowired
    public Client(RestTemplateBuilder builder, @Value("${stat.serverц.url}") String url) {
        this.restTemplate = builder
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
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
                .queryParam(startStr, start.minusSeconds(10).format(formatter))
                .queryParam(endStr, end.plusHours(1).format(formatter))
                .queryParam(uniqueStr, unique);

        if (uris != null && !uris.isEmpty()) {
            builder.queryParam(urisStr, String.join(",", uris));
        }

        return builder;
    }

    private List<StatEntityGetResponse> sendResponse(UriComponentsBuilder builder) {
        String uriString = builder.build(false).toUriString();
        ResponseEntity<StatEntityGetResponse[]> response = restTemplate.getForEntity(
                uriString,
                StatEntityGetResponse[].class
        );

        StatEntityGetResponse[] responses = response.getBody();

        assert responses != null;
        return Arrays.asList(responses);
    }


}
