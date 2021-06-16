package eu.maltemueller.doppelblock.model;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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

    private void saveTable(Table table, Context context){
        // TODO handle duplicates
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

    public void saveTables(Context context){
        for (Table table: tables) {
            saveTable(table, context);
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

        public void importTable(Uri uri, Context context){
        try {
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            byte[] data = new byte[inputStream.available()];
            int n = inputStream.read(data);
            inputStream.close();
            String json = new String(data);
            Table table = gson.fromJson(json, Table.class);
            tables.add(table);
            saveTable(table, context);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteTable(String name, Context context){
        String filename = name + ".json";
        File f = new File(context.getFilesDir(), filename);
        boolean res = f.delete();
    }

    public void deleteTable(Table table, Context context){
        tables.remove(table);
        deleteTable(table.getName(), context);
    }

    public File getFile(Table table, Context context){
        String filename = table.getName() + ".json";
        return new File(context.getFilesDir(), filename);
    }
}
