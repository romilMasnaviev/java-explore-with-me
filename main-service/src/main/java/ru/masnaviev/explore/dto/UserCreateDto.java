package ru.masnaviev.explore.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserCreateDto {
    @NotEmpty
    @Email
    @Size(min = 5, max = 255)
    private String email;
    @NotEmpty
    @Size(min = 2, max = 250)
    private String name;
}
