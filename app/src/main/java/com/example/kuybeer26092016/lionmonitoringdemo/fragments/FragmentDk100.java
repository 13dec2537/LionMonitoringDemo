package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kuybeer26092016.lionmonitoringdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDk100 extends Fragment {


    public FragmentDk100() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dk100, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnsetshowgone = (Button) getActivity().findViewById(R.id.button);
        btnsetshowgone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Status_nt",true);
                editor.commit();
            }
        });
    }
}
