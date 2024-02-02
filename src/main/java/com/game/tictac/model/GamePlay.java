package com.game.tictac.model;

import com.game.tictac.constant.TicToe;
import lombok.Data;

@Data
public class GamePlay {

    private TicToe symbol;
    private int coordinateX;
    private int coordinateY;
    private String gameId;
    private String username;
}
