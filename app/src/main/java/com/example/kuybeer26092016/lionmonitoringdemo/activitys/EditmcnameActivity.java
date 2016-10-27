package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterManageProcess;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditmcnameActivity extends AppCompatActivity {
    private String mMc_name,mMc_id;
    private EditText mEd_newname;
    private TextView mTxt_mcname;
    private Button mBtnReset,mBtnChange;
    private AdapterManageProcess mAdapter;
    private ManagerRetrofit mManager;
    private RecyclerView mRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.popup_slie_in_bottom,R.anim.popup_slie_in_bottom);
        setContentView(R.layout.activity_editmcname);
        setFindbyid();
        setIntents();
        setView();
        setRecyclerView();
        setBtnOnclick();
    }

    private void setRecyclerView() {
        mAdapter = new AdapterManageProcess();
        mManager = new ManagerRetrofit();
        mRecyclerview  = (RecyclerView) findViewById(R.id.XML_RecyclerViewParameter);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mAdapter);
        CallData(mMc_name);
    }

    private void CallData(String process) {
        Log.d("TAG",mMc_name);
        Call<List<Mis_monitoringitem>> call = mManager.getmService().CallbackDetailProcess(process);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if (response.isSuccessful()) {
                    List<Mis_monitoringitem> calllist = response.body();
                    mAdapter.addList(calllist);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }

    private void setView() {
        mTxt_mcname.setText(mMc_name);
    }

    private void setIntents() {
        mMc_name  = getIntent().getExtras().getString("mc_name");
        mMc_id = getIntent().getExtras().getString("mc_id");
    }

    private void setFindbyid() {
        mBtnReset = (Button)findViewById(R.id.XML_btnreset);
        mBtnChange = (Button)findViewById(R.id.XML_btnchange);
        mTxt_mcname = (TextView)findViewById(R.id.XML_mcname);
        mEd_newname = (EditText)findViewById(R.id.XML_newname);
    }

    private void setBtnOnclick() {
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMcname = mEd_newname.getText().toString();
                if(newMcname.matches("")){
                    Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
                }
                else{
                    UpdateMcname(mMc_id,mEd_newname.getText().toString());
                }
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEd_newname.setText("");
            }
        });
    }

    private void UpdateMcname(String mc_id,String newname) {
        Toast.makeText(getApplicationContext(),String.valueOf(mc_id+" " + newname),Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_AddMcname(mc_id,newname);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Update Compile !",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Update false !",Toast.LENGTH_LONG).show();
            }
        });
    }

}
