package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterHistory;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistory extends Fragment {
    private ProgressBar progressBar;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterHistory mAdapter;
    MyThread myThread;
    MyHandler myHandler;
    private boolean running;
    private String mMc_name,mMc_id,mMin,mMax,mPram;
    private TextView txtMc_name,txt_mMinMix,txtMo_pram;
    private ImageView mImageToobar;
    public FragmentHistory() {
        // Required empty public constructor
    }

    public static FragmentHistory newInstant(String mMc_name, String mMc_id, String mMo_min, String mMo_max , String mMo_pram) {
        FragmentHistory fd = new FragmentHistory();
        Bundle bundle = new Bundle();
        bundle.putString("mc_name" ,mMc_name);
        bundle.putString("mc_id" ,mMc_id);
        bundle.putString("mo_pram" ,mMo_pram);
        bundle.putString("mo_min" ,mMo_min);
        bundle.putString("mo_max" ,mMo_max);
        fd.setArguments(bundle);
        return fd;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMc_name = getArguments().getString("mc_name");
        mMc_id = getArguments().getString("mc_id");
        mPram = getArguments().getString("mo_pram");
        mMin = getArguments().getString("mo_min");
        mMax = getArguments().getString("mo_max");
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        txtMc_name = (TextView)getView().findViewById(R.id.mc_name_his);
        txtMo_pram = (TextView)getView().findViewById(R.id.pram_his);
        txt_mMinMix = (TextView)getView().findViewById(R.id.min_max_his);
        toolbar = (Toolbar)getView().findViewById(R.id.toolbar);
        //Set Data to TextView
            txtMc_name.setText(String.valueOf(mMc_name));
            txtMo_pram.setText(String.valueOf(mPram));
            txt_mMinMix.setText(String.valueOf(mMin + "-" + mMax));
        //End
        toolbar.setTitle(mMc_name);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mManeger = new ManagerRetrofit();
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.recyclerView);
        mImageToobar = (ImageView)getView().findViewById(R.id.imageToobar) ;
        Picasso.with(getContext()).load(IMAGEURL + mMc_id + ".jpg")
                .placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(mImageToobar);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterHistory();
        mRecyclerView.setAdapter(mAdapter);
        myHandler = new MyHandler(this);
        running = true;
        if(myThread != null){
            setRunning(false);
        }
        myThread = new MyThread(myHandler);
        myThread.start();
    }



    public class MyThread extends  Thread {
        private boolean running;
        FragmentHistory.MyHandler mainHandler;

        public MyThread(FragmentHistory.MyHandler myHandler) {
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

    private void CallData() {
        Call<List<Mis_history>> call = mManeger.getmService().Callback_History("1");
        call.enqueue(new Callback<List<Mis_history>>() {
            @Override
            public void onResponse(Call<List<Mis_history>> call, Response<List<Mis_history>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_history>  Listhistory = response.body();
                    mAdapter.addList(Listhistory);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_history>> call, Throwable t) {

            }
        });
    }

    public static class MyHandler extends Handler {
        public static final int UPDATE_CNT = 0;
        private FragmentHistory parent;

        public MyHandler(FragmentHistory parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_CNT:
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