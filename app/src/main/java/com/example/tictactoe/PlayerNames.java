package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PlayerNames extends AppCompatActivity {

    private EditText playerOne;
    private EditText playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_names);

        playerOne = findViewById(R.id.PlayerOneName);
        playerTwo = findViewById(R.id.PlayerTwoName);
    }

    public void submitButtonClick(View view){
        String playerOneName = playerOne.getText().toString();
        String playerTwoName = playerTwo.getText().toString();

        Intent intent = new Intent(this, GameScreen.class);

        intent.putExtra("PLAYER_NAMES", new String[] {playerOneName, playerTwoName});
        startActivity(intent);
    }
}