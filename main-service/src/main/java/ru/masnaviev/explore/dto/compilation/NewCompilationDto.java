package ru.masnaviev.explore.dto.compilation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
