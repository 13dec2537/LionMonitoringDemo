package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterEditprocess;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterEditstation;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_menu;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditstation extends Fragment {
    private AdapterEditstation mAdapterstation;
    private ManagerRetrofit mManager;
    private RecyclerView mRecyclerview;
    private ImageView mFabAddstation;
    private ProgressBar mProgressBar;
    private Thread mThread;
    private Context mContext;
    private LinearLayout mLinertlayout;
    public FragmentEditstation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_editstation, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getActivity();
        setGetClass();
        setFindById();
        setRecyclerview();
        mThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        CallData();
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

    }

    private void setRecyclerview() {
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRecyclerview.setAdapter(mAdapterstation);
    }

    private void setFindById() {
        mLinertlayout = (LinearLayout)getView().findViewById(R.id.Lineareditstation);
        mFabAddstation = (ImageView)getView().findViewById(R.id.XML_Fubaddstation);
        mFabAddstation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogInput();
            }
        });
        mRecyclerview = (RecyclerView)getView().findViewById(R.id.XML_RecyclerView);
        mProgressBar = (ProgressBar)getView().findViewById(R.id.XML_Pgeditstation);
        mProgressBar.setVisibility(View.VISIBLE);
    }


    private void setGetClass() {
        mAdapterstation = new AdapterEditstation();
        mManager = new ManagerRetrofit();
    }


    public void CallData() {
            Call<List<Mis_menu>> call = mManager.getmService().CallbackStation();
            call.enqueue(new Callback<List<Mis_menu>>() {
                @Override
                public void onResponse(Call<List<Mis_menu>> call, Response<List<Mis_menu>> response) {
                    if(response.isSuccessful()){
                        mProgressBar.setVisibility(View.GONE);
                        List<Mis_menu> JsonList = response.body();
                        mAdapterstation.CallAdapter(JsonList);
                    }

                }

                @Override
                public void onFailure(Call<List<Mis_menu>> call, Throwable t) {

                }
            });
    }

    public static FragmentEditstation newInstant(String menu) {
        FragmentEditstation fragmenu = new FragmentEditstation();
        Bundle bundle = new Bundle();
        bundle.putString("menu",menu);
        fragmenu.setArguments(bundle);
        return fragmenu;
    }
    private void ShowDialogInput() {
        View view = (LayoutInflater.from(getActivity())).inflate(R.layout.input_station,null);
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(getActivity());
        final EditText EdParameter = (EditText)view.findViewById(R.id.XML_Edstation_name);
        final EditText EdProcessname = (EditText)view.findViewById(R.id.XML_Edprocessname);
        final Button Btn_confirm = (Button)view.findViewById(R.id.XML_Btnconfirm);
        final Button Btn_cancel = (Button)view.findViewById(R.id.XML_Btncancel);
        alertDialog.setTitle("Add Station ");
        alertDialog.setIcon(R.drawable.ic_plus);
        alertDialog.setView(view,50,50,50,100);
        alertDialog.setCancelable(true);
        final Dialog dialog = alertDialog.create();
        Btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdParameter.getText().toString().matches("")){
                    Snakbar("Character Station Null !");
                }else{
                    AddParameter(EdParameter.getText().toString(),EdProcessname.getText().toString());
                    Snakbar("Add Station complete !");
                    dialog.dismiss();
                }
            }
        });


        Btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void Snakbar(String messages){
        Snackbar snackbar = Snackbar.make(mLinertlayout,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    private void AddParameter(final String mc_div, String mc_name){
        Log.d("log",mc_div + " | " + mc_name);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Mis_monitoringitem>> call = service.Callback_AddStation(mc_div,mc_name);
        call.enqueue(new Callback<List<Mis_monitoringitem>>() {
            @Override
            public void onResponse(Call<List<Mis_monitoringitem>> call, Response<List<Mis_monitoringitem>> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<List<Mis_monitoringitem>> call, Throwable t) {

            }
        });
    }
}
