package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterTower2  extends RecyclerView.Adapter<AdapterTower2.ViewHolder>{
    private List<Mis_monitoringitem> mList = new ArrayList<>();

    public AdapterTower2() {
        this.mList = mList;
    }

    @Override
    public AdapterTower2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tower2,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(AdapterTower2.ViewHolder holder, int position) {
        Mis_monitoringitem setList = mList.get(position);
        holder.mMc_name.setText(setList.getMc_name());
        holder.mAct_1.setText(setList.getMo_act().getAct_1());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_monitoringitem> list_monitoringitem) {
        mList.clear();
        mList.addAll(list_monitoringitem);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mMc_name,mAct_1;
        public ViewHolder(View itemView) {
            super(itemView);
            mMc_name = (TextView)itemView.findViewById(R.id.mc_name);
            mAct_1 = (TextView)itemView.findViewById(R.id.act_1);
        }
    }
}
