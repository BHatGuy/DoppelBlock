package com.malte_mueller.dokobo.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.TableManager;

public class GameInputActivity extends AppCompatActivity {

    private EditText scoreInput;
    private ToggleButton[] playerButtons;
    private TableManager tableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);
        tableManager = TableManager.getInstance();


        //TODO Dynamic
        playerButtons = new ToggleButton[4];
        playerButtons[0] = findViewById(R.id.btn_player1);
        playerButtons[1] = findViewById(R.id.btn_player2);
        playerButtons[2] = findViewById(R.id.btn_player3);
        playerButtons[3] = findViewById(R.id.btn_player4);

        String[] playerNames = tableManager.getActiveTable().getPlayers();
        for (int i = 0; i < playerButtons.length; i++){
            playerButtons[i].setText(playerNames[i]);
            playerButtons[i].setTextOn(playerNames[i]);
            playerButtons[i].setTextOff(playerNames[i]);
        }

        scoreInput = findViewById(R.id.eT_score_input);
        scoreInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onGameSubmit(v);
                return false;
            }
        });
    }

    public void onGameSubmit(View view){
        //Create Game
        //TODO check if it is valid
        String text = scoreInput.getText().toString();
        if (text.length() == 0) return; //TODO toas or snackbar
        int score = Integer.valueOf(text);
        boolean[] winners = new boolean[4];

        for (int i = 0; i < playerButtons.length; i++) {
            winners[i] = playerButtons[i].isChecked();
        }

        Game g = new Game(score, winners);
        tableManager.getActiveTable().addGame(g);
        tableManager.saveTables(getApplicationContext());
        finish();
    }
}
