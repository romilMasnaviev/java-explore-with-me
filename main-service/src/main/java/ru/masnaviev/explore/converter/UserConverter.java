package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.explore.dto.UserCreateDto;
import ru.masnaviev.explore.dto.request.UserDto;
import ru.masnaviev.explore.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {

    User userCreateDtoConvertToUser(UserCreateDto userCreateDto);

    UserDto userConvertToUserDto(User user);

    List<UserDto> userConvertToUserDto(List<User> users);
}
