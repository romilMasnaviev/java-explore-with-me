package ru.masnaviev.explore.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.user.NewUserDto;
import ru.masnaviev.explore.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class AdminUserController {

    private UserService service;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserDto user) {
        return service.createUser(user);
    }

    @GetMapping()
    public ResponseEntity<?> getUsers(@RequestParam(name = "from", required = false, defaultValue = "0") @Min(value = 0) Integer from,
                                      @RequestParam(name = "size", required = false, defaultValue = "10") @Min(value = 1) Integer size,
                                      @RequestParam(name = "ids", required = false) List<Integer> ids) {
        return service.getUsers(from, size, ids);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "userId") Integer userId) {
        return service.deleteUser(userId);
    }

}
