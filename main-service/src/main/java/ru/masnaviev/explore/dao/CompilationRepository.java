package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masnaviev.explore.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}
