package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.HistoryActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.ViewHolder> {
    private List<Mis_detail> mList = new ArrayList<>();

    public AdapterDetail() {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_description,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_detail setList = mList.get(position);
        holder.mPram.setText(String.valueOf(setList.getMo_pram()));
        holder.mAct.setText(String.valueOf(setList.getMo_act()));
        holder.mUnit.setText(String.valueOf(setList.getMo_unit()));
        holder.mMinMax.setText(String.valueOf(setList.getMo_min()+"-"+setList.getMo_max()));

        holder.mLinearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HistoryActivity.class);
                i.putExtra("mc_name",setList.getMc_name());
                i.putExtra("mc_id",setList.getMc_id());
                i.putExtra("mo_pram",setList.getMo_pram());
                i.putExtra("mo_min",setList.getMo_min());
                i.putExtra("mo_max",setList.getMo_max());
                Toast.makeText(v.getContext(),setList.getMc_name() + " " + setList.getMc_id(),Toast.LENGTH_LONG).show();
                v.getContext().startActivity(i);
            }
        });
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
        private LinearLayout mLinearAll;
        public ViewHolder(View itemView) {
            super(itemView);
            mPram = (TextView)itemView.findViewById(R.id.pram);
            mAct = (TextView)itemView.findViewById(R.id.act);
            mUnit = (TextView)itemView.findViewById(R.id.unit);
            mMinMax = (TextView)itemView.findViewById(R.id.min_max);
            mLinearAll = (LinearLayout) itemView.findViewById(R.id.LinearAll);
        }
    }
}
