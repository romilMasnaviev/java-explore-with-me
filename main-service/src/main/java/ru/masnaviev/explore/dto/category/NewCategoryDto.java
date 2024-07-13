package ru.masnaviev.explore.dto.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewCategoryDto {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
