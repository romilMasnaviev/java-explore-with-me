package ru.masnaviev.explore.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatEntityPostDto {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
