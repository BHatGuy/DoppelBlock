package com.malte_mueller.dokobo.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malte_mueller.dokobo.R;
import com.malte_mueller.dokobo.model.Table;

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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + holder.toString());
        holder.item = mValues.get(position);
        holder.titleView.setText(mValues.get(position).getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TODO adjust
        public final View view;
        public final TextView titleView;
        public Table item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = view.findViewById(R.id.tw_title);
        }
    }

    public interface OnListFragmentInteractionListener{
        public void onListFragmentInteraction(Table t);
    }
}
