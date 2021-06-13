package com.malte_mueller.doppelbock.controller;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.malte_mueller.doppelbock.R;
import com.malte_mueller.doppelbock.model.Game;
import com.malte_mueller.doppelbock.model.Table;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display tables and makes a call to the
 * specified {@link //OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TableRecyclerViewAdapter extends RecyclerView.Adapter<TableRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = TableRecyclerViewAdapter.class.getName();


    private final List<Table> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TableRecyclerViewAdapter(List<Table> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_table, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.toString());
        Table table = mValues.get(position);
        holder.item = table;
        holder.titleView.setText(table.getName());
        holder.tvPlayers.setText(String.join(", ", table.getPlayers()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.item);
                }
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onShareClick(holder.item);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onDeleteClick(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView titleView;
        final TextView tvPlayers;
        Table item;
        ImageButton btnDelete;
        ImageButton btnShare;


        ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.tw_title);
            btnDelete = view.findViewById(R.id.btnDeleteTable);
            btnShare = view.findViewById(R.id.btnShareTable);
            tvPlayers = view.findViewById(R.id.tv_players);
        }
    }

    public interface OnListFragmentInteractionListener{
        void onListFragmentInteraction(Table t);
        void onDeleteClick(Table t);
        void onShareClick(Table t);
    }
}
