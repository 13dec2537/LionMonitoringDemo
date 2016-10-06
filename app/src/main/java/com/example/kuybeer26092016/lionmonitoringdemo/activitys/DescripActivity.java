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

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterDescrip;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_descrip;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescripActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterDescrip mAdapter;

    /******************** Global ******************/
    private boolean running;
    private String mMc_id;
    private String mMc_name = "NODATA";
    private ImageView mImageToobar;
    private boolean isRunning  = true;
    Bundle bundle = new Bundle();
    /******************** Global ******************/
    private SharedPreferences sp,spApp_Gone;
    private  SharedPreferences.Editor editor,editor_App_Gone;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descrip);
        context = this;

        /******************** Open & set Shared  ******************/
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        /******************** Open & set Shared  ******************/

        sp = getSharedPreferences("DB",context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void onStart() {
        super.onStart();

        //*********************   Manager & set RecyclerView **************************//
        mManeger = new ManagerRetrofit();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mImageToobar = (ImageView)findViewById(R.id.imageToobar) ;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterDescrip(this);
        mRecyclerView.setAdapter(mAdapter);
        progressBar = (ProgressBar)findViewById(R.id.progressBarDes);
        //End
        /**************** Save Shared ************/
        editor_App_Gone.putString("Detail_Gone" , "0");
        editor_App_Gone.commit();
        /**************** Save Shared ************/

        /**************** set Toolbar ************/
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        bundle = getIntent().getExtras();
        if(bundle != null) {
            mMc_name = getIntent().getExtras().getString("mc_name");
            mMc_id = getIntent().getExtras().getString("mc_id");
        }else{
            mMc_name = sp.getString("mc_name","");
            mMc_id = sp.getString("mc_id","");
        }

        toolbar.setTitle(mMc_name);
        Picasso.with(this).load(IMAGEURL + mMc_id + ".jpg")
                .placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(mImageToobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DescripActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        /**************** set Toolbar ************/


        /**************** Thread ************/
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
        /**************** Thread ************/
    }
    private void CallData() {
//        mMc_id = getIntent().getExtras().getString("mc_id");
        Call<List<Mis_descrip>> call = mManeger.getmService().Callback_Detail(mMc_id);
        call.enqueue(new Callback<List<Mis_descrip>>() {
            @Override
            public void onResponse(Call<List<Mis_descrip>> call, Response<List<Mis_descrip>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_descrip>  ListTower = response.body();
                    mAdapter.addList(ListTower);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_descrip>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor_App_Gone.putString("Detail_Gone" , "1");
        editor_App_Gone.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editor_App_Gone.putString("Detail_Gone" , "0");
        editor_App_Gone.commit();
    }
}
