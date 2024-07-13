package ru.masnaviev.explore.dto.compilation;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}
