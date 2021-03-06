package com.example.onlineshop.entity.product.boardGame;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
public class DifficultyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long difficultyLevelId;

    @Column(name = "name")
    private String name;

}
