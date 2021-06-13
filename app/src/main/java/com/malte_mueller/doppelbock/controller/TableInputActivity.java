package com.malte_mueller.doppelbock.controller;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.malte_mueller.doppelbock.R;
import com.malte_mueller.doppelbock.model.Table;
import com.malte_mueller.doppelbock.model.TableManager;

import java.util.ArrayList;
import java.util.List;

public class TableInputActivity extends AppCompatActivity {
    private TableManager tableManager;
    private List<PlayerFragment> fragments;
    private EditText edName;
    private boolean edit = false;
    private Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_input);
        tableManager = TableManager.getInstance();

        edName = findViewById(R.id.et_title);

        Bundle b = getIntent().getExtras();
        if(b != null)
            edit = b.getBoolean("edit");
        if(edit){
            table = tableManager.getTables().get(b.getInt("table"));
            edName.setText(table.getName());
            for(String player: table.getPlayers()){
                addPlayerFragment();
                fragments.get(fragments.size()-1).setName(player);
            }
        }
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

        String name = edName.getText().toString();

        if (edit) {
            tableManager.deleteTable(table.getName(), getApplicationContext());
            table.setName(name);
            table.setPlayers(players);
        } else {
            table = new Table(name, players);
            tableManager.addTable(table);
            tableManager.setActiveTable(table);
            Intent intent = new Intent(this, GameChartActivity.class);
            startActivity(intent);
        }
        tableManager.saveTables(getApplicationContext());
        finish();
    }
}
