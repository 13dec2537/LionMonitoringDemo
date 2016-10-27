package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterHistory;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences spApp_Gone;
    private  SharedPreferences.Editor editor_App_Gone;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/img/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterHistory mAdapter;
    private Thread thread;
    /************** Global *****************************/
    private ProgressBar progressBar;
    private String mMo_id, mMc_name, mMc_id, mMin, mMax, mPram,mAnim;
    private TextView mProcessname,mParametername,mStandardvalue,mHeadername;
    private ImageView mImageToobar;
    /************** Global *****************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setIntents();
        setAnimation();
        setSharedPreferences();

    }

    private void setAnimation() {
        this.overridePendingTransition(R.anim.anim_silde_out_left,R.anim.anim_silde_out_left);
        if(mAnim.equals("1")){
            this.overridePendingTransition(R.anim.anim_silde_out_left,R.anim.anim_silde_out_left);
        }else if(mAnim.equals("2")){
            this.overridePendingTransition(R.anim.anim_silde_in_left,R.anim.anim_silde_in_left);
        }
    }

    private void setIntents() {
        mAnim = getIntent().getExtras().getString("Ianim","");
        Intent call = getIntent();
        if(call != null){
            mMc_name = call.getStringExtra("mc_name");
            mMc_id = call.getStringExtra("mc_id");
            mPram = call.getStringExtra("mo_pram");
            mMin = call.getStringExtra("mo_min");
            mMax= call.getStringExtra("mo_max");
        }
    }

    private void setSharedPreferences() {
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        sp  = getSharedPreferences("DataAccount",Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void onStart() {
        super.onStart();
        editor_App_Gone.putString("History_Gone" , "0");
        editor_App_Gone.commit();
        setToolbar();
        setUsingClass();
        setFindbyid();
        setRecyclerView();
        setXML();

        /************** put Data to XML *****************************/
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        CallData();
                        sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private void setXML() {
        mHeadername.setText(mMc_name);
        mProcessname.setText(mMc_name);
        mParametername.setText(mPram);
        mStandardvalue.setText(mMin+mMax);
        Picasso.with(this).load(IMAGEURL + mMc_id + ".jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(128, 128)
                .centerCrop()
                .placeholder(R.drawable.person)
                .error(R.drawable.person).into(mImageToobar);
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUsingClass() {
        mManeger = new ManagerRetrofit();
        mAdapter = new AdapterHistory();
    }

    private void setToolbar() {
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
                i.putExtra("Ianim","2");
                startActivity(i);
                finish();
            }
        });
    }

    private void setFindbyid() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mHeadername = (TextView)findViewById(R.id.XML_Headername);
        mProcessname = (TextView) findViewById(R.id.XML_Processname);
        mParametername = (TextView)findViewById(R.id.XML_Parametername);
        mStandardvalue =  (TextView)findViewById(R.id.XML_Standardvalue);
        mImageToobar = (ImageView) findViewById(R.id.imageToobar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        thread.interrupt();
        Intent i = new Intent(HistoryActivity.this,DescripActivity.class);
        i.putExtra("Ianim","2");
        i.putExtra("mc_name",mMc_name);
        i.putExtra("mc_id",mMc_id);
        editor.putBoolean("Runanim",true);
        editor.commit();
        startActivity(i);
        finish();
    }
    private void CallData() {
        Call<List<Mis_history>> call = mManeger.getmService().Callback_History(mPram);
        call.enqueue(new Callback<List<Mis_history>>() {
            @Override
            public void onResponse(Call<List<Mis_history>> call, Response<List<Mis_history>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    List<Mis_history> Listhistory = response.body();
                    mAdapter.addList(Listhistory);

                    if(Listhistory.size() == 0){
                        mRecyclerView.setBackgroundResource(R.drawable.notthingfound);
                    }
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
        thread.interrupt();
        editor_App_Gone.putString("History_Gone" , "1");
        editor_App_Gone.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setIntents();
    }
}
