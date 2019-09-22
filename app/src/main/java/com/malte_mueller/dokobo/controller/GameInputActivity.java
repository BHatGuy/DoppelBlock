package com.malte_mueller.dokobo.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.TableManager;

import java.util.zip.Inflater;

public class GameInputActivity extends AppCompatActivity {

    private EditText scoreInput;
    private ResultButton[] playerButtons;
    private TableManager tableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);
        tableManager = TableManager.getInstance();

        LinearLayout ll = findViewById(R.id.ll_winBtns);

        String[] playerNames = tableManager.getActiveTable().getPlayers();

        playerButtons = new ResultButton[playerNames.length];
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < playerNames.length; i++){
            ResultButton btn = (ResultButton) inflater.inflate(R.layout.button_resultbutton, null);
            btn.setText(playerNames[i]);
            playerButtons[i] = btn;
            ll.addView(btn, i, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        }

        scoreInput = findViewById(R.id.eT_score_input);
        scoreInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onGameSubmit(v);
                return false;
            }
        });
        if(scoreInput.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onGameSubmit(View view) {
        //Create Game
        //TODO check if it is valid
        String text = scoreInput.getText().toString();
        if (text.length() == 0) return; //TODO toast or snack-bar
        int score = Integer.valueOf(text);
        Game.Role[] roles = new Game.Role[tableManager.getActiveTable().getPlayers().length];

        for (int i = 0; i < roles.length; i++){
            roles[i] = playerButtons[i].getState();
        }

        Game g = new Game(score, roles);
        tableManager.getActiveTable().addGame(g);
        tableManager.saveTables(getApplicationContext());
        finish();
    }
}
