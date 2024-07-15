package ru.masnaviev.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NewUserDto {
    @NotEmpty(message = "Поле 'email' не должно быть пустым")
    @Email(message = "Поле 'email' должно быть действительным адресом электронной почты")
    @Size(min = 6, max = 254, message = "Длина поля 'email' должна быть от 6 до 254 символов")
    private String email;

    @NotEmpty(message = "Поле 'name' не должно быть пустым")
    @NotBlank(message = "Поле 'name' не должно содержать только пробелы")
    @Size(min = 2, max = 250, message = "Длина поля 'name' должна быть от 2 до 250 символов")
    private String name;
}