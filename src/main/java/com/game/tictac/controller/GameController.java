package com.game.tictac.controller;


import com.game.tictac.dto.ConnectRequest;
import com.game.tictac.dto.GameRequest;
import com.game.tictac.exception.InvalidGameException;
import com.game.tictac.exception.NotFoundException;
import com.game.tictac.model.Game;
import com.game.tictac.model.GamePlay;
import com.game.tictac.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;


    @PostMapping("/start")
    public ResponseEntity<Game> createNewGame(@RequestBody GameRequest request) {

        return ResponseEntity.ok(gameService.createNewGame(request));
    }


    @PostMapping("/connect")
    public ResponseEntity<Game> connectToGame(@RequestBody ConnectRequest request) throws InvalidGameException, NotFoundException {

        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }


    @PostMapping("/play")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {

        Game game = gameService.gamePlay(request);
        return ResponseEntity.ok(game);
    }
}
