package ru.practicum.dto.compilation;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateCompilationRequest {
    private List<Integer> events;
    private Boolean pinned;
    @Size(max = 50, message = "Заголовок должен быть не более {max} символов")
    private String title;
}