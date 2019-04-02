package com.malte_mueller.dokobo.model;

import java.util.List;

/**
 * This Class represents one single Game with its score and winners.
 */
public class Game {
    private int score;
    private boolean[] winners;

    public Game(int score, boolean[] winners){
        this.score = score;
        this.winners = winners;
    }

    public boolean isWinner(int i){
        return winners[i];
    }

    public int getScore(){
        return score;
    }
}
