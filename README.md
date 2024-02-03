## Description

Tic-Tac-Toe with Real Application:
- Configurable Size of Board
- Login Player

#### Requires
- Java 17
- Maven

#### Setup
```
mvn install
```

#### Run
```
mvn springboot:run
```

### Tic-Tac-Toe Configuration
- Under `src/main/resources` path the configuration file `application.properties` can be found.

#### Configuration file Variables

- `game.board.size=3`: Size of board, must be bigger than three
- `player1.symbol=X`: Character of first player
- `player2.symbol=O`: Character of second player


### API Endpoint
##### 1. Star New Game

Endpoint
```http
POST http://localhost:8080/game/start
```
Request Body
```
{
    "username": "david"
}
```
| Parameter  | Type | Description                                         |
|:-----------| :--- |:----------------------------------------------------|
| `username` | `string` | **Required**. login with username to start new game |




##### 2. Connect to Game
Endpoint
```http
POST http://localhost:8080/game/connect
```
Request Body
```
{
    "player": {
        "username": "anwar"
    },
    "gameId": "e3181756-8602-487f-a9c8-5c606c755dc9"
}
```
| Parameter  | Type | Description                                    |
|:-----------| :--- |:-----------------------------------------------|
| `username` | `string` | **Required**. Can't same with username player1 |
| `gameId`   | `string` | **Required**. Get when create new game              |

##### 3. Play Game
Endpoint
```http
POST http://localhost:8080/game/play
```
Request Body
```
{
    "username": "david",
    "symbol": "X",
    "coordinateX": 0,
    "coordinateY": 2,
    "gameId": "e3181756-8602-487f-a9c8-5c606c755dc9"
}
```
| Parameter  | Type | Description                                                                                        |
|:-----------| :--- |:---------------------------------------------------------------------------------------------------|
| `username` | `string` | **Required**. username of the player who is playing                                                |
| `symbol` | `string` | **Required**. symbol have to consistent for each player base on symbol configuration in properties |
| `coordinateX` | `string` | **Required**. coordinate X of board 2-Dimensions                                                   |
| `coordinateY` | `string` | **Required**. coordinate Y of board 2-Dimensions                                                   |
| `gameId` | `string` | **Required**. Get when create new game                                                                 |

Response API
```
{
    "gameId": "75d82358-f6d8-42ff-a9d5-9f29fd0bc0b1",
    "players": [
        {
            "username": "anwar",
            "symbol": "O",
            "move": true
        },
        {
            "username": "david",
            "symbol": "X",
            "move": false
        }
    ],
    "status": "FINISHED",
    "board": [
        [
            "X",
            "X",
            "X"
        ],
        [
            "O",
            "O",
            "_"
        ],
        [
            "_",
            "_",
            "_"
        ]
    ],
    "winner": "X",
    "currentSymbol": "X"
}
```

### Rule Of Game
- Player 1 create new game by username and player 2 can connect to the game with game id already created before 
- Player 1 move first and then player 2
- When move username and symbol must be correct
- The player cannot move twice in sequence. players must wait until other players move
- Coordinates that have been filled in cannot be changed or filled in again
- Minimum size of board is 3x3

