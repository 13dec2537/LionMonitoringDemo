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

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterDetail;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_detail;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDescription extends Fragment {
    private ProgressBar progressBar;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterDetail mAdapter;
    MyThread myThread;
    MyHandler myHandler;
    private boolean running;
    private String mMc_name,mMc_id;
    private ImageView mImageToobar;
    public FragmentDescription() {
        // Required empty public constructor
    }

    public static FragmentDescription newInstant(String mMc_name, String mMc_id) {
        FragmentDescription fd = new FragmentDescription();
        Bundle bundle = new Bundle();
        bundle.putString("mc_name" ,mMc_name);
        bundle.putString("mc_id" ,mMc_id);
        fd.setArguments(bundle);
        return fd;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_description, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBarDes);
        toolbar = (Toolbar)getView().findViewById(R.id.toolbar);
        mMc_name = getArguments().getString("mc_name");
        mMc_id = getArguments().getString("mc_id");
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterDetail();
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
        FragmentDescription.MyHandler mainHandler;

        public MyThread(FragmentDescription.MyHandler myHandler) {
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
        Log.d("TEST" , mMc_id);
        Call<List<Mis_detail>> call = mManeger.getmService().Callback_Detail(mMc_id);
        call.enqueue(new Callback<List<Mis_detail>>() {
            @Override
            public void onResponse(Call<List<Mis_detail>> call, Response<List<Mis_detail>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_detail>  ListTower = response.body();
                    mAdapter.addList(ListTower);
                }
            }

            @Override
            public void onFailure(Call<List<Mis_detail>> call, Throwable t) {

            }
        });
    }

    public static class MyHandler extends Handler {
        public static final int UPDATE_CNT = 0;
        private FragmentDescription parent;

        public MyHandler(FragmentDescription parent) {
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
