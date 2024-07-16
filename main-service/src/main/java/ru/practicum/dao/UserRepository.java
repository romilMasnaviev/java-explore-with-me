package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.practicum.model.User;

import java.util.List;

@Service
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users u LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAllUsers(@Param("offset") int offset, @Param("limit") int limit);
}
