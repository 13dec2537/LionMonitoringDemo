package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private List<Mis_descrip> mList = new ArrayList<>();
    private Context context;
    private int  prevPosition = 0;
    private boolean Runanim;
    private int CountList = 0;
    public AdapterDescrip(Context context) {
        this.mList = mList;
        this.context = context;
    }

    static String setColor(String act, String min , String max){
        Double act_int = Double.valueOf(act);
        Double min_int = Double.valueOf(min);
        Double max_int = Double.valueOf(max);
        String color = "";
        if(act_int>=min_int && act_int<= max_int){
            color = "#B2FF59";
        }
        else{
            color = "#F44336";
        }
        return  color;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewrow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_description,parent,false);
        return new ViewHolder(viewrow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyCustomFonts(holder.mAct,holder.mMinMax);
        sp  = context.getSharedPreferences("DataAccount",context.MODE_PRIVATE);
        editor = sp.edit();
        Runanim = sp.getBoolean("Runanim",false);
        if(Runanim){
            CountList++;
            if(CountList==mList.size()){
                editor.putBoolean("Runanim",false);
                editor.commit();
            }
        }
        final Mis_descrip setList = mList.get(position);
        holder.mPram.setText(String.valueOf(setList.getMo_pram().toUpperCase()));
        holder.mAct.setText(String.valueOf(setList.getMo_act()));
        holder.mAct.setTextColor(Color.parseColor(setColor(String.valueOf(setList.getMo_act()),String.valueOf(setList.getMo_min()),String.valueOf(setList.getMo_max()))));
        holder.mUnit.setText(String.valueOf(setList.getMo_unit().toUpperCase()));
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
                editor.putBoolean("Runanim",true);
                editor.commit();
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

    private void MyCustomFonts(TextView mAct_1, TextView mAct_2) {
        Typeface MyCustomFont = Typeface.createFromAsset(context.getAssets(),"fonts/DS-DIGIT.TTF");
        mAct_1.setTypeface(MyCustomFont);
        mAct_2.setTypeface(MyCustomFont);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mPram,mAct,mUnit,mMinMax,mStantdard,mNow;
        private LinearLayout mLinearAll;
        public ViewHolder(View itemView) {
            super(itemView);
            mPram = (TextView)itemView.findViewById(R.id.XML_Parametername);
            mAct = (TextView)itemView.findViewById(R.id.XML_Actvalue);
            mUnit = (TextView)itemView.findViewById(R.id.XML_Unitname);
            mMinMax = (TextView)itemView.findViewById(R.id.XML_Standardvalue);
            mStantdard = (TextView)itemView.findViewById(R.id.XML_txtstandard);
            mNow = (TextView)itemView.findViewById(R.id.XML_txtnow);
            mLinearAll = (LinearLayout) itemView.findViewById(R.id.XML_Linaer);
        }
    }
}
