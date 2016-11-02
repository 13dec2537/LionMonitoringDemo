package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterManageProcess;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_parameter;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_process;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageProcessActivity extends AppCompatActivity implements View.OnClickListener{
    private String mMc_name,mMc_id,mMc_division;
    private EditText mEd_Process_name;
    private ImageView mIc_cencel;
    private Button mBtnChange,mBtnCancel;
    private AdapterManageProcess mAdapter;
    private ManagerRetrofit mManager;
    private RecyclerView mRecyclerview;
    private ProgressBar progressBar;
    private ImageView mPlus;
    private LinearLayout mLinertlayout;
    private Thread mThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.popup_slie_in_bottom,R.anim.popup_slie_in_bottom);
        setContentView(R.layout.activity_managerprocess);
        setFindbyid();
        setIntents();
        setView();
        setRecyclerView();
        setOnclick();

    }

    private void setRecyclerView() {
        mAdapter = new AdapterManageProcess(this);
        mManager = new ManagerRetrofit();
        mRecyclerview  = (RecyclerView) findViewById(R.id.XML_RecyclerViewParameter);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mAdapter);
        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        CallData(mMc_id);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

    }

    private void CallData(final String mMc_id) {
        Call<List<Mis_parameter>> call = mManager.getmService().CallbackParameter(mMc_id);
        call.enqueue(new Callback<List<Mis_parameter>>() {
            @Override
            public void onResponse(Call<List<Mis_parameter>> call, Response<List<Mis_parameter>> response) {
                if (response.isSuccessful()) {
                    List<Mis_parameter> calllist = response.body();
                    mAdapter.addList(calllist);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_parameter>> call, Throwable t) {

            }
        });
    }

    private void setView() {
        progressBar.setVisibility(View.VISIBLE);
        mEd_Process_name.setText(mMc_name);
    }

    private void setIntents() {
        mMc_name  = getIntent().getExtras().getString("mc_name");
        mMc_id = getIntent().getExtras().getString("mc_id");
        mMc_division = getIntent().getExtras().getString("mc_div");
    }

    private void setFindbyid() {
        mPlus = (ImageView)findViewById(R.id.XML_IC_PLUS);
        mLinertlayout = (LinearLayout)findViewById(R.id.XML_MANAGERPROCESS);
        mIc_cencel = (ImageView)findViewById(R.id.XML_IC_RESETTEXT);
        progressBar = (ProgressBar)findViewById(R.id.XML_PGSTATION);
        mBtnChange = (Button)findViewById(R.id.XML_BTNDONE);
        mBtnCancel = (Button)findViewById(R.id.XML_BTNCANCEL);
        mEd_Process_name = (EditText)findViewById(R.id.XML_PROCESSNAME);
    }

    private void setOnclick() {
        mBtnChange.setOnClickListener(this);
        mIc_cencel.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mPlus.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.XML_IC_PLUS:
                ShowDialogInput(mMc_id);
                break;
            case R.id.XML_IC_RESETTEXT:
                mEd_Process_name.setText("");
                break;
            case R.id.XML_BTNDONE:
                String newMcname = mEd_Process_name.getText().toString();
                if(newMcname.matches("")){
                    Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
                }
                else{
                    UpdateMcname(mMc_id,mEd_Process_name.getText().toString());
                    Toast.makeText(getApplicationContext(),"Update Compile !",Toast.LENGTH_LONG).show();
                    ManageProcessActivity.this.finish();
                }
                break;

            case R.id.XML_BTNCANCEL:
                ManageProcessActivity.this.finish();
                break;
        }
    }
    private void ShowDialogInput(final String mc_id) {
        View view = (LayoutInflater.from(ManageProcessActivity.this)).inflate(R.layout.input_parameter,null);
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(ManageProcessActivity.this);
        final EditText EdParameter = (EditText)view.findViewById(R.id.XML_EdPram_name);
        final EditText EdMinValue = (EditText)view.findViewById(R.id.XML_EdMin_value);
        final EditText EdMaxValue = (EditText)view.findViewById(R.id.XML_EdMax_value);
        final EditText EdParamterUnit = (EditText)view.findViewById(R.id.XML_EdUnit_parameter);
        final Button Btn_confirm = (Button)view.findViewById(R.id.XML_Btnconfirm);
        final Button Btn_cancel = (Button)view.findViewById(R.id.XML_Btncancel);
        alertDialog.setTitle("Add Parameter");
        alertDialog.setIcon(R.drawable.ic_plus);
        alertDialog.setView(view,50,50,50,100);
        alertDialog.setCancelable(true);
        final Dialog dialog = alertDialog.create();
        Btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdParameter.getText().toString().matches("")){
                    Snakbar("Character Parameter Null !");
                }else{
                    AddParameter(mMc_id,mMc_name,mMc_division,EdParameter.getText().toString(),EdMinValue.getText().toString(),EdMaxValue.getText().toString()
                            ,EdParamterUnit.getText().toString());
                    Snakbar("Add Parameter Success!");
                    dialog.dismiss();
                    CallData(mc_id);
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

    private void AddParameter(final String mc_id,String mc_name,String mc_division,String mo_pram, String mo_min, String mo_max, String mo_unit){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_AddParameter(mc_id,mc_name,mc_division,mo_pram,mo_min,mo_max,mo_unit);
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

    private void UpdateMcname(String mc_id,String newname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_Editprocess(mc_id,newname);
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
        Snackbar snackbar = Snackbar.make(mLinertlayout,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }



}
