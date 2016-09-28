package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentDescription;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentHistory;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

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
        BottomBar bottomBar =(BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_Description:
                        Tran_Description();
//                        toolbar.setTitle("Description");
                        break;
                    case R.id.tab_Story:
                        Tran_Story();
//                        toolbar.setTitle("Story");
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Tran_Description(){
        FragmentDescription fd = FragmentDescription.newInstant(mMc_name,mMc_id);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentContainer,fd);
        ft.commit();
    }
    public void Tran_Story(){
        FragmentHistory fs = FragmentHistory.newInstant(mMc_name,mMc_id);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.contentContainer,fs);
        ft.commit();
    }
}
