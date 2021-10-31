package com.kerinb.servergamechallenge;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private final PlayerRepository repository;
    private fiveInARow game = new fiveInARow();

    Controller(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {

        return "Welcome to 5 in a row! \n\n please register your player";
    }

    @PostMapping("/registerNewPlayer")
    public Player registerNewPlayer(@RequestBody String playerName) {
        // add unique player name too?
        Player newPlayer = new Player(playerName);
        System.out.println(game.getGameState() + repository.count());
        if (repository.count() <= 1){
            game.setGameState("WAITING FOR PLAYER 2 TO JOIN");
        } else if (repository.count() <= 2){
            game.setGameState("GAME CAN BEGIN");
        } else if (repository.count() >= 3) {
            game.setGameState("ERROR: TO MANY PLAYERS IN GAME");
        }
        System.out.println(game.getGameState() + repository.count());

        return repository.save(newPlayer);
    }

    @GetMapping("/players/{id}")
    Player getPlayerRecordById(@PathVariable Long id) throws Exception {

        return repository.findById(id)
                .orElseThrow(() -> new Exception("Player not found"));
    }

    @GetMapping("/gameStatus")
    String getGameStatus(){

        return game.getGameState();
    }

}