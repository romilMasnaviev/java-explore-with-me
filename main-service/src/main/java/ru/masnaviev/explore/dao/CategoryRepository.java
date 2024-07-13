package ru.masnaviev.explore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.model.Category;
import ru.masnaviev.explore.model.User;

import java.util.List;

@Service
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "SELECT * FROM categories u LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Category> findAllCategories(@Param("offset") int offset, @Param("limit") int limit);
}