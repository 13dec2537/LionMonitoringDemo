package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.ManageProcessActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.ManageStationActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_menu;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KuyBeer26092016 on 20/10/2559.
 */

public class AdapterEditstation extends RecyclerView.Adapter<AdapterEditstation.ViewHolder> {
    private ManagerRetrofit mManager;
    private Context mContext;
    private List<Mis_menu> mList = new ArrayList<>();
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_detailstation,parent,false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mManager = new ManagerRetrofit();
        final Mis_menu Json_item = mList.get(position);
        holder.mTxt_mcname.setText(Json_item.getMc_div());
        holder.mBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SweetDialog(Json_item.getMc_div());
            }
        });
        holder.mBtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManageStationActivity.class);
                intent.putExtra("mc_div",Json_item.getMc_div());
                v.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void CallAdapter(List<Mis_menu> jsonList){
        mList.clear();
        mList.addAll(jsonList);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxt_mcname;
        private ImageView mBtn_edit,mBtn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxt_mcname = (TextView)itemView.findViewById(R.id.XML_txt_stationname);
            mBtn_delete = (ImageView)itemView.findViewById(R.id.XML_Deletestation);
            mBtn_edit = (ImageView)itemView.findViewById(R.id.XML_Editstation);

        }
    }

    private void SweetDialog(final String mc_div){
        final SweetAlertDialog sweetDialog = new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE);
        sweetDialog.setCanceledOnTouchOutside(true);
        sweetDialog.setTitleText("DELETE " + mc_div + " !");
        sweetDialog.setContentText("Are you sure went to Delete "+ mc_div + " !");
        sweetDialog.setConfirmText("Delete");
        sweetDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                CallDelete(mc_div);
                sweetDialog.cancel();
            }
        });
        sweetDialog.setCancelText("Cancel");
        sweetDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                sweetDialog.cancel();
            }
        });
        sweetDialog.show();
    }
    private void CallDelete(String mc_div) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_DeleteStation(mc_div);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){
                    Log.d("log","isSuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }
}
