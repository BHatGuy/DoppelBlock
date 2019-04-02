package com.malte_mueller.dokobo.model;

import java.util.List;

/**
 * This singleton manages all exiisting Tables and provides functions like saving and sharing.
 */
public class TableManager{
    static private TableManager singleton;

    private List<Table> tables;
    private Table activeTable;

    private TableManager () {}

    public static TableManager getInstance() {
        if (singleton == null){
            singleton = new TableManager();
        }
        return singleton;
    }
}
