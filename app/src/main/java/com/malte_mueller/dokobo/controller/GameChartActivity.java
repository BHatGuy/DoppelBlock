package com.malte_mueller.dokobo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.TableManager;

public class GameChartActivity extends AppCompatActivity {
    private static final String TAG = GameChartActivity.class.getName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TableManager tableManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chart);
        recyclerView = findViewById(R.id.rv_games);


        tableManager = TableManager.getInstance();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameRecyclerViewAdapter(this, tableManager.getActiveTable());
        recyclerView.setAdapter(mAdapter);

        LinearLayout headline = findViewById(R.id.ll_headline);
        LayoutInflater inflater = getLayoutInflater();
        String[] playerNames = tableManager.getActiveTable().getPlayers();
        for (int i = 0; i < playerNames.length; i++){
            TextView nameTV = (TextView) inflater.inflate(R.layout.textview_pname, null);
            nameTV.setText(playerNames[i]);

            headline.addView(nameTV, i+1, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4));
        }






    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mAdapter.notifyDataSetChanged();
    }

    public void onAddGameClicked(View v){
        //Create Intend to start the GameChartActivity
        Intent intent = new Intent(this, GameInputActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("edit", false);
        intent.putExtras(b);
        startActivity(intent);

    }

}
