package com.game.tictac.service;

import com.game.tictac.dto.GameRequest;
import com.game.tictac.exception.InvalidGameException;
import com.game.tictac.exception.NotFoundException;
import com.game.tictac.model.Game;
import com.game.tictac.model.GamePlay;
import com.game.tictac.model.Player;

public interface GameService {

    Game createNewGame(GameRequest gameRequest);
    Game connectToGame(Player player2, String gameId) throws InvalidGameException, NotFoundException;
    Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException;
}
