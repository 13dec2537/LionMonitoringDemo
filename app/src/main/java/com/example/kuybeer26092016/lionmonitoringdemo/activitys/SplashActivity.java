package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;

public class SplashActivity extends AppCompatActivity {
        private TextView showStatusConnect;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showStatusConnect = (TextView)findViewById(R.id.txtviewstatus);
        progressBar = (ProgressBar)findViewById(R.id.PgSplash);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo nf=cn.getActiveNetworkInfo();
                    if(nf != null && nf.isConnected()==true )
                    {
                        showStatusConnect.setText("Network Available");
                        Thread.sleep(4000);

                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        showStatusConnect.setText("Network Not Available");
                    }


                } catch (InterruptedException e) { }
            }
        }).start();
        ((Button)findViewById(R.id.btnExit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
