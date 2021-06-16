package com.malte_mueller.doppelblock.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.malte_mueller.doppelblock.R;
import com.malte_mueller.doppelblock.model.Game;


public class ResultButton extends android.support.v7.widget.AppCompatButton {


    private Game.Role state;

    public ResultButton(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        state = Game.Role.LOSER;

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state){
                    case LOSER:
                        changeState(Game.Role.NEUTRAL);
                        break;
                    case WINNER:
                        changeState(Game.Role.LOSER);
                        break;
                    case NEUTRAL:
                        changeState(Game.Role.WINNER);
                        break;
                }
            }
        });

        changeState(Game.Role.NEUTRAL);
    }

    public ResultButton(Context context) {
        this(context, null);
    }

    private void changeState(Game.Role s){
        state = s;
        switch (state){
            case LOSER:
                setBackgroundColor(getResources().getColor(R.color.loser));
                break;
            case WINNER:
                setBackgroundColor(getResources().getColor(R.color.winner));
                break;
            case NEUTRAL:
                setBackgroundColor(getResources().getColor(R.color.neutral));
                break;
        }
    }

    public Game.Role getState(){
        return state;
    }

    public void setState(Game.Role state) {
        changeState(state);
    }
}
