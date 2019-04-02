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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_input);

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
        List<Integer> winners = new ArrayList<>();
        TableLayout rootLinearLayout = findViewById(R.id.tableLayout);
        int count = rootLinearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = rootLinearLayout.getChildAt(i);
            if (v instanceof ToggleButton) {
                ToggleButton btn = (ToggleButton) v;
                if(btn.isChecked()){
                    winners.add(i);
                }
            }
        }

        Game g = new Game(score, winners);
        TableManager.getInstance().getActiveTable().addGame(g);

        finish();
    }
}
