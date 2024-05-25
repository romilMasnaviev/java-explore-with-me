package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.explore.model.StatEntity;

public interface StatEntityRepository extends JpaRepository<StatEntity, Integer> {
}
