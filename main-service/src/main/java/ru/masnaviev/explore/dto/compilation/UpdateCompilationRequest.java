package ru.masnaviev.explore.dto.compilation;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}
