package ru.masnaviev.explore.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.UserCreateDto;
import ru.masnaviev.explore.dto.request.UserDto;
import ru.masnaviev.explore.service.AdminService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@Slf4j
@Validated
public class AdminController {

    private AdminService service;

    @PostMapping("/users")
    public UserDto createUser(@RequestBody @Valid UserCreateDto user) {
        return service.createUser(user);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(name = "from", required = false) @Min(value = 0) Integer from,
                                  @RequestParam(name = "size", required = false) @Min(value = 1) Integer size,
                                  @RequestParam(name = "ids", required = false) List<Integer> ids) {
        return service.getUsers(from, size, ids);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        return service.deleteUser(userId);
    }

}
