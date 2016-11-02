package com.example.kuybeer26092016.lionmonitoringdemo.adapters;

import android.content.Context;
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
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_parameter;
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

public class AdapterManageProcess  extends RecyclerView.Adapter<AdapterManageProcess.ViewHolder>{
    List<Mis_parameter> mList = new ArrayList<>();
    Context mContext;
    @Override
    public AdapterManageProcess.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_itemparameter,parent,false);
        return new ViewHolder(view);
    }

    public AdapterManageProcess(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(AdapterManageProcess.ViewHolder holder, int position) {
        final Mis_parameter jsonlist  = mList.get(position);
        holder.mTxtdetail1.setText(jsonlist.getMo_pram());
        holder.mICDELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogExit(jsonlist.getMo_id(),jsonlist.getMo_pram());

            }
        });

    }

    private void CallDelete(String mo_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_DeleteParameter(mo_id);
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

    public void addList(List<Mis_parameter> calllist) {
        mList.clear();
        mList.addAll(calllist);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtdetail1;
        private ImageView mICDELETE,mICEDIT;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxtdetail1 = (TextView) itemView.findViewById(R.id.XML_PARAMETERNAME);
            mICDELETE = (ImageView) itemView.findViewById(R.id.XML_ICDELETE);
        }
    }
    private void mAlertDialogExit(final String mo_id,final String mo_pram){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setTitleText("Delete !");
        sweetAlertDialog.setContentText("Are you sure went to Delete Parameter : "+mo_pram+" !");
        sweetAlertDialog.setCancelText("Cancel");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(final SweetAlertDialog sDialog) {
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sDialog.dismiss();
                            }
                        });
                    }

                    public void onFinish() {
                        sDialog.cancel();
                    }
                }.start();
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
                        CallDelete(mo_id);
                        Toast.makeText(mContext,"Delete complete !",Toast.LENGTH_LONG).show();
                        sDialog.dismiss();
                    }
                }.start();
            }
        });
        sweetAlertDialog.show();
    }
}
