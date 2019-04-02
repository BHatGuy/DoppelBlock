package com.malte_mueller.dokobo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.TableManager;

public class GameChartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TableManager tableManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chart);
        recyclerView = (RecyclerView) findViewById(R.id.rw_games);


        tableManager = TableManager.getInstance();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameRecyclerViewAdapter(tableManager.getActiveTable().getScores());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mAdapter.notifyDataSetChanged();
    }

    public void onAddGameClicked(View v){
        //Create Intend to start the GameChartActivity
        Intent intent = new Intent(this, GameInputActivity.class);
        startActivity(intent);

    }

}
