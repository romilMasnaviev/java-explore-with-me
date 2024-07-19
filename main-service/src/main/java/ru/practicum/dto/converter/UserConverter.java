package ru.practicum.dto.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.user.NewUserDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("userConverter")
public interface UserConverter {

    User newUserDtoConvertToUser(NewUserDto newUser);

    UserDto userConvertToUserDto(User user);

    List<UserDto> userConvertToUserDto(List<User> users);
}
