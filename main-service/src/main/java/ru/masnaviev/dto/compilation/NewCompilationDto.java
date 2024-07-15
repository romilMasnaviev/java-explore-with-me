package ru.masnaviev.dto.compilation;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @NotNull(message = "Заголовок не должен быть пустым")
    @NotBlank(message = "Заголовок не должен содержать только пробельные символы")
    @Size(min = 1, max = 50, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;
}