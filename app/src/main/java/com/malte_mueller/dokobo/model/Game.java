package com.malte_mueller.dokobo.model;

import java.io.Serializable;

/**
 * This Class represents one single Game with its score and winners.
 */
public class Game implements Serializable {
    private int score;
    private Role[] roles;

    public static enum Role{
      WINNER, LOSER, NEUTRAL
    }

    public Game(int score, Role[] roles){
        this.score = score;
        this.roles = roles;
    }

    public boolean isWinner(int i){
        return (roles[i] == WINNER);
    }

    public Role getRole(int i){
        return roles[i];
    }

    public boolean existsWinner(){
        for(int i=0; i<roles.length; i++){
            if (roles[i] == WINNER) return true;
        }
        return false;
    }

    public boolean existsLoser(){
        for(int i=0; i<roles.length; i++){
            if (roles[i] == LOSER) return true;
        }
        return false;
    }

    public boolean existsNeutral(){
        for(int i=0; i<roles.length; i++){
            if (roles[i] == NEUTRAL) return true;
        }
        return false;
    }

    public boolean numberOfWinners(){
        int ret=0;
        for(int i=0; i<roles.length; i++){
            if (roles[i] == WINNER) ret++;
        }
        return ret;
    }

    public boolean numberOfLosers(){
        int ret=0;
        for(int i=0; i<roles.length; i++){
            if (roles[i] == LOSER) ret++;
        }
        return ret;
    }

    public boolean numberOfNeutrals(){
        int ret=0;
        for(int i=0; i<roles.length; i++){
            if (roles[i] == NEUTRAL) ret++;
        }
        return ret;
    }

//    public boolean[] getWinners(){
//        return winners;
//    }

    public int getScore(){
        return score;
    }

/*    public boolean isSolo(){
        int wc = 0;
        for (boolean b : winners) {
            if (b) wc++;
        }
        return wc == 1 || wc == 3;
    }
*/
/*    public int getSolist(){
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
*/
}
