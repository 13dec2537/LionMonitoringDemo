package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccount extends Fragment {
    private String mUsername;
    private ImageView image;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public FragmentAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        sp = getActivity().getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        ((TextView)getView().findViewById(R.id.txtUsername)).setText("Username : " + sp.getString("username",""));
        ((TextView)getView().findViewById(R.id.txtDivision)).setText("Division : " + sp.getString("division",""));
        ImageView mImage = (ImageView)getView().findViewById(R.id.image);
        Picasso.with(getContext()).load(sp.getString("imageUrl",""))
                .placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(mImage);
    }

    public static FragmentAccount newInstent(String username) {
        FragmentAccount fragAc = new FragmentAccount();
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        fragAc.setArguments(bundle);
        return fragAc;
    }
}
