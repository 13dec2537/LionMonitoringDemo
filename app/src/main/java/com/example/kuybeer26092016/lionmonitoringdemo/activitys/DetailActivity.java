package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentDescription;

public class DetailActivity extends AppCompatActivity {
    private String mMc_name,mMc_id;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMc_name = getIntent().getExtras().getString("mc_name");
        mMc_id = getIntent().getExtras().getString("mc_id");
        Tran_Description();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Tran_Description(){
//        FragmentDescription fd = FragmentDescription.newInstant(mMc_name,mMc_id);
        FragmentDescription fd = FragmentDescription.newInstant(mMc_name,mMc_id);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentContainer,fd);
        ft.commit();
    }

}
