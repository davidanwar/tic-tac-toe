package com.game.tictac.model;

import com.game.tictac.constant.GameStatus;
import com.game.tictac.constant.TicToe;
import lombok.Data;

import java.util.Set;

@Data
public class Game {

    private String gameId;
    private Set<Player> players;
    private GameStatus status;
    private String[][] board;
    private TicToe winner;
    private TicToe currentSymbol;
}
