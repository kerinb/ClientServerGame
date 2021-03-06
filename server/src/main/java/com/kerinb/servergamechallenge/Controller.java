package com.kerinb.servergamechallenge;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private final PlayerRepository repository;
    private Game game = new Game();

    Controller(PlayerRepository repository) {
        this.repository = repository;
        game.initialiseBoard();
    }

    @GetMapping("/")
    public String index() {

        return "Welcome to 5 in a row! please register your player";
    }


    @PostMapping("/endGame")
    public String endGame(@RequestBody String playerName) {
        System.out.println("player: " + playerName + " has quit the Game");
        game.setGameState("GAME HAS ENDED");
        return game.getGameState();
    }

    @PostMapping("/registerNewPlayer")
    public Player registerNewPlayer(@RequestBody String playerName) {
        // add unique player name too?
        playerName = playerName.replaceAll("=", "");
        Player newPlayer = new Player(playerName);
        System.out.println(game.getGameState() + repository.count());
        if (repository.count() <= 1){
            game.setGameState("WAITING FOR PLAYER 2 TO JOIN");
        } else if (repository.count() <= 2){
            game.setGameState("GAME HAS BEGUN");
        } else if (repository.count() >= 3) {
            game.setGameState("ERROR: TO MANY PLAYERS IN GAME");
        }
        System.out.println(game.getGameState() + repository.count());
        return repository.save(newPlayer);
    }

    @PostMapping("/makeMove")
    public void  makeMove(@RequestBody String params) {
        String[] splitParams = params.replaceAll("=", "").split("%2C");
        game.insertPiece(Integer.parseInt(splitParams[0]), splitParams[1]);
        game.setCurrMove(game.getCurrMove() + 1);

        if (game.getCurrPlayer() == 2){
            game.setCurrPlayer(game.getCurrPlayer() + 1);
        }  else {
            game.setCurrPlayer(game.getCurrPlayer() - 1);
        }

        String gameState = game.checkGameState();
        game.setGameState(gameState);
    }

    @PostMapping("/postGameStatus") // CHAMNGE FOR WGHEN CLIENT QUIRS
    public void postGameStatus(@RequestBody String playerId){
        game.setGameState("PLAYER " + playerId.replaceAll("=", "") + " QUIT");
    }

    @GetMapping("/getGameStatus")
    String getGameStatus(){

        return game.getGameState();
    }

    @GetMapping("/getPlayerCount")
    long getPlayerCount(){

        return repository.count();
    }

    @GetMapping("/boardStatus")
    String[][] getCurrBoardState(){

        return game.getCurrBoardState();
    }

    @GetMapping("/playerTurn")
    int getCurrPlayer(){

        return game.getCurrPlayer();
    }
}