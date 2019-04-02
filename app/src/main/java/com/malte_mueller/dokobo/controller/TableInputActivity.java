package com.malte_mueller.dokobo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Table;
import com.malte_mueller.dokobo.model.TableManager;

public class TableInputActivity extends AppCompatActivity {
    private TableManager tableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_input);
        tableManager = TableManager.getInstance();
    }

    public void onSubmit(View v){
        //TODO: dynamic for more players?
        String[] players = new String[4];
        players[0] = ((EditText) findViewById(R.id.et_player1)).getText().toString();
        players[1] = ((EditText) findViewById(R.id.et_player2)).getText().toString();
        players[2] = ((EditText) findViewById(R.id.et_player3)).getText().toString();
        players[3] = ((EditText) findViewById(R.id.et_player4)).getText().toString();
        String name = ((EditText) findViewById(R.id.et_title)).getText().toString();
        Table t = new Table(name, players);
        tableManager.addTable(t);
        tableManager.setActiveTable(t);

        //Create Intend to start the GameChartActivity
        Intent intent = new Intent(this, GameChartActivity.class);
        startActivity(intent);

    }
}
