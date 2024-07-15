package ru.masnaviev.dto.category;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateCategoryDto {
    @Size(min = 1, max = 50, message = "Имя должно быть от {min} до {max} символов")
    private String name;
}
