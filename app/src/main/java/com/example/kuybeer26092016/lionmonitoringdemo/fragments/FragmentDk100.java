package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterDk100;
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
public class FragmentDk100 extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManager;
    private AdapterDk100 mAdapter;
    private LinearLayout mLinearlayout;
    private Switch mSwitch;
    private Thread thread;
    private SharedPreferences spApp_Gone,sp_reload;
    private  SharedPreferences.Editor editor_App_Gone,editor_reload;

    public FragmentDk100() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dk100, container, false);
        sp_reload = getActivity().getSharedPreferences("img", Context.MODE_PRIVATE);
        editor_reload = sp_reload.edit();
        editor_reload.putBoolean("img_reload", true);
        editor_reload.commit();
        mLinearlayout = (LinearLayout) getActivity().findViewById(R.id.linear_tw2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar = (ProgressBar) getView().findViewById(R.id.progessbarDk100);
        mSwitch = (Switch)getView().findViewById(R.id.switch_nt);
        mManager = new ManagerRetrofit();
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleviewDk100);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new AdapterDk100(getContext());
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
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
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
        Call<List<Mis_monitoringitem>> call = mManager.getmService().CallbackDk100();
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    List<Mis_monitoringitem> ListDk100 = response.body();
                    mAdapter.addList(ListDk100);
                    if (ListDk100.size() == 0) {
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

