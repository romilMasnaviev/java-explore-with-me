package ru.masnaviev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatEntityGetResponse {
    private String app;
    private String uri;
    private long hits;
}
