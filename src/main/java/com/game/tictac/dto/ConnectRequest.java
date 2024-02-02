package com.game.tictac.dto;

import com.game.tictac.model.Player;
import lombok.Data;

@Data
public class ConnectRequest {

    private Player player;
    private String gameId;
}
