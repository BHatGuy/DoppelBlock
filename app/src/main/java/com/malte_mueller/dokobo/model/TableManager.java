package com.malte_mueller.dokobo.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * This singleton manages all exiisting Tables and provides functions like saving and sharing.
 */
public class TableManager{
    private static final String TAG = TableManager.class.getName();

    static private TableManager singleton;

    private List<Table> tables;
    private int activeTable;

    private TableManager () {
        tables = new ArrayList<>();
    }

    public static TableManager getInstance() {
        if (singleton == null){
            singleton = new TableManager();
        }
        return singleton;
    }

    public void addTable(Table t){
        tables.add(t);
        Log.d(TAG, "addTable: " + t.toString());
    }

    public Table[] getTables(){
        return (Table[]) tables.toArray();
    }

    public void setActiveTable(Table t){
        activeTable = tables.indexOf(t);
    }
}
