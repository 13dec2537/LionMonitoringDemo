package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.DetailActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterTower2  extends RecyclerView.Adapter<AdapterTower2.ViewHolder>{
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private List<Mis_monitoringitem> mList = new ArrayList<>();
    ManagerRetrofit mManager = new ManagerRetrofit();
    Context context;
    public AdapterTower2() {
        this.mList = mList;

    }

    @Override
    public AdapterTower2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tower2,parent,false);
        context = parent.getContext();
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(final AdapterTower2.ViewHolder holder, int position) {
        final Mis_monitoringitem setList = mList.get(position);
        holder.mMc_name.setText(setList.getMc_name());
        holder.mAct_1.setText(setList.getMo_act().getAct_1());
        holder.mAct_2.setText(setList.getMo_act().getAct_2());
        holder.mAct_3.setText(setList.getMo_act().getAct_3());
        holder.mAct_4.setText(setList.getMo_act().getAct_4());
        Picasso.with(context).load(IMAGEURL + setList.getMc_id() + ".jpg")
                .placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(holder.mImvMachine);
        holder.mlinearOnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),DetailActivity.class);
                i.putExtra("mc_id",setList.getMc_id());
                i.putExtra("mc_name",setList.getMc_name());
                v.getContext().startActivity(i);
            }
        });
        if(holder.mAct_1.getText().equals("") || holder.mAct_1.getText().equals(null)){
            holder.mLinearAll_txt_1.setVisibility(View.GONE);
        }
        if(holder.mAct_2.getText().equals("") || holder.mAct_2.getText().equals(null)){
            holder.mLinearAll_txt_2.setVisibility(View.GONE);
        }
        if(holder.mAct_3.getText().equals("") || holder.mAct_3.getText().equals(null)){
            holder.mLinearAll_txt_3.setVisibility(View.GONE);
        }
        if(holder.mAct_4.getText().equals("") || holder.mAct_4.getText().equals(null)){
            holder.mLinearAll_txt_4.setVisibility(View.GONE);
        }
        if(holder.mAct_1.getText().length()>0){
            holder.mLinearAll_txt_1.setVisibility(View.VISIBLE);
        }
        if(holder.mAct_2.getText().length()>0){
            holder.mLinearAll_txt_2.setVisibility(View.VISIBLE);
        }
        if(holder.mAct_3.getText().length()>0){
            holder.mLinearAll_txt_3.setVisibility(View.VISIBLE);
        }
        if(holder.mAct_4.getText().length()>0){
            holder.mLinearAll_txt_4.setVisibility(View.VISIBLE);
        }
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
        private TextView mMc_name,mAct_1,mAct_2,mAct_3,mAct_4;
        private TextView mUnit_1,mUnit_2,mUnit_3,mUnit_4;
        private ImageView mImvMachine;
        private LinearLayout mlinearOnclick,mLinearAll_txt_1,mLinearAll_txt_2,mLinearAll_txt_3,mLinearAll_txt_4;
        public ViewHolder(View itemView) {
            super(itemView);
            mImvMachine = (ImageView)itemView.findViewById(R.id.imvMachine);
            mMc_name = (TextView)itemView.findViewById(R.id.mc_name);
            mlinearOnclick = (LinearLayout)itemView.findViewById(R.id.Linearalltower2);
            mLinearAll_txt_1 = (LinearLayout)itemView.findViewById(R.id.LinearAll_txt_1);
            mLinearAll_txt_2 = (LinearLayout)itemView.findViewById(R.id.LinearAll_txt_2);
            mLinearAll_txt_3 = (LinearLayout)itemView.findViewById(R.id.LinearAll_txt_3);
            mLinearAll_txt_4 = (LinearLayout)itemView.findViewById(R.id.LinearAll_txt_4);
            mAct_1 = (TextView)itemView.findViewById(R.id.act_1);
            mAct_2 = (TextView)itemView.findViewById(R.id.act_2);
            mAct_3 = (TextView)itemView.findViewById(R.id.act_3);
            mAct_4 = (TextView)itemView.findViewById(R.id.act_4);
        }
    }
}
