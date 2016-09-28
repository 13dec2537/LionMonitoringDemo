package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder>{
    private List<Mis_history> mList = new ArrayList<>();

    public AdapterHistory() {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_history setList = mList.get(position);
        holder.mPram.setText(String.valueOf(setList.getMo_pram()));
        holder.mAct.setText(String.valueOf(setList.getMo_act()));
        holder.mDateStart.setText(String.valueOf(setList.getStart_datetime()));
        holder.mDateEnd.setText(String.valueOf(setList.getEnd_datetime()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_history> listTower) {
        mList.clear();
        mList.addAll(listTower);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mPram,mAct,mDateStart,mDateEnd;
        public ViewHolder(View itemView) {
            super(itemView);
            mPram = (TextView)itemView.findViewById(R.id.mo_pram);
            mAct = (TextView)itemView.findViewById(R.id.mo_act);
            mDateStart = (TextView)itemView.findViewById(R.id.mo_datestart);
            mDateEnd = (TextView)itemView.findViewById(R.id.mo_dateend);
        }
    }
}

