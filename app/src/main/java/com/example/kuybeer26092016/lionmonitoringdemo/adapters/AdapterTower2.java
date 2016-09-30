package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterTower2  extends RecyclerView.Adapter<AdapterTower2.ViewHolder>{
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private List<Mis_monitoringitem> mList = new ArrayList<>();
    private String act,min,max;;
    ManagerRetrofit mManager = new ManagerRetrofit();
    Context context;
    ColorDrawable[] AnimaBackColor;
    public AdapterTower2() {
        this.mList = mList;

    }
    TransitionDrawable TranAnimaBackColor;
    ColorDrawable[] BackGroundColor = {
            new ColorDrawable(Color.parseColor("#fa0707")),
            new ColorDrawable(Color.parseColor("#ffffff"))
    };

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
                act = String.valueOf(setList.getMo_act().getAct_1());
                min = String.valueOf(setList.getMo_min().getMin_1());
                max = String.valueOf(setList.getMo_max().getMax_1());
                holder.mAct_1.setTextColor(Color.parseColor(setColor(act,min,max)));
                TranAnimaBackColor = new TransitionDrawable(setColorBackground(act,min,max));
                holder.mAct_1.setBackground(TranAnimaBackColor);
                TranAnimaBackColor.startTransition(2000);
            }
            if(holder.mAct_2.getText().length()>0){
                holder.mLinearAll_txt_2.setVisibility(View.VISIBLE);
                act = String.valueOf(setList.getMo_act().getAct_2());
                min = String.valueOf(setList.getMo_min().getMin_2());
                max = String.valueOf(setList.getMo_max().getMax_2());
                holder.mAct_2.setTextColor(Color.parseColor(setColor(act,min,max)));
                TranAnimaBackColor = new TransitionDrawable(setColorBackground(act,min,max));
                holder.mAct_2.setBackground(TranAnimaBackColor);
                TranAnimaBackColor.startTransition(2000);
               }
            if(holder.mAct_3.getText().length()>0){
                holder.mLinearAll_txt_3.setVisibility(View.VISIBLE);
                act = String.valueOf(setList.getMo_act().getAct_3());
                min = String.valueOf(setList.getMo_min().getMin_3());
                max = String.valueOf(setList.getMo_max().getMax_3());
                holder.mAct_3.setTextColor(Color.parseColor(setColor(act,min,max)));
                TranAnimaBackColor = new TransitionDrawable(setColorBackground(act,min,max));
                holder.mAct_3.setBackground(TranAnimaBackColor);
                TranAnimaBackColor.startTransition(2000);
            }
            if(holder.mAct_4.getText().length()>0){
                holder.mLinearAll_txt_4.setVisibility(View.VISIBLE);
                act = String.valueOf(setList.getMo_act().getAct_4());
                min = String.valueOf(setList.getMo_min().getMin_4());
                max = String.valueOf(setList.getMo_max().getMax_4());
                holder.mAct_4.setTextColor(Color.parseColor(setColor(act,min,max)));
                TranAnimaBackColor = new TransitionDrawable(setColorBackground(act,min,max));
                holder.mAct_4.setBackground(TranAnimaBackColor);
                TranAnimaBackColor.startTransition(2000);
            }
            holder.mPram_1.setText(setList.getMo_pram().getPram_1());
            holder.mPram_2.setText(setList.getMo_pram().getPram_2());
            holder.mPram_3.setText(setList.getMo_pram().getPram_3());
            holder.mPram_4.setText(setList.getMo_pram().getPram_4());
            holder.mUnit_1.setText(setList.getMo_unit().getUnit_1());
            holder.mUnit_2.setText(setList.getMo_unit().getUnit_2());
            holder.mUnit_3.setText(setList.getMo_unit().getUnit_3());
            holder.mUnit_4.setText(setList.getMo_unit().getUnit_4());
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
    static String setColor(String act, String min , String max){
        Double act_int = Double.valueOf(act);
        Double min_int = Double.valueOf(min);
        Double max_int = Double.valueOf(max);
        String color = "";
        if(act_int>=min_int && act_int<= max_int){
            color = "#64DD17";
        }
        else{
            color = "#d81114";
        }
        return  color;
    }

    ColorDrawable[] setColorBackground(String act, String min, String max){
        Double act_int = Double.valueOf(act);
        Double min_int = Double.valueOf(min);
        Double max_int = Double.valueOf(max);
        if(act_int>=min_int && act_int<= max_int){
            AnimaBackColor = setColorBackgroundGreen();
        }
        else{
            AnimaBackColor = setColorBackgroundRed();
        }
        return  AnimaBackColor;
    }
    ColorDrawable[] setColorBackgroundGreen(){
        ColorDrawable[] AnimaBackColor = {
                new ColorDrawable(Color.parseColor("#CCFF90")),
                new ColorDrawable(Color.parseColor("#ffffff"))};

        return  AnimaBackColor;
    }
    ColorDrawable[] setColorBackgroundRed(){
        ColorDrawable[] AnimaBackColor = {
                new ColorDrawable(Color.parseColor("#FFCDD2")),
                new ColorDrawable(Color.parseColor("#ffffff"))};

        return  AnimaBackColor;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mMc_name,mAct_1,mAct_2,mAct_3,mAct_4;
        private TextView mUnit_1,mUnit_2,mUnit_3,mUnit_4;
        private TextView mPram_1,mPram_2,mPram_3,mPram_4;
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
            mPram_1 = (TextView)itemView.findViewById(R.id.pram_1);
            mPram_2 = (TextView)itemView.findViewById(R.id.pram_2);
            mPram_3 = (TextView)itemView.findViewById(R.id.pram_3);
            mPram_4 = (TextView)itemView.findViewById(R.id.pram_4);
            mUnit_1 = (TextView)itemView.findViewById(R.id.unit_1);
            mUnit_2 = (TextView)itemView.findViewById(R.id.unit_2);
            mUnit_3 = (TextView)itemView.findViewById(R.id.unit_3);
            mUnit_4 = (TextView)itemView.findViewById(R.id.unit_4);
        }
    }
}
