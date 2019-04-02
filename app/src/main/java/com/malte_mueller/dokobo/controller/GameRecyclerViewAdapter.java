package com.malte_mueller.dokobo.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malte_mueller.dokobo.R;

import com.malte_mueller.dokobo.model.Game;
import com.malte_mueller.dokobo.model.TableManager;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a score and makes a call to the
 * specified {@link //OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = GameRecyclerViewAdapter.class.getName();


    private final List<Integer[]> mValues;
    //private final OnListFragmentInteractionListener mListener;

    public GameRecyclerViewAdapter(List<Integer[]> items) {
        mValues = items;
        //mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.toString());
        holder.mItem = mValues.get(position);
        holder.gameNumberView.setText(String.valueOf(position + 1));
        for (int i = 0; i < mValues.get(position).length; i++){
            holder.scoreViews[i].setText(mValues.get(position)[i].toString());
        }

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView gameNumberView;
        public final TextView[] scoreViews;
        public Integer[] mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            gameNumberView = view.findViewById(R.id.game_number);
            scoreViews = new TextView[4];
            scoreViews[0] = view.findViewById(R.id.score1);
            scoreViews[1] = view.findViewById(R.id.score2);
            scoreViews[2] = view.findViewById(R.id.score3);
            scoreViews[3] = view.findViewById(R.id.score4);
        }
    }
}
