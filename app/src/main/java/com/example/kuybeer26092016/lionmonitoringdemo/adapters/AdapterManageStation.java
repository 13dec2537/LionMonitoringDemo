package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.ManageProcessActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.ManageStationActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_process;
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
 * Created by KuyBeer26092016 on 25/10/2559.
 */

public class AdapterManageStation extends RecyclerView.Adapter<AdapterManageStation.ViewHolder>{
    List<Mis_process> mList = new ArrayList<>();
    Context mContext;
    @Override
    public AdapterManageStation.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_itemprocess,parent,false);
        return new ViewHolder(view);
    }

    public AdapterManageStation(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(AdapterManageStation.ViewHolder holder, int position) {
        final Mis_process jsonlist  = mList.get(position);
        holder.mTxtdetail1.setText(jsonlist.getMc_name());
        holder.mIcdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogExit(jsonlist.getMc_name(),jsonlist.getMc_id());
            }
        });
        holder.mIC_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManageProcessActivity.class);
                intent.putExtra("mc_div",jsonlist.getMc_div());
                intent.putExtra("mc_id",jsonlist.getMc_id());
                intent.putExtra("mc_name",jsonlist.getMc_name());
                v.getContext().startActivity(intent);
            }
        });
    }

    private void CallDelete(String mc_id) {
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

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addList(List<Mis_process> calllist) {
        mList.clear();
        mList.addAll(calllist);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtdetail1;
        private ImageView mIcdelete,mIC_Edit;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxtdetail1 = (TextView) itemView.findViewById(R.id.XML_PROCESSNAME);
            mIcdelete = (ImageView)itemView.findViewById(R.id.XML_ICDELETE);
            mIC_Edit = (ImageView)itemView.findViewById(R.id.XML_ICEDIT);
        }
    }
    private void mAlertDialogExit(final String mc_name, final String mc_id){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setTitleText("Delete !");
        sweetAlertDialog.setContentText("Are you sure went to Delete Parameter : "+mc_name+" !");
        sweetAlertDialog.setCancelText("Cancel");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sDialog.dismiss();
                    }
        });
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sDialog) {

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }

                    public void onFinish() {
                        CallDelete(mc_id);
                        Toast.makeText(mContext,"Delete complete !",Toast.LENGTH_LONG).show();
                        sDialog.dismiss();
                    }
                }.start();
            }
        });
        sweetAlertDialog.show();
    }
}
