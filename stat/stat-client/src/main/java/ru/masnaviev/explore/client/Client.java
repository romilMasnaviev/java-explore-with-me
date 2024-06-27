package ru.masnaviev.explore.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@RestController
@Slf4j
public class Client {
    private final String url = "http://127.0.0.1:9090"; //TODO при добавлении докера поменять на переменные из app.properties
    private final String startStr = "start";
    private final String endStr = "end";
    private final String urisStr = "uris";
    private final String uniqueStr = "unique";
    private final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private final RestTemplate restTemplate;

    public Client(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StatEntityPostRequest request) {
        log.debug("StatClient. Post request, request = {}", request);
        return restTemplate.exchange(url + "/hit", HttpMethod.POST, new HttpEntity<>(request), HttpStatus.class);
    }

    @GetMapping("/stats")
    public List<StatEntityGetResponse> get(@RequestParam(startStr) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime start,
                                           @RequestParam(endStr) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime end,
                                           @RequestParam(name = urisStr, required = false) List<String> uris,
                                           @RequestParam(name = uniqueStr, required = false, defaultValue = "false") boolean unique) {
        log.debug("StatClient. Get request, Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        UriComponentsBuilder builder = buildRequest(start, end, uris, unique);
        return buildResponse(builder);
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

    private List<StatEntityGetResponse> buildResponse(UriComponentsBuilder builder) {
        HttpEntity<StatEntityGetResponse[]> response = restTemplate.getForEntity(
                builder.encode().toUriString(),
                StatEntityGetResponse[].class
        );

        StatEntityGetResponse[] responses = response.getBody();

        assert responses != null;
        return Arrays.asList(responses);
    }


}
