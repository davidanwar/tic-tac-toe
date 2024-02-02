package com.game.tictac.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicToe {
    X("X"), O("O");

    private final String value;
}
