package ru.practicum.main.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.users.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
}