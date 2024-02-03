package com.game.tictac.service;

import com.game.tictac.dto.GameRequest;
import com.game.tictac.exception.InvalidGameException;
import com.game.tictac.exception.NotFoundException;
import com.game.tictac.model.Game;
import com.game.tictac.model.GamePlay;
import com.game.tictac.model.Player;
import com.game.tictac.constant.TicToe;
import com.game.tictac.storage.GameStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.game.tictac.constant.ErrorMessage.*;
import static com.game.tictac.constant.GameStatus.*;


@Service
@Slf4j
public class GameServiceImpl implements GameService {

    @Value("${game.board.size}")
    private int boardSize;
    @Value("${player1.symbol}")
    private String player1Symbol;
    @Value("${player2.symbol}")
    private String player2Symbol;


    @Override
    public Game createNewGame(GameRequest gameRequest) {

        // Initial Board
        String[][] board = new String[boardSize][boardSize];
        for (String[] strings : board) {
            Arrays.fill(strings, "_");
        }
        log.info("Board Initial with {} X {}", boardSize, boardSize);

        // Create Player
        Player player = new Player();
        player.setUsername(gameRequest.getUsername());
        player.setSymbol(Enum.valueOf(TicToe.class, player1Symbol));
        player.setMove(true);

        Set<Player> players = new HashSet<>();
        players.add(player);

        // Create New Game
        Game game = new Game();
        game.setBoard(board);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayers(players);
        game.setStatus(NEW);
        GameStorage.getInstance().setGame(game);
        log.info("Success Create Game with Game ID {}", game.getGameId());
        return game;
    }


    @Override
    public Game connectToGame(Player player2, String gameId) throws InvalidGameException, NotFoundException {

        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new NotFoundException(GAME_NOT_FOUND);
        }
        Game game = GameStorage.getInstance().getGames().get(gameId);
        player2.setSymbol(Enum.valueOf(TicToe.class, player2Symbol));

        if (game.getPlayers().size() != 1) {
            throw new InvalidGameException(GAME_NOT_VALID);
        }

        // The Player's Username Must be Unique
        boolean usernameExist = game.getPlayers().stream().anyMatch(player -> player.getUsername().equals(player2.getUsername()));
        if (usernameExist) {
            throw new InvalidGameException(USERNAME_ALREADY_EXIST);
        }

        // Connect Player2 to Game
        game.getPlayers().add(player2);
        game.setStatus(IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        log.info("Player 1 and Player 2 Already Connected");
        return game;
    }


    @Override
    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {

        Game game = gameInstanceValidation(gamePlay);

        // Coordinates Can Only be Filled in Once
        String[][] board = game.getBoard();
        for (TicToe ticToe : TicToe.values()) {
            if (ticToe.getValue().equals(board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()])) {
                throw new InvalidGameException(COORDINATE_ALREADY_EXIST);
            }
        }

        movementValidation(gamePlay, game);

        // Winner Calculation
        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getSymbol().getValue();
        GameCalculator winnerCalculation = new WinnerCalculation(board);
        final String winner = winnerCalculation.getWinner(gamePlay.getSymbol().getValue());
        game.setCurrentSymbol(gamePlay.getSymbol());
        log.info("The Winner is {}", winner);

        if (winner != null) {
            game.setWinner(Enum.valueOf(TicToe.class, winner));
            game.setStatus(FINISHED);
        }

        GameStorage.getInstance().setGame(game);
        return game;
    }


    private void movementValidation(GamePlay gamePlay, Game game) throws InvalidGameException {

        // Checking Allowed Movement && Correct Symbol
        boolean moveAllowed = false;
        for (Player player : game.getPlayers()) {

            if (player.getUsername().equals(gamePlay.getUsername()) && player.isMove()) {

                if (!gamePlay.getSymbol().equals(player.getSymbol())) {
                    throw new InvalidGameException(WRONG_SYMBOL);
                }
                player.setMove(false);
                moveAllowed = true;
            }

            if (!player.getUsername().equals(gamePlay.getUsername()) && !player.isMove()) {
                player.setMove(true);
            }
        }

        if (!moveAllowed) {
            throw new InvalidGameException("Player " + gamePlay.getUsername() + " Not Allowed to Move");
        }
    }


    private Game gameInstanceValidation(GamePlay gamePlay) throws NotFoundException, InvalidGameException {

        if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
            throw new NotFoundException(GAME_NOT_FOUND);
        }

        Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if (game.getStatus().equals(FINISHED) || game.getStatus().equals(NEW)) {
            throw new InvalidGameException(GAME_FINISHED);
        }

        return game;
    }
}
