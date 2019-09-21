package com.malte_mueller.dokobo.controller;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Table;
import com.malte_mueller.dokobo.model.TableManager;

import java.util.ArrayList;
import java.util.List;

public class TableInputActivity extends AppCompatActivity {
    private TableManager tableManager;
    private List<PlayerFragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_input);
        tableManager = TableManager.getInstance();
    }

    public void onAddPlayer(View v){
        addPlayerFragment();
    }

    private void addPlayerFragment() {
        if (fragments == null) fragments = new ArrayList<>();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerFragment fragment = new PlayerFragment();
        fragments.add(fragment);
        fragmentTransaction.add(R.id.ll_players, fragment);
        fragmentTransaction.commit();

    }

    public void onSubmit(View v) {
        //TODO: dynamic for more players?
        String[] players = new String[fragments.size()];

        for(int i = 0; i < players.length; i++){
            players[i] = fragments.get(i).getName();
        }

        String name = ((EditText) findViewById(R.id.et_title)).getText().toString();
        Table t = new Table(name, players);
        tableManager.addTable(t);
        tableManager.setActiveTable(t);

        //Create Intend to start the GameChartActivity
        Intent intent = new Intent(this, GameChartActivity.class);
        startActivity(intent);
        finish();
    }
}
