package com.malte_mueller.dokobo.model;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    }

    public List<Table> getTables(){
        return tables;
    }

    public void setActiveTable(Table t){
        activeTable = tables.indexOf(t);
    }

    public Table getActiveTable(){
        return tables.get(activeTable);
    }

    public void saveTables(Context context){
        for (Table table: tables) {
            String filename = table.getName() + ".dokobo";
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
                objectOut.writeObject(table);
                objectOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void loadTables(Context context){
        for (String s : context.fileList()) {
            Log.d(TAG, "loading table " + s);
            try {
                FileInputStream inputStream = context.openFileInput(s);
                ObjectInputStream objectIn = new ObjectInputStream(inputStream);
                Table t = (Table) objectIn.readObject();
                addTable(t);
                objectIn.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
