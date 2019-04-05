package com.malte_mueller.dokobo.model;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.google.gson.Gson;

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

    private final Gson gson;

    private TableManager () {
        tables = new ArrayList<>();
        gson = new Gson();
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
            String filename = table.getName() + ".json";
            FileOutputStream outputStream;
            try {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                byte[] data = gson.toJson(table).getBytes();
                outputStream.write(data);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void loadTables(Context context){
        tables.clear();
        for (String s : context.fileList()) {
            Log.d(TAG, "loading table " + s);
            try {
                FileInputStream inputStream = context.openFileInput(s);
                byte[] data = new byte[inputStream.available()];
                int n = inputStream.read(data);
                String json = new String(data);
                tables.add(gson.fromJson(json, Table.class));
                inputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void deleteTable(Table table, Context context){
        tables.remove(table);
        String filename = table.getName() + ".json";
        File f = new File(context.getFilesDir(), filename);
        boolean res = f.delete();
        Log.d(TAG, "deleteTable: " + res);

    }

    public File getFile(Table table, Context context){
        String filename = table.getName() + ".json";
        return new File(context.getFilesDir(), filename);
    }
}
