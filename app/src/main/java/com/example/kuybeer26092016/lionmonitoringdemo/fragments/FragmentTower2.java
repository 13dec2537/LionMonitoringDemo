package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


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
import android.widget.ProgressBar;

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
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManager;
    private AdapterTower2 mAdapter;
    MyThread myThread;
    MyHandler myHandler;
    private boolean running;
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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterTower2();
        mRecyclerView.setAdapter(mAdapter);
        myHandler = new MyHandler(this);
        running = true;
        if(myThread != null){
            setRunning(false);
        }
        myThread = new MyThread(myHandler);
        myThread.start();
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
    public class MyThread extends  Thread {
        private int cnt;
        private boolean running;
        MyHandler mainHandler;

        public MyThread(MyHandler myHandler) {
            super();
        }

        @Override
        public void run() {
            running = true;
            while (running){
                try {
                    Thread.sleep(2000);
                    CallData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class MyHandler extends Handler {
        public static final int UPDATE_CNT = 0;
        private FragmentTower2 parent;

        public MyHandler(FragmentTower2 parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_CNT:
                    int c = (int)msg.obj;
                    Log.d("TEST",String.valueOf(c) );

                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
    public void setRunning(boolean running){
        this.running = running;
    }
}
