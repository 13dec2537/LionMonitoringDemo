package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.RegisterActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterTower2;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTower2 extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private Switch mSwitch;
    private ManagerRetrofit mManager;
    private AdapterTower2 mAdapter;
    private Context context;
    private Thread thread;
    private LinearLayout mLinearlayout;
    private SharedPreferences spApp_Gone,sp_reload;
    private  SharedPreferences.Editor editor_App_Gone,editor_reload;
    public FragmentTower2() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tower2, container, false);
        sp_reload = getActivity().getSharedPreferences("img",Context.MODE_PRIVATE);
        editor_reload = sp_reload.edit();
        editor_reload.putBoolean("img_reload",true);
        editor_reload.commit();
        mLinearlayout = (LinearLayout)getActivity().findViewById(R.id.linear_tw2);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar = (ProgressBar)getView().findViewById(R.id.progessbar);
        mManager = new ManagerRetrofit();
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.recycleviewTower2);
        mSwitch = (Switch)getView().findViewById(R.id.switch_nt);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterTower2(getContext());
        mRecyclerView.setAdapter(mAdapter);
        spApp_Gone = getActivity().getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        String CheckSwitch = spApp_Gone.getString("switch_nt","false");
        if(CheckSwitch.equals("1")){
            mSwitch.setChecked(true);
        }
        else if(CheckSwitch.equals("2")){
            mSwitch.setChecked(false);
        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor_App_Gone.putString("switch_nt" , "1");
                    editor_App_Gone.commit();
                }
                else {
                    editor_App_Gone.putString("switch_nt" , "0");
                    editor_App_Gone.commit();
                }
            }
        });
//        isRunningTw2 = spTW2_Running.getBoolean("TW2_Running",true);
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

    @Override
    public void onStop() {
        super.onStop();
        thread.interrupt();
    }

    private void CallData() {
        Call<List<Mis_monitoringitem>> call = mManager.getmService().CallbackTower2();
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_monitoringitem>  ListTower = response.body();
                    mAdapter.addList(ListTower);
                    if(ListTower.size() == 0){
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }


}
