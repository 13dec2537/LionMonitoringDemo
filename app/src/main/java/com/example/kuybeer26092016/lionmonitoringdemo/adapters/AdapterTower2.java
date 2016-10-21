package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.DescripActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.UploadImageActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.service.AnimationListitem;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class AdapterTower2  extends RecyclerView.Adapter<AdapterTower2.ViewHolder>{
    public  CallbackInterface mCallback;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/img/";
    private final String ADMIN = "ADMIN";
    private List<Mis_monitoringitem> mList = new ArrayList<>();
    private String act,min,max;;
    private SharedPreferences sp,sp_uploadimg;
    private SharedPreferences.Editor editor,editor_uploadimg;
    private Context context;
    private ColorDrawable[] AnimaBackColor;
    private TransitionDrawable TranAnimaBackColor;
    private Boolean ReloadImage = true;
    private int sizeimg = 0;
    private int prevPosition=0;
    LayoutInflater layoutInflater;
    public AdapterTower2(Context context) {
        this.mList = mList;
        this.context = context;
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
        sp = context.getSharedPreferences("DataAccount",Context.MODE_PRIVATE);
        editor = sp.edit();
        sp_uploadimg = context.getSharedPreferences("img",Context.MODE_PRIVATE);
        editor_uploadimg = sp_uploadimg.edit();
            holder.mMc_name.setText(setList.getMc_name());
            holder.mAct_1.setText(setList.getMo_act().getAct_1());
            holder.mAct_2.setText(setList.getMo_act().getAct_2());
            holder.mAct_3.setText(setList.getMo_act().getAct_3());
            holder.mAct_4.setText(setList.getMo_act().getAct_4());
            ReloadImage = sp_uploadimg.getBoolean("img_reload",true);
            if(sizeimg<mList.size() && ReloadImage == true){
                        Picasso.with(context)
                                .load(IMAGEURL + setList.getMc_id() + ".jpg")
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .resize(128, 128)
                                .centerCrop()
                                .rotate(90)
                                .placeholder(R.drawable.progress_aniloadimg)
                                .error(R.drawable.ic_me)
                                .noFade()
                                .into(holder.mImvMachine);
                sizeimg++;
                Animation_List(position,holder);
                if(sizeimg==mList.size()){
                    editor_uploadimg.putBoolean("img_reload",false);
                    editor_uploadimg.commit();
                    editor.putBoolean("RunAnim",false);
                    sizeimg = 0;
                }
            }
            else if(sizeimg<mList.size() && ReloadImage == false){
                Picasso.with(context)
                        .load(IMAGEURL + setList.getMc_id() + ".jpg")
                        .resize(128, 128)
                        .centerCrop()
                        .rotate(90)
                        .placeholder(R.drawable.progress_aniloadimg)
                        .error(R.drawable.ic_me)
                        .noFade()
                        .into(holder.mImvMachine);
            }

            holder.mImvMachine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sp.getString("Userdivision","No Information").equals(ADMIN)){
                        Intent i = new Intent(context.getApplicationContext(), UploadImageActivity.class);
                        i.putExtra("I_URL_IMAGE" , IMAGEURL + setList.getMc_id() + ".jpg");
                        i.putExtra("ID_IMAGE",setList.getMc_id());
                        context.startActivity(i);
                    }

                }
            });
            holder.mlinearOnclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),DescripActivity.class);
                    i.putExtra("Ianim","1");
                    i.putExtra("mc_id",setList.getMc_id());
                    i.putExtra("mc_name",setList.getMc_name());
                    editor.putString("division",setList.getMc_division());
                    editor.putBoolean("Runanim",true);
                    editor.commit();
                    v.getContext().startActivity(i);
                    ((Activity)context).finish();
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
             if(String.valueOf(holder.mAct_1.getTextColors().getDefaultColor()).equals("-2617068")
                     ||String.valueOf(holder.mAct_2.getTextColors().getDefaultColor()).equals("-2617068")
                     ||String.valueOf(holder.mAct_3.getTextColors().getDefaultColor()).equals("-2617068")
                     ||String.valueOf(holder.mAct_4.getTextColors().getDefaultColor()).equals("-2617068")){
                 holder.Icon.setImageResource(R.drawable.shape_round_ofline);
             }
            if(String.valueOf(holder.mAct_1.getTextColors().getDefaultColor()).equals("-13730510")
                    && String.valueOf(holder.mAct_2.getTextColors().getDefaultColor()).equals("-13730510")
                    && String.valueOf(holder.mAct_3.getTextColors().getDefaultColor()).equals("-13730510")
                    && String.valueOf(holder.mAct_4.getTextColors().getDefaultColor()).equals("-13730510")){
                holder.Icon.setImageResource(R.drawable.shape_round_online);
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

    private void Animation_List(Integer position, ViewHolder holder) {
        if(position > prevPosition){
            AnimationListitem.animate(holder , true);
        }else{
            AnimationListitem.animate(holder , false);
        }
        prevPosition = position;
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
            color = "#2E7D32";
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

    public interface CallbackInterface {
        void CallBackMethod();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mMc_name,mAct_1,mAct_2,mAct_3,mAct_4;
        private TextView mUnit_1,mUnit_2,mUnit_3,mUnit_4;
        private TextView mPram_1,mPram_2,mPram_3,mPram_4;
        private ImageView mImvMachine,Icon;
        private LinearLayout mlinearOnclick,mLinearAll_txt_1,mLinearAll_txt_2,mLinearAll_txt_3,mLinearAll_txt_4;
        public ViewHolder(View itemView) {
            super(itemView);
            mImvMachine = (ImageView)itemView.findViewById(R.id.imvMachine);
            Icon = (ImageView)itemView.findViewById(R.id.shapeIconOnline);
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
