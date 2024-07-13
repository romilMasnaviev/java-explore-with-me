package ru.masnaviev.explore.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class NewUserDto {
    @NotEmpty
    @Email
    @Size(min = 6, max = 254)
    private String email;
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
