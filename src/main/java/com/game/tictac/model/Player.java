package com.game.tictac.model;

import com.game.tictac.constant.TicToe;
import lombok.Data;

@Data
public class Player {
    private String username;
    private boolean isMove = false;
    private TicToe symbol;
}
