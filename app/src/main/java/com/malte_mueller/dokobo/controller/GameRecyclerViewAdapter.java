package com.malte_mueller.dokobo.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malte_mueller.dokobo.R;

import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.Table;


public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = GameRecyclerViewAdapter.class.getName();


    private final Table table;
    //private final OnListFragmentInteractionListener mListener;

    GameRecyclerViewAdapter(Table table) {
        this.table = table;
        //mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Game game = table.getGames().get(position);
        Integer[] score = table.getScore(game);
        int gameIndex = table.getGameIndex(game);

        holder.item = game;
        holder.gameNumberView.setText(String.valueOf(gameIndex));
        LayoutInflater inflater = (LayoutInflater) holder.view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < score.length; i++){
            if(holder.stuffed) continue;
            TextView scoreView = (TextView) inflater.inflate(R.layout.textview_score, null);
            holder.container.addView(scoreView, 1, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4));
            scoreView.setText(String.valueOf(score[i]));
            if(game.isWinner(i)){
                scoreView.setTextColor(holder.view.getResources().getColor(R.color.winner));
            } else {
                scoreView.setTextColor(holder.view.getResources().getColor(R.color.loser));
            }
        }
        holder.stuffed = true;

        holder.scoreView.setText(String.valueOf(game.getScore()));
        if((gameIndex-1) % 8 < 4){
            holder.container.setBackgroundColor(holder.view.getResources().getColor(R.color.chartBackground1));
        } else {
            holder.container.setBackgroundColor(holder.view.getResources().getColor(R.color.chartBackground2));

        }
    }

    @Override
    public int getItemCount() {
        return table.getGames().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView gameNumberView;
        final LinearLayout container;
        final TextView scoreView;
        Game item;
        boolean stuffed = false;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            gameNumberView = view.findViewById(R.id.game_number);
            scoreView = view.findViewById(R.id.score);
            container = view.findViewById(R.id.ll_game);
        }
    }
}
