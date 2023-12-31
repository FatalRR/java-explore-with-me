package ru.practicum.main.users.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.messages.LogMessages;
import ru.practicum.main.users.admin.service.AdminUsersService;
import ru.practicum.main.users.dto.NewUserRequest;
import ru.practicum.main.users.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUsersController {
    private final AdminUsersService service;

    @GetMapping(path = "/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.debug(LogMessages.TRY_ADMIN_GET_USERS.label);
        return service.getUsers(ids, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/users")
    public UserDto createUsers(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.debug(LogMessages.TRY_ADMIN_POST_USERS.label);
        return service.createUsers(newUserRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/users/{userId}")
    public void deleteUsers(@PathVariable(name = "userId") int userId) {
        log.debug(LogMessages.TRY_ADMIN_DELETE_USERS.label, userId);
        service.deleteUsers(userId);
    }
}