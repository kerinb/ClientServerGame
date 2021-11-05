package com.kerinb.servergamechallenge;

import org.junit.jupiter.api.Test;

import java.util.Arrays;


class gameTests {
    @Test
    void testSetBoard() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {"1", "1", "1", "1", "1", " ", " ", " ", " "}
        };
        Game game = new Game();
        game.setBoard(testBoard);
        String[][] currBoard = game.getCurrBoardState();
        assert(testBoard == currBoard);
    }


    @Test
    void testInitialiseBoard() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "}
        };
        Game game = new Game();
        game.initialiseBoard();

        String[][] currBoard = game.getCurrBoardState();
        assert(Arrays.deepToString(currBoard).equals(Arrays.deepToString(testBoard)));
    }


    @Test
    void testSetCurrPlayer(){
        Game game = new Game();
        game.setCurrPlayer(1);
        assert(1 == game.getCurrPlayer());
    }


    @Test
    void testSetCurrMove(){
        Game game = new Game();
        game.setCurrMove(9);
        assert(9 == game.getCurrMove());
    }

    @Test
    void testSetGameState(){
        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        assert("GAME HAS BEGUN".equals(game.getGameState()));
    }


    @Test
    void testInsertPiece(){
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {"1", " ", " ", " ", " ", " ", " ", " ", " "}};

        Game game = new Game();
        game.initialiseBoard();
        game.insertPiece(0, "1");
        String[][] currBoard = game.getCurrBoardState();
        assert(Arrays.deepToString(currBoard).equals(Arrays.deepToString(testBoard)));
    }

    @Test
    void testFindHorizontalWinner() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {" ", "2", " ", "2", " ", " ", " ", " ", " "},
                {" ", "2", "2", "2", " ", " ", " ", " ", " "},
                {"3", "3", "3", "3", "3", " ", " ", " ", " "}
        };

        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        game.setCurrPlayer(3);
        game.setCurrMove(10);
        game.setBoard(testBoard);
        String gameState = game.checkGameState();
        System.out.println(gameState);
        assert(gameState.equals("PLAYER 3 HAS WON"));
    }


    @Test
    void testFindVeritcalWinner() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {"3", " ", " ", " ", " ", " ", " ", " ", " "},
                {"3", " ", " ", " ", " ", " ", " ", " ", " "},
                {"3", "2", " ", "2", " ", " ", " ", " ", " "},
                {"3", "2", "2", "2", " ", " ", " ", " ", " "},
                {"3", "2", "3", "2", "3", " ", " ", " ", " "}
        };

        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        game.setCurrPlayer(3);
        game.setCurrMove(10);
        game.setBoard(testBoard);
        String gameState = game.checkGameState();
        System.out.println(gameState);
        assert(gameState.equals("PLAYER 3 HAS WON"));
    }

    @Test
    void testFindDiagonalWinner1() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {"3", " ", " ", " ", " ", " ", " ", " ", " "},
                {"2", "3", " ", " ", " ", " ", " ", " ", " "},
                {"2", "2", "3", "2", " ", " ", " ", " ", " "},
                {"2", "2", "2", "3", " ", " ", " ", " ", " "},
                {"2", "2", "3", "2", "3", " ", " ", " ", " "}
        };

        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        game.setCurrPlayer(3);
        game.setCurrMove(10);
        game.setBoard(testBoard);
        String gameState = game.checkGameState();
        System.out.println(gameState);
        assert(gameState.equals("PLAYER 3 HAS WON"));
    }


    @Test
    void testFindDiagonalWinner2() {
        String[][] testBoard = {
                {" ", " ", " ", " ", " ", " ", " ", " ", " "},
                {"3", " ", " ", " ", "3", " ", " ", " ", " "},
                {"2", "2", " ", "3", "3", " ", " ", " ", " "},
                {"2", "2", "3", "2", "2", " ", " ", " ", " "},
                {"2", "3", "2", "3", "2", " ", " ", " ", " "},
                {"3", "2", "3", "2", "3", " ", " ", " ", " "}
        };

        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        game.setCurrPlayer(3);
        game.setCurrMove(10);
        game.setBoard(testBoard);
        String gameState = game.checkGameState();
        System.out.println(gameState);
        assert(gameState.equals("PLAYER 3 HAS WON"));
    }

    @Test
    void testFindTie() {
        String[][] testBoard = {
                {"2", "3", "2", "3", "2", "3", "2", "3", "2"},
                {"2", "3", "2", "3", "2", "3", "2", "3", "2"},
                {"2", "3", "2", "3", "2", "3", "2", "3", "2"},
                {"3", "2", "3", "2", "3", "2", "3", "2", "3"},
                {"3", "2", "3", "2", "3", "2", "3", "2", "3"},
                {"3", "2", "3", "2", "3", "2", "3", "2", "3"}
        };

        Game game = new Game();
        game.setGameState("GAME HAS BEGUN");
        game.setCurrPlayer(3);
        game.setCurrMove(54);
        game.setBoard(testBoard);
        String gameState = game.checkGameState();
        System.out.println(gameState);
        assert(gameState.equals("TIE"));
    }
}
