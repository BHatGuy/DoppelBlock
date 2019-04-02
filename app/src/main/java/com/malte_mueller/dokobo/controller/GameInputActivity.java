package com.malte_mueller.dokobo.controller;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.TableManager;

import java.util.ArrayList;
import java.util.List;

public class GameInputActivity extends AppCompatActivity {

    private EditText scoreInput;
    private ToggleButton[] playerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);

        //TODO Dynamic
        playerButtons = new ToggleButton[4];
        playerButtons[0] = findViewById(R.id.btn_player1);
        playerButtons[1] = findViewById(R.id.btn_player2);
        playerButtons[2] = findViewById(R.id.btn_player3);
        playerButtons[3] = findViewById(R.id.btn_player4);

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
        int score = Integer.valueOf(scoreInput.getText().toString());
        boolean[] winners = new boolean[4];

        for (int i = 0; i < playerButtons.length; i++) {
            winners[i] = playerButtons[i].isChecked();
        }

        Game g = new Game(score, winners);
        TableManager tm = TableManager.getInstance();
        tm.getActiveTable().addGame(g);
        tm.saveTables(getApplicationContext());
        finish();
    }
}
