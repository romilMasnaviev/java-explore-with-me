package ru.masnaviev.explore.dto.request;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String name;
    private String email;
}
