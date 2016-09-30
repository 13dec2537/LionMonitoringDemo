package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterHistory;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentHistory;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentTower2;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/images/";
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ManagerRetrofit mManeger;
    private AdapterHistory mAdapter;
    MyThread myThread;
    MyHandler myHandler;
    private String mMc_name,mMc_id,mMo_min,mMo_max,mMo_pram;
    private ImageView mImageToobar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mMc_name = getIntent().getExtras().getString("mc_name");
        mMc_id = getIntent().getExtras().getString("mc_id");
        mMo_pram = getIntent().getExtras().getString("mo_pram");
        mMo_min = getIntent().getExtras().getString("mo_min");
        mMo_max = getIntent().getExtras().getString("mo_max");
        Tran_FragHis();
    }

    public class MyThread extends  Thread {
        private boolean running;
        HistoryActivity.MyHandler mainHandler;

        public MyThread(HistoryActivity.MyHandler myHandler) {
            super();
        }

        @Override
        public void run() {
            running = true;
            while (running){
                try {
                    Thread.sleep(2000);
//                    CallData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class MyHandler extends Handler {
        public static final int UPDATE_CNT = 0;
        private HistoryActivity parent;

        public MyHandler(HistoryActivity parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_CNT:
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
    public void Tran_FragHis(){
        FragmentHistory frag_his = FragmentHistory.newInstant(mMc_name ,mMc_id,mMo_min,mMo_max,mMo_pram);
        android.support.v4.app.FragmentManager fg = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fg.beginTransaction();
        ft.replace(R.id.Layout,frag_his);
        ft.commit();
    }
}
