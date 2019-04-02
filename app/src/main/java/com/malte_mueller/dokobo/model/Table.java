package com.malte_mueller.dokobo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents one Tabel. It has a set of played games and the Players.
 */
public class Table {
    private String name;
    private String[] players;
    private List<Game> games;

    public Table(String name, String[] players){
        this.name = name;
        this.players = players;
        games = new ArrayList<>();
    }

    @Override
    public String toString(){
        return name;
    }

    public List<Game> getGames() {
        return games;
    }
}
