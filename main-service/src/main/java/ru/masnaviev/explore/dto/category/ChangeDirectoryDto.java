package ru.masnaviev.explore.dto.category;

import lombok.Data;

import javax.validation.constraints.Size;
@Data
public class ChangeDirectoryDto {
    @Size(min = 1, max = 50)
    private String name;
}
