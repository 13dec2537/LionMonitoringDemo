package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by KuyBeer26092016 on 6/10/2559.
 */
public class CustomOnItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(parent.getContext(),
                "On Item Select : \n" + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
