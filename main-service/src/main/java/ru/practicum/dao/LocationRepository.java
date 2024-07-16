package ru.practicum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
