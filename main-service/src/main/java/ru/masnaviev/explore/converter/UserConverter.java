package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.explore.dto.user.NewUserDto;
import ru.masnaviev.explore.dto.user.UserDto;
import ru.masnaviev.explore.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User newUserDtoConvertToUser(NewUserDto newUser);

    UserDto userConvertToUserDto(User user);

    List<UserDto> userConvertToUserDto(List<User> users);
}
