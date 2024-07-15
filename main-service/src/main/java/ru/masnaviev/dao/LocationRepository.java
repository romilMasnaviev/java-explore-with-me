package ru.masnaviev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
