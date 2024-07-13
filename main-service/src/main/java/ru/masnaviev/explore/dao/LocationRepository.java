package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.explore.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
