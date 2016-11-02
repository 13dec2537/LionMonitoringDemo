package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.ManageProcessActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
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

public class AdapterEditprocess extends RecyclerView.Adapter<AdapterEditprocess.ViewHolder> {
    private ManagerRetrofit mManager;
    private Context mContext;
    private List<Mis_monitoringitem> mList = new ArrayList<>();
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_detailprocess,parent,false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mManager = new ManagerRetrofit();
        final Mis_monitoringitem Json_item = mList.get(position);
        holder.mTxt_mcname.setText(Json_item.getMc_name());
        holder.mBtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManageProcessActivity.class);
                intent.putExtra("mc_name",Json_item.getMc_name());
                intent.putExtra("mc_id",Json_item.getMc_id());
                intent.putExtra("mc_division",Json_item.getMc_div());
                v.getContext().startActivity(intent);
            }
        });
        holder.mBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetDialog(holder.mTxt_mcname.getText().toString(),Json_item.getMc_id());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void CallAdapter(List<Mis_monitoringitem> jsonList){
        mList.clear();
        mList.addAll(jsonList);
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxt_mcname;
        private Button mBtn_edit,mBtn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxt_mcname = (TextView)itemView.findViewById(R.id.XML_txt_mcname);
            mBtn_edit = (Button)itemView.findViewById(R.id.XML_Edit);
            mBtn_delete = (Button)itemView.findViewById(R.id.XML_Delete);
        }
    }

    private void SweetDialog(String nameprocess, final String mc_id){
        final SweetAlertDialog sweetDialog = new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE);
        sweetDialog.setCanceledOnTouchOutside(true);
        sweetDialog.setTitleText("DELETE " + nameprocess + " !");
        sweetDialog.setContentText("Are you sure went to Delete "+ nameprocess + " !");
        sweetDialog.setConfirmText("Delete");
        sweetDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                CallDelete(mc_id);
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
    private void CallDelete(String mc_id) {
        Log.d("C",mc_id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_DeleteProcess(mc_id);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {

            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }
}
