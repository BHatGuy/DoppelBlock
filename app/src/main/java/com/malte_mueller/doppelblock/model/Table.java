package com.malte_mueller.doppelblock.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.malte_mueller.doppelblock.model.Game.Role.LOSER;
import static com.malte_mueller.doppelblock.model.Game.Role.NEUTRAL;
import static com.malte_mueller.doppelblock.model.Game.Role.WINNER;

/**
 * This Class represents one Table. It has a set of played games and the Players.
 */
public class Table implements Serializable {
    private String name;
    private int playerCount; //number of players in current table
    private String[] players;
    private List<Game> games;
    private transient List<Integer[]> scores;

    public Table(String name, String[] players){
        this.name = name;
        this.players = players;
        games = new ArrayList<>();
        scores = new ArrayList<>();
        playerCount = players.length;
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
        calculateScore();
    }

    public void deleteGame(Game g){
        games.remove(g);
        calculateScore();
    }

    public void updateGame(Game g){
        calculateScore();
    }

    private void calculateScore(){
        if (scores == null) scores = new ArrayList<>();
        scores.clear(); //each score is being calculated again
        for (Game g : games){

            //get previous game
            Integer[] prev = new Integer[playerCount];
            if (scores.isEmpty()){
                for (int i = 0; i < prev.length; i++) {
                    prev[i] = 0;
                }
            } else {
                prev = scores.get(scores.size() - 1);
            }

            //calculate current game
            Integer[] score = new Integer[playerCount];

            //case: only some players get score; sum needs not be zero (e. g. in Skat)
            if( !(g.existsWinner() && g.existsLoser()) ){
                for (int i = 0; i < score.length; i++){
                    if(g.getRole(i) == WINNER) score[i] = prev[i] + g.getScore();
                    else if(g.getRole(i) == LOSER) score[i] = prev[i] - g.getScore();
                    else if(g.getRole(i) == NEUTRAL) score[i] = prev[i];
                }
            }

            //case: each player gets score, depending on whether they win or lose
            //neutrals do not get score; sum needs to be zero
            else{
                //maybe (e. g. in case of a solo) scores have to be multiplied
                int factorWinner = g.numberOfLosers() / g.numberOfWinners();
                int factorLoser = g.numberOfWinners() / g.numberOfLosers();
                if (factorWinner == 0) factorWinner = 1;
                if (factorLoser == 0) factorLoser = 1;

                for (int i = 0; i < score.length; i++){
                    if(g.getRole(i) == WINNER) score[i] = prev[i] + factorWinner * g.getScore();
                    else if(g.getRole(i) == LOSER) score[i] = prev[i] - factorLoser * g.getScore();
                    else if(g.getRole(i) == NEUTRAL) score[i] = prev[i];
                }

            }

            scores.add(score);
        }
    }


    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameIndex(Game game) {
        return games.indexOf(game);
    }

    public Game getGame(int i){
        return games.get(i);
    }

    public Integer[] getScore(Game game) {
        int index = games.indexOf(game);
        calculateScore();
        return scores.get(index);
    }
}
