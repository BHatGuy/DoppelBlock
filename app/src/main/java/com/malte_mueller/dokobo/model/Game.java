package com.malte_mueller.dokobo.model;

import java.util.List;

/**
 * This Class represents one single Game with its score and winners.
 */
public class Game {
    private int score;
    private List<Integer> winners;

    public Game(int score, List<Integer> winners){
        this.score = score;
        this.winners = winners;
    }
}
