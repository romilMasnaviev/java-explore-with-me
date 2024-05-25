package ru.masnaviev.explore.dto;

import lombok.Data;

@Data
public class StatEntityGetDto {
    private String app;
    private String uri;
    private int hits;
}
