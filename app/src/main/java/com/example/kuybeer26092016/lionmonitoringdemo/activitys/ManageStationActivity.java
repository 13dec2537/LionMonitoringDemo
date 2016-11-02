package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterManageStation;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_process;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageStationActivity extends AppCompatActivity implements View.OnClickListener {
    private String mMc_id,mMc_division;
    private EditText mEd_stationname;
    private ImageView mIc_resettext;
    private Button mBtn_done,mBtn_cancel;
    private AdapterManageStation mAdapter;
    private ManagerRetrofit mManager;
    private RecyclerView mRecyclerview;
    private ProgressBar progressBar;
    private ImageView mPlus;
    private LinearLayout mManager_station;
    private Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.popup_slie_in_bottom,R.anim.popup_slie_in_bottom);
        setContentView(R.layout.activity_managerstation);
        setFindbyid();
        setIntents();
        setView();
        setRecyclerView();
        setOnClick();
    }


    private void setRecyclerView() {
        mAdapter = new AdapterManageStation(this);
        mManager = new ManagerRetrofit();
        mRecyclerview  = (RecyclerView) findViewById(R.id.XML_RecyclerViewProcess);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mAdapter);
        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        CallData(mMc_division);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

    }

    private void CallData(final String mc_div) {
        Call<List<Mis_process>> call = mManager.getmService().CallbackProcess(mc_div);
        call.enqueue(new Callback<List<Mis_process>>() {
            @Override
            public void onResponse(Call<List<Mis_process>> call, Response<List<Mis_process>> response) {
                if (response.isSuccessful()) {
                    List<Mis_process> calllist = response.body();
                    mAdapter.addList(calllist);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_process>> call, Throwable t) {

            }
        });
    }

    private void setView() {
        progressBar.setVisibility(View.VISIBLE);
        mEd_stationname.setText(mMc_division);
    }

    private void setIntents() {
        mMc_division = getIntent().getExtras().getString("mc_div");
    }

    private void setFindbyid() {
        mPlus = (ImageView)findViewById(R.id.XML_IC_PLUS);
        mManager_station = (LinearLayout)findViewById(R.id.XML_MANAGERSTATION);
        mIc_resettext= (ImageView)findViewById(R.id.XML_IC_RESETTEXT);
        progressBar = (ProgressBar)findViewById(R.id.XML_PGSTATION);
        mBtn_done = (Button)findViewById(R.id.XML_BTNDONE);
        mBtn_cancel = (Button)findViewById(R.id.XML_BTNCANCEL);
        mEd_stationname = (EditText)findViewById(R.id.XML_STATIONNAME);
    }
    private void setOnClick() {
        mPlus.setOnClickListener(this);
        mBtn_done.setOnClickListener(this);
        mBtn_cancel.setOnClickListener(this);
        mIc_resettext.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.XML_IC_PLUS:
                ShowDialogInput(mMc_division);
                break;
            case R.id.XML_IC_RESETTEXT:
                mEd_stationname.setText("");
                break;
            case R.id.XML_BTNDONE:
                String newMcname = mEd_stationname.getText().toString();
                if(newMcname.matches("")){
                    Toast.makeText(getApplicationContext(),"Not Null !",Toast.LENGTH_LONG).show();
                }
                else{
                    UpdateMcname(mMc_division,mEd_stationname.getText().toString());
                    Toast.makeText(getApplicationContext(),"Update Complete !",Toast.LENGTH_LONG).show();
                    ManageStationActivity.this.finish();
                }
                break;
            case R.id.XML_BTNCANCEL:
                ManageStationActivity.this.finish();
                break;
        }
    }
    private void ShowDialogInput(final String mc_div) {
        View view = (LayoutInflater.from(ManageStationActivity.this)).inflate(R.layout.input_process,null);
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(ManageStationActivity.this);
        final TextView txtStationname = (TextView)view.findViewById(R.id.XML_TEXTSTATIONNAME);
        txtStationname.setText(mc_div);
        final EditText edProcessname = (EditText)view.findViewById(R.id.XML_EDITPROCESSNAME);
        final Button Btn_done = (Button)view.findViewById(R.id.XML_BTNDONE);
        final Button Btn_cancel = (Button)view.findViewById(R.id.XML_BTNCANCEL);
        alertDialog.setTitle("Add Process");
        alertDialog.setIcon(R.drawable.ic_plus);
        alertDialog.setView(view,50,50,50,100);
        alertDialog.setCancelable(true);
        final Dialog dialog = alertDialog.create();
        Btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edProcessname.getText().toString().matches("")){
                   Toast.makeText(ManageStationActivity.this,"Character Parameter Null !",Toast.LENGTH_LONG).show();
                }else{
                    AddProcess(txtStationname.getText().toString(),edProcessname.getText().toString());
                    Snakbar("Add Parameter Success!");
                    Toast.makeText(ManageStationActivity.this,"Add Parameter Complete !",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    CallData(mc_div);
                }
            }
        });


        Btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void AddProcess(String stationname,String processname){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_AddProcess(stationname,processname);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }

    private void UpdateMcname(String station_name,String new_station_name) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.SERVICE_EDITSTATION(station_name,new_station_name);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {
            }
        });
    }
    private void Snakbar(String messages){
        Snackbar snackbar = Snackbar.make(mManager_station,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }



}
