package com.kerinb.servergamechallenge;

public class game {

    private static final int ROWS = 9;
    private static final int COLS = 9;
    private final String[][] board = new String[ROWS][COLS];
    private int currPlayer = 2;
    private int move = 0;
    private String gameState = "WAITING FOR BOTH PLAYERS TO JOIN";

    public void initialiseBoard(){
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < ROWS; j++){
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

    public String checkGameState(){
        int row;
        int col;

        for (row = 0; row < ROWS; row++) {
            for (col = 0; col < COLS; col++) {
                String player = board[row][col];
                if (!player.equals(" ")) {
                    // look at 4 kinds of rows of 5
                    //  1. a column going up
                    //  2. a row going to the right
                    //  3. a diagonal up and to the right
                    //  4. a diagonal up and to the left

                    if (row < ROWS-4) // Look up
                        if (countFive(row, 1, col, 0, player)) return "PLAYER " + player + " HAS WON";

                    if (col < COLS-4) { // row to right
                        if (countFive(row, 0, col, 1, player))  return "PLAYER " + player + " HAS WON";

                        if (row < ROWS-4) { // diagonal up to right
                            if (countFive(row, 1, col, 1, player)) return "PLAYER " + player + " HAS WON";
                        }
                    }

                    if (col > 3 && row < ROWS-4) { // diagonal up left
                        if (countFive(row, 1, col, -1, player)) return "PLAYER " + player + " HAS WON";
                    }
                }//endif position wasn't empty
            }//endfor row
        }//endfor col

        //... Neither player has won, it's tie if there are empty positions.
        //    Game is finished if total moves equals number of positions.
        if (move == ROWS * COLS) {
            return "TIE"; // Game tied.  No more possible moves.
        } else {
            return "CONTINUE";  // More to play.
        }

    }

    public void insertPiece(int col, String playerId){
        System.out.println("adding " + playerId + " to column " + col);
        int row = 8;
        for (int i = ROWS-1; i >= 0; i--) {
            if (board[i][col].equals(" ")) {
                board[i][col] = playerId;
                row = i;
                break;
            }
        }
        System.out.println("Done...");
        System.out.println(checkGameState());
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
