package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManager;
    private AdapterTower2 mAdapter;
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
        mManager = new ManagerRetrofit();
        mRecyclerView = (RecyclerView)getView().findViewById(R.id.recycleviewTower2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new AdapterTower2();
        mRecyclerView.setAdapter(mAdapter);
        CallData();
    }

    private void CallData() {
        Call<List<Mis_monitoringitem>> call = mManager.getmService().CallbackTower2();
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if (response.isSuccessful()){
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
