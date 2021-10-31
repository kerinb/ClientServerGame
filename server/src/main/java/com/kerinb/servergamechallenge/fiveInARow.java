package com.kerinb.servergamechallenge;

public class fiveInARow {

    private String gameState = "WAITING FOR BOTH PLAYERS TO JOIN";

    public String getGameState(){
        return this.gameState;
    }

    public void setGameState(String gameState){
        this.gameState = gameState;
    }
}
