package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KuyBeer26092016 on 25/10/2559.
 */

public class AdapterManageProcess  extends RecyclerView.Adapter<AdapterManageProcess.ViewHolder>{
    List<Mis_monitoringitem> mList = new ArrayList<>();
    @Override
    public AdapterManageProcess.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_managedetail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterManageProcess.ViewHolder holder, int position) {
        final Mis_monitoringitem jsonlist  = mList.get(position);
        holder.mTxtdetail1.setText(jsonlist.getMo_pram().getPram_1());
        holder.mTxtdetail2.setText(jsonlist.getMo_pram().getPram_2());
        holder.mTxtdetail3.setText(jsonlist.getMo_pram().getPram_3());
        holder.mTxtdetail4.setText(jsonlist.getMo_pram().getPram_4());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_monitoringitem> calllist) {
        mList.clear();
        mList.addAll(calllist);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtdetail1,mTxtdetail2,mTxtdetail3,mTxtdetail4;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxtdetail1 = (TextView) itemView.findViewById(R.id.XML_txtdetailact1);
            mTxtdetail2 = (TextView) itemView.findViewById(R.id.XML_txtdetailact2);
            mTxtdetail3 = (TextView) itemView.findViewById(R.id.XML_txtdetailact3);
            mTxtdetail4 = (TextView) itemView.findViewById(R.id.XML_txtdetailact4);
        }
    }
}
