package com.kerinb.servergamechallenge;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private final PlayerRepository repository;

    Controller(PlayerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "Welcome to 5 in a row!";
    }

    @PostMapping("/registerNewPlayer")
    public Player registerNewPlayer(@RequestBody Player newPlayer) {
        return repository.save(newPlayer);
    }

    @GetMapping("/players/{id}")
    Player getPlayerRecordById(@PathVariable Long id) throws Exception {

        return repository.findById(id)
                .orElseThrow(() -> new Exception("Player not found"));
    }

}