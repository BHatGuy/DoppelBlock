package com.malte_mueller.doppelblock.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.malte_mueller.doppelblock.BuildConfig;
import com.malte_mueller.doppelblock.R;
import com.malte_mueller.doppelblock.model.Table;
import com.malte_mueller.doppelblock.model.TableManager;

import java.io.File;

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

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action)) {
            handleViewIntent(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean res = super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_add){
            onAddTable();
            return true;
        }
        return res;
    }

    private void handleViewIntent(Intent intent){
        //TODO toas and dialog
        Uri fileUri = intent.getData();
        if (fileUri == null) return;
        tableManager.importTable(fileUri, getApplicationContext());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListFragmentInteraction(Table t) {
        tableManager.setActiveTable(t);
        Log.d(TAG, "onListFragmentInteraction: " + tableManager.getActiveTable().getName());
        Intent intent = new Intent(this, GameChartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(final Table t) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are your sure?").setTitle("Continue deleting?");

        // Add the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Continue:
                tableManager.deleteTable(t, getApplicationContext());
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onShareClick(Table t) {
        // create new Intent
        Intent intent = new Intent(Intent.ACTION_SEND);

        // set flag to give temporary permission to external app to use your FileProvider
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
        File f = tableManager.getFile(t, getApplicationContext());
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID, f);

        // I am opening a PDF file so I give it a valid MIME type
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("application/json");

        // validate that the device can open your File!
        PackageManager pm = getPackageManager();
        if (intent.resolveActivity(pm) != null) {
            startActivity(Intent.createChooser(intent, "Send save"));
        }
    }

    public void onAddTable(){
        Intent intent = new Intent(this, TableInputActivity.class);
        startActivity(intent);
    }
}
