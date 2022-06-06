package com.example.onlineshop.entity.product.boardGame;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer optionId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "options")
       List<BoardGame> boardGames = new ArrayList<>();

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BoardGame> getBoardGames() {
        return boardGames;
    }

    public void setBoardGames(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
    }

    public Option(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
    }
}
