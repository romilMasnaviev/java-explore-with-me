package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.dto.user.NewUserDto;
import ru.masnaviev.dto.user.UserDto;
import ru.masnaviev.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("userConverter")
public interface UserConverter {

    User newUserDtoConvertToUser(NewUserDto newUser);

    UserDto userConvertToUserDto(User user);

    List<UserDto> userConvertToUserDto(List<User> users);
}
