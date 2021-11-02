package com.kerinb.servergamechallenge;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private final PlayerRepository repository;
    private com.kerinb.servergamechallenge.game game = new game();

    Controller(PlayerRepository repository) {
        this.repository = repository;
        game.initialiseBoard();
    }

    @GetMapping("/")
    public String index() {

        return "Welcome to 5 in a row! \n\n please register your player";
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
        System.out.println(params);
        String[] splitParams = params.replaceAll("=", "").split("%2C");
        System.out.println(splitParams[0]);
        System.out.println(splitParams[1]);
        game.insertPiece(Integer.parseInt(splitParams[0]), splitParams[1]);
        game.setCurrMove(game.getCurrMove() + 1);
        System.out.println("Curr Player: " + game.getCurrPlayer());

        if (game.getCurrPlayer() == 2){
            game.setCurrPlayer(game.getCurrPlayer() + 1);
        }  else {
            game.setCurrPlayer(game.getCurrPlayer() - 1);
        }

        System.out.println("Curr Player: " + game.getCurrPlayer());
        System.out.println("Curr Move: " + game.getCurrMove());
    }

    @GetMapping("/playersId/{id}")
    Player getPlayerRecordById(@PathVariable Long id) throws Exception {

        return repository.findById(id)
                .orElseThrow(() -> new Exception("Player not found"));
    }

    @GetMapping("/gameStatus")
    String getGameStatus(){

        return game.getGameState();
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