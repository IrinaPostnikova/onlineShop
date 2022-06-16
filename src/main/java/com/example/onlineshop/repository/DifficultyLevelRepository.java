package com.example.onlineshop.repository;

import com.example.onlineshop.entity.product.boardGame.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel,Long> {
}
