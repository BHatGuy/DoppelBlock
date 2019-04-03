package com.malte_mueller.dokobo.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Table;
import com.malte_mueller.dokobo.model.TableManager;

public class TableSelectActivity extends AppCompatActivity implements TableRecyclerViewAdapter.OnListFragmentInteractionListener{
    private static final String TAG = TableSelectActivity.class.getName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TableManager tableManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_select);
        recyclerView = findViewById(R.id.rv_tables);


        tableManager = TableManager.getInstance();
        tableManager.loadTables(getApplicationContext());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new TableRecyclerViewAdapter(tableManager.getTables(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: ");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListFragmentInteraction(Table t) {
        tableManager.setActiveTable(t);
        Log.d(TAG, "onListFragmentInteraction: " + tableManager.getActiveTable().getName());
        Intent intent = new Intent(this, GameChartActivity.class);
        startActivity(intent);
    }

    public void onAddTable(View v){
        Intent intent = new Intent(this, TableInputActivity.class);
        startActivity(intent);
    }
}
