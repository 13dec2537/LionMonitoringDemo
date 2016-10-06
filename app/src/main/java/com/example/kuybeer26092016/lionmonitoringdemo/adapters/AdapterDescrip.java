package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.HistoryActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_descrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterDescrip extends RecyclerView.Adapter<AdapterDescrip.ViewHolder> {
    private List<Mis_descrip> mList = new ArrayList<>();
    Context context;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    String TDS,NT;
    public AdapterDescrip(Context context) {
        this.mList = mList;
        this.context = context;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_description,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mis_descrip setList = mList.get(position);
        holder.mPram.setText(String.valueOf(setList.getMo_pram()));
        holder.mAct.setText(String.valueOf(setList.getMo_act()));
        holder.mAct.setTextColor(Color.parseColor(setColor(String.valueOf(setList.getMo_act()),String.valueOf(setList.getMo_min()),String.valueOf(setList.getMo_max()))));
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
                i.putExtra("mo_id","0");
                v.getContext().startActivity(i);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_descrip> listTower) {
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
    static String setColor(String act, String min , String max){
        Double act_int = Double.valueOf(act);
        Double min_int = Double.valueOf(min);
        Double max_int = Double.valueOf(max);
        String color = "";
        if(act_int>=min_int && act_int<= max_int){
            color = "#2E7D32";
        }
        else{
            color = "#d81114";
        }
        return  color;
    }
}
