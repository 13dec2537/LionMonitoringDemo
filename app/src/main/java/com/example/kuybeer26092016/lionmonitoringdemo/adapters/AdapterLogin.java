package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class AdapterLogin extends RecyclerView.Adapter<AdapterLogin.ViewHolder>{
    List<Mis_login> mList = new ArrayList<>();

    public AdapterLogin() {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mis_login setList = mList.get(position);
        holder.mc_name.setText(setList.getUsername());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addLogin(Mis_login login) {
        mList.add(login);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mc_name;
        private LinearLayout mLinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mc_name = (TextView)itemView.findViewById(R.id.edUsername);
        }
    }
}
