package ru.practicum.main.users.admin.service;

import ru.practicum.main.users.dto.NewUserRequest;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.model.User;

import java.util.List;

public interface AdminUsersService {
    List<UserDto> getUsers(List<Integer> ids, int from, int size);

    UserDto createUsers(NewUserRequest newUserRequest);

    void deleteUsers(int userId);

    User getUserById(int userId);
}