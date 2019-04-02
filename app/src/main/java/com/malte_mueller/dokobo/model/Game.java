package com.malte_mueller.dokobo.model;

import java.io.Serializable;

/**
 * This Class represents one single Game with its score and winners.
 */
public class Game implements Serializable {
    private int score;
    private boolean[] winners;

    public Game(int score, boolean[] winners){
        this.score = score;
        this.winners = winners;
    }

    public boolean isWinner(int i){
        return winners[i];
    }

    public boolean[] getWinners(){
        return winners;
    }

    public int getScore(){
        return score;
    }

    public boolean isSolo(){
        int wc = 0;
        for (boolean b : winners) {
            if (b) wc++;
        }
        return wc == 1 || wc == 3;
    }

    public int getSolist(){
        if (!isSolo()) return -1;
        //count if more true or more false:
        int trues = 0;
        for (boolean b: winners){
            if (b) trues++;
        }
        //search for the one with has the lesser count
        boolean lookingFor = trues < winners.length-trues;
        for (int i = 0; i < winners.length; i++){
            if (winners[i] == lookingFor) return i;
        }
        return -1;
    }
}
