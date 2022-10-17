package eu.maltemueller.doppelblock.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.maltemueller.doppelblock.R;

import eu.maltemueller.doppelblock.model.Game;
import eu.maltemueller.doppelblock.model.Table;


public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = GameRecyclerViewAdapter.class.getName();


    private final Table table;
    //private final OnListFragmentInteractionListener mListener;
    private final Context context;

    GameRecyclerViewAdapter(Context context, Table table) {
        this.table = table;
        this.context = context;
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
        int gameIndex = table.getGameIndex(game) + 1;
        int pc = game.getPlayerCount();

        holder.item = game;
        holder.gameNumberView.setText(String.valueOf(gameIndex));

        holder.initViews(game.getPlayerCount());
        for (int i = 0; i < score.length; i++){
            TextView scoreView = holder.pScoreViews[i];
            scoreView.setText(String.valueOf(score[i]));
            switch (game.getRole(i)){
                case NEUTRAL:
                    scoreView.setTextColor(holder.view.getResources().getColor(R.color.neutral));
                    break;
                case LOSER:
                    scoreView.setTextColor(holder.view.getResources().getColor(R.color.loser));
                    break;
                case WINNER:
                    scoreView.setTextColor(holder.view.getResources().getColor(R.color.winner));
                    break;
            }
            scoreView.setOnClickListener(new MenuListener(game, table));
        }

        holder.scoreView.setText(String.valueOf(game.getScore()));
        if((gameIndex-1) % (2*pc) < pc){
            holder.container.setBackgroundColor(holder.view.getResources().getColor(R.color.chartBackground1));
        } else {
            holder.container.setBackgroundColor(holder.view.getResources().getColor(R.color.chartBackground2));

        }
        holder.scoreView.setOnClickListener(new MenuListener(game, table));

    }

    @Override
    public int getItemCount() {
        return table.getGames().size();
    }

    class MenuListener implements View.OnClickListener{
        Game game;
        Table table;

        MenuListener(Game g, Table t){
            game = g;
            table = t;
        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);

            popupMenu.getMenuInflater().inflate(R.menu.menu_game, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.edit_game:
                            Intent intent = new Intent(context, GameInputActivity.class);
                            Bundle b = new Bundle();
                            b.putBoolean("edit", true);
                            b.putInt("game", table.getGameIndex(game));
                            intent.putExtras(b);
                            context.startActivity(intent);
                            //TODO refresh?
                            break;
                        case R.id.delete_game:
                            table.deleteGame(game);
                            GameRecyclerViewAdapter.this.notifyDataSetChanged();
                            //TODO undo snackbar
                            break;
                    }
                    return true;
                }
            });

            popupMenu.show();

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView gameNumberView;
        final LinearLayout container;
        final TextView scoreView;
        Game item;
        TextView[] pScoreViews;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            gameNumberView = view.findViewById(R.id.game_number);
            scoreView = view.findViewById(R.id.score);
            container = view.findViewById(R.id.ll_game);
        }

        void initViews(int count){
            if (pScoreViews != null) return;
            pScoreViews = new TextView[count];
            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < count; i++) {
                TextView scoreView = (TextView) inflater.inflate(R.layout.textview_score, null);
                container.addView(scoreView, i + 1, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 4));
                pScoreViews[i] = scoreView;
            }
        }
    }
}
