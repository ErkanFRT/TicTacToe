package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;
    private int player = 1;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private String[] playerNames = {"Player One", "Player Two"};

    private int[] winType = {-1, -1, -1};

    GameLogic(){
        gameBoard = new int[3][3];
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                gameBoard[row][column] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int column){
        if(gameBoard[row - 1][column - 1] == 0){
            gameBoard[row - 1][column - 1] = player;

            if(player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"));
            } else {
                playerTurn.setText((playerNames[0] + "'s Turn"));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck(){

        boolean isWinner = false;
        int boardFilled = 0;

        // Horizontal Check
        for(int row = 0; row < 3; row++){
            if(gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][0] == gameBoard[row][2] && gameBoard[row][0] != 0){
                isWinner = true;
                winType = new int[] {row, 0, 1};
            }
        }

        // Vertical Check
        for(int column = 0; column < 3; column++){
            if(gameBoard[0][column] == gameBoard[1][column] && gameBoard[0][column] == gameBoard[2][column] && gameBoard[0][column] != 0){
                isWinner = true;
                winType = new int[] {0, column, 2};
            }
        }

        // Positive Diagonal
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0){
            isWinner = true;
            winType = new int[] {0, 2, 3};
        }

        // Negative Diagonal
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0){
            isWinner = true;
            winType = new int[] {2, 2, 4};
        }

        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                if(gameBoard[row][column] != 0){
                    boardFilled += 1;
                }
            }
        }

        if(isWinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player - 1] + " is the Winner"));
            return true;

        } else if(boardFilled == 9) {

            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText(("Tie Game"));
            return true;

        } else {
            return false;
        }

    }

    public void resetGame(){
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                gameBoard[row][column] = 0;
            }
        }

        player = 1;

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        playerTurn.setText((playerNames[0] + "'s Turn"));
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public int[] getWinType() {
        return winType;
    }
}
