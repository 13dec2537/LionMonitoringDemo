package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.PopActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder>{
    private List<Mis_history> mList = new ArrayList<>();
    Context context;
    public AdapterHistory() {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history,parent,false);
        context = parent.getContext();
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_history setList = mList.get(position);
        holder.mTime.setText(String.valueOf(setList.getStart_datetime()));
        holder.mMo_act.setText(String.valueOf(setList.getMo_act()));
        Integer mStatus = setList.getStatus();
        if(mStatus == 0){
            holder.Status.setBackgroundColor(Color.RED);
        }
        else if(mStatus == 1){
            holder.Status.setBackgroundColor(Color.GREEN);
        }
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
        private TextView mTime,mMo_act;
        private ImageView Status;
        public ViewHolder(View itemView) {
            super(itemView);
            mTime = (TextView)itemView.findViewById(R.id.mo_time);
            Status = (ImageView) itemView.findViewById(R.id.status);
            mMo_act = (TextView)itemView.findViewById(R.id.mo_act);
        }
    }
}

