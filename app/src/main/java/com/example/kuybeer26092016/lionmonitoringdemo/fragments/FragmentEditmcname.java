package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterEdit_mcname;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditmcname extends Fragment {
    private AdapterEdit_mcname mAdapter;
    private ManagerRetrofit mManager;
    private RecyclerView mRecyclerview;
    private Button mReset,mChange;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public FragmentEditmcname() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_editmcname, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sp = getActivity().getSharedPreferences("CLICK",Context.MODE_PRIVATE);
        editor = sp.edit();

        /*********************Set Spinner **************************************/
//        mSpinner = (Spinner)getView().findViewById(R.id.XML_Spinner);
//        List<String> list_spinner = new ArrayList<>();
//        list_spinner.add("TW2");
//        list_spinner.add("DK100");
//        ArrayAdapter<String> adapter_spinner = new ArrayAdapter<String>
//                (getActivity(),android.R.layout.simple_spinner_item,list_spinner);
//        adapter_spinner.setDropDownViewResource
//                (R.layout.support_simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter_spinner);
        /*********************Set Spinner **************************************/



        /******************** Set Adapter *************************************/
        mAdapter = new AdapterEdit_mcname();
        mManager = new ManagerRetrofit();
        mRecyclerview = (RecyclerView)getView().findViewById(R.id.XML_RecyclerView);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mAdapter);
        CallData();
        editor.putString("onclick","");
        editor.commit();
        /******************** Set Adapter *************************************/
    }

    private void CallData() {
        Call<List<Mis_monitoringitem>> call = mManager.getmService().CallbackMcname("TW2");
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){
                    List<Mis_monitoringitem> JsonList = response.body();
                    mAdapter.CallAdapter(JsonList);
                }

            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }
}
