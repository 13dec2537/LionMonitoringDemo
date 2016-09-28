package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class AdapterStory extends RecyclerView.Adapter<AdapterStory.ViewHolder>{
    private List<Mis_detail> mList = new ArrayList<>();

    public AdapterStory() {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_story,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_detail setList = mList.get(position);
        holder.mPram.setText(String.valueOf(setList.getMo_pram()));
        holder.mAct.setText(String.valueOf(setList.getMo_act()));
        holder.mUnit.setText(String.valueOf(setList.getMo_unit()));
        holder.mMinMax.setText(String.valueOf(setList.getMo_min()+"-"+setList.getMo_max()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_detail> listTower) {
        mList.clear();
        mList.addAll(listTower);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mPram,mAct,mUnit,mMinMax;
        public ViewHolder(View itemView) {
            super(itemView);
            mPram = (TextView)itemView.findViewById(R.id.pram);
            mAct = (TextView)itemView.findViewById(R.id.act);
            mUnit = (TextView)itemView.findViewById(R.id.unit);
            mMinMax = (TextView)itemView.findViewById(R.id.min_max);
        }
    }
}

