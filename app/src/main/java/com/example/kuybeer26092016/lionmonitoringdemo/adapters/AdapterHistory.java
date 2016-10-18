package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.service.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder>{
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private List<Mis_history> mList = new ArrayList<>();
    private Context context;
    private int  prevPosition = 0;
    private boolean Runanim;
    private int CountList = 0;
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
        sp  = context.getSharedPreferences("DataAccount",context.MODE_PRIVATE);
        editor = sp.edit();
        final Mis_history setList = mList.get(position);
        holder.mTime.setText(String.valueOf(setList.getStart_datetime()));
        holder.mMo_act.setText(String.valueOf(setList.getMo_act()));
        Integer mStatus = setList.getStatus();
        if(mStatus == 0){
            holder.Status.setImageResource(R.drawable.ic_true);
        }
        else if(mStatus == 1){
            holder.Status.setImageResource(R.drawable.ic_false);
        }
        Runanim = sp.getBoolean("Runanim",false);
        Log.d("TAG",String.valueOf(Runanim));
        if(Runanim){
            Animation_List(position,holder);
            CountList++;
            if(CountList==mList.size()){
                editor.putBoolean("Runanim",false);
                editor.commit();
            }
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
    private void Animation_List(Integer position, AdapterHistory.ViewHolder holder) {
        if(position > prevPosition){
            AnimationUtil.animate(holder , true);
        }else{
            AnimationUtil.animate(holder , false);
        }
        prevPosition = position;
    }
}

