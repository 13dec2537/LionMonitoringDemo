package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterTower2;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTower2 extends Fragment {
    private boolean isRunning  = true;
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private Switch mSwitch;
    private ManagerRetrofit mManager;
    private AdapterTower2 mAdapter;
    private boolean running;
    private SharedPreferences spApp_Gone;
    private  SharedPreferences.Editor editor_App_Gone;
    public FragmentTower2() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tower2, container, false);
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
        mAdapter = new AdapterTower2();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                       CallData();
                    }
                }


            }
        }).start();
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
                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }
}
