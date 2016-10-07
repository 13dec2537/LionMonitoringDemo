package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterHistory;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_adddata;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences spApp_Gone;
    private  SharedPreferences.Editor editor_App_Gone;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterHistory mAdapter;
    Bundle bundle = new Bundle();
    /************** Global *****************************/
    private boolean isRunning  = true;
    private ProgressBar progressBar;
    private String mMo_id, mMc_name, mMc_id, mMin, mMax, mPram;
    private TextView txtMc_name, txt_mMinMix, txtMo_pram;
    private ImageView mImageToobar;
    /************** Global *****************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /************** set Shared *****************************/
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        /************** set Shared *****************************/

        Intent call = getIntent();
        if(call != null){
            mMc_name = call.getStringExtra("mc_name");
            mMc_id = call.getStringExtra("mc_id");
            mPram = call.getStringExtra("mo_pram");
            mMin = call.getStringExtra("mo_min");
            mMax= call.getStringExtra("mo_max");
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        /************** Open & set Shared *****************************/
        editor_App_Gone.putString("History_Gone" , "0");
        editor_App_Gone.commit();
        /************** Open & set Shared *****************************/


        /************** set Toolbar *****************************/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mMc_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryActivity.this,DescripActivity.class);
                i.putExtra("mc_name",mMc_name);
                i.putExtra("mc_id",mMc_id);
                startActivity(i);
                finish();
            }
        });
        /************** set Toolbar *****************************/

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtMc_name = (TextView) findViewById(R.id.mc_name_his);
        txtMo_pram = (TextView) findViewById(R.id.pram_his);
        txt_mMinMix = (TextView) findViewById(R.id.min_max_his);
        mImageToobar = (ImageView) findViewById(R.id.imageToobar);

        /************** set Recycler View  *****************************/
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mManeger = new ManagerRetrofit();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new AdapterHistory();
        mRecyclerView.setAdapter(mAdapter);
        /************** set Recycler View  *****************************/


        /************** put Data to XML *****************************/
        txtMc_name.setText(String.valueOf(mMc_name));
        txtMo_pram.setText(String.valueOf(mPram));
        txt_mMinMix.setText(String.valueOf(mMin + "-" + mMax));
        Picasso.with(this).load(IMAGEURL + mMc_id + ".jpg").placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(mImageToobar);
        /************** put Data to XML *****************************/
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        CallData();
                    }
                }


            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HistoryActivity.this,DescripActivity.class);
        startActivity(i);
        finish();
    }
    private void CallData() {
        Log.d("TEST",mPram);
        Call<List<Mis_history>> call = mManeger.getmService().Callback_History(mPram);
        call.enqueue(new Callback<List<Mis_history>>() {
            @Override
            public void onResponse(Call<List<Mis_history>> call, Response<List<Mis_history>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    List<Mis_history> Listhistory = response.body();
                    mAdapter.addList(Listhistory);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_history>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor_App_Gone.putString("History_Gone" , "1");
        editor_App_Gone.commit();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        Intent call = getIntent();
        Log.d("TEST", "onResume");
        if(call != null){
            mMc_name = call.getStringExtra("mc_name");
            mMc_id = call.getStringExtra("mc_id");
            mPram = call.getStringExtra("mo_pram");
            mMin = call.getStringExtra("mo_min");
            mMax= call.getStringExtra("mo_max");
            Log.d("TEST" , "Resume mc_name : " + mMc_name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
