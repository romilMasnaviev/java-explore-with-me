package ru.masnaviev.explore.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewCategoryDto {
    @NotNull(message = "Имя категории не должно быть пустым")
    @NotBlank(message = "Имя категории не должно содержать только пробельные символы")
    @Size(min = 1, max = 50, message = "Имя категории должно быть от {min} до {max} символов")
    private String name;
}
