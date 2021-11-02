package com.kerinb.servergamechallenge;

class game {

    private static final int ROWS = 6;
    private static final int COLS = 9;
    private final String[][] board = new String[ROWS][COLS];
    private int currPlayer = 2;
    private int move = 0;
    private String gameState = "WAITING FOR BOTH PLAYERS TO JOIN";

    public void initialiseBoard(){
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                board[i][j] = " ";
            }
        }
    }


    public boolean countFive(int row, int delRow, int col, int delCol, String player){
        for (int i = 1; i < 5; i++) {
            if (!board[row + delRow * i][col + delCol * i].equals(player)) return false;
        }
        return true;  // There were 5 in a row!
    }

    public void checkGameState(){
        int row;
        int col;

        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                String player = board[row][col];
                if (!player.equals(" ")) {
                    if (row < ROWS-4)
                        if (countFive(row, 1, col, 0, player)) this.gameState = "PLAYER " + player + " HAS WON";

                    if (col < COLS-4) {
                        if (countFive(row, 0, col, 1, player))  this.gameState = "PLAYER " + player + " HAS WON";

                        if (row < ROWS-4) {
                            if (countFive(row, 1, col, 1, player)) this.gameState = "PLAYER " + player + " HAS WON";
                        }
                    }

                    if (col > 3 && row < ROWS-4) {
                        if (countFive(row, 1, col, -1, player)) this.gameState = "PLAYER " + player + " HAS WON";
                    }
                }
            }
        }

        if (move == ROWS * COLS) {
            this.gameState = "TIE";
        }
    }

    public void insertPiece(int col, String playerId){
        System.out.println("adding " + playerId + " to column " + col);
        for (int i = ROWS-1; i >= 0; i--) {
            if (board[i][col].equals(" ")) {
                board[i][col] = playerId;
                break;
            }
        }
        System.out.println("Done...");
        System.out.println(getGameState());
    }

    public String getGameState(){
        return this.gameState;
    }

    public void setGameState(String gameState){
        this.gameState = gameState;
    }

    public String[][] getCurrBoardState(){
        return this.board;
    }

    public int getCurrPlayer(){
        return this.currPlayer;
    }

    public void setCurrPlayer(int currPlayer){
        this.currPlayer = currPlayer;
    }

    public int getCurrMove(){
        return this.move;
    }

    public void setCurrMove(int move){
        this.move = move;
    }
}
