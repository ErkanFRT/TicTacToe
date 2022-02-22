package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private boolean winningLine = false;

    private final Paint paint = new Paint();

    private int cellSize = getWidth()/3;

    private final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try{

            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);

        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y / cellSize);
            int column = (int) Math.ceil(x / cellSize);

            if(!winningLine) {
                if (game.updateGameBoard(row, column)) {
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }

            invalidate();

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for(int column = 1; column < 3; column++){
            canvas.drawLine(cellSize * column, 0, cellSize * column, canvas.getWidth(), paint);
        }
        for(int row = 1; row < 3; row++){
            canvas.drawLine(0, cellSize * row, canvas.getWidth(), cellSize * row, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                if(game.getGameBoard()[row][column] != 0){
                    if(game.getGameBoard()[row][column] == 1){
                        drawX(canvas, row, column);
                    } else {
                        drawO(canvas, row, column);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int column){
        paint.setColor(XColor);

        canvas.drawLine((float) ((column + 1) * cellSize - cellSize * 0.15), (float) (row * cellSize + cellSize * 0.15), (float) (column * cellSize + cellSize * 0.15), (float) ((row + 1) * cellSize - cellSize * 0.15), paint);

        canvas.drawLine((float) (column * cellSize + cellSize * 0.15), (float) (row * cellSize + cellSize * 0.15), (float) ((column + 1) * cellSize - cellSize * 0.15), (float) ((row + 1) * cellSize - cellSize * 0.15), paint);
    }

    private void drawO(Canvas canvas, int row, int column){
        paint.setColor(OColor);

        canvas.drawOval((float) (column * cellSize + cellSize * 0.15), (float) (row * cellSize + cellSize * 0.15), (float) ((column * cellSize + cellSize) - cellSize * 0.15),(float) ((row * cellSize + cellSize) - cellSize * 0.15), paint);
        
    }

    private void drawHorizontalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column, row * cellSize + (float)(cellSize / 2), cellSize * 3, row * cellSize + (float)(cellSize / 2), paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column * cellSize + (float)(cellSize / 2), row, column * cellSize + (float)(cellSize / 2), cellSize * 3, paint);
    }

    private void drawDiagonalLinePositive(Canvas canvas){
        canvas.drawLine(0, cellSize * 3, cellSize * 3, 0, paint);
    }

    private void drawDiagonalLineNegative(Canvas canvas){
        canvas.drawLine(0, 0, cellSize * 3, cellSize * 3, paint);
    }

    public void setUpGame(Button playAgain, Button Home, TextView playerDisplay, String[] names){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(Home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int column = game.getWinType()[1];
        int lineType = game.getWinType()[2];

        switch (lineType){
            case 1:
                drawHorizontalLine(canvas, row, column);
                break;
            case 2:
                drawVerticalLine(canvas, row, column);
                break;
            case 3:
                drawDiagonalLinePositive(canvas);
                break;
            case 4:
                drawDiagonalLineNegative(canvas);
                break;
        }
    }
    
    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }

}
