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
    private List<Integer[]> scores;

    public Table(String name, String[] players){
        this.name = name;
        this.players = players;
        games = new ArrayList<>();
        scores = new ArrayList<>();
    }

    @Override
    public String toString(){
        return name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game g){
        games.add(g);

        // calculate score
        //TODO: solos
        Integer[] prev = new Integer[4];
        if (scores.isEmpty()){
            for (int i = 0; i < prev.length; i++) {
                prev[i] = 0;
            }
        } else {
            prev = scores.get(scores.size() - 1);
        }
        Integer[] score = new Integer[4];
        for (int i = 0; i < score.length; i++){
            if(g.isWinner(i)){
                score[i] = prev[i] + g.getScore();
            } else {
                score[i] = prev[i] - g.getScore();
            }
        }
        scores.add(score);
    }

    public List<Integer[]> getScores(){
        return scores;
    }

    public String[] getPlayers() {
        return players;
    }
}
