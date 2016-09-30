package com.example.kuybeer26092016.lionmonitoringdemo.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.DetailActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KuyBeer26092016 on 29/9/2559.
 */

public class BackgroundService extends android.app.Service {
    //globle notification//
    String mc_name,mo_act,mo_pram,status,status_nt;
    //
    String test = "0";
    private ManagerRetrofit mManager;
    private boolean running;
    MyHandler myHandler;
    MyThread myThread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TEST" , "StartService");
        myHandler = new MyHandler(this);
        running = true;
        if(myThread != null){
            setRunning(false);
        }
        myThread = new MyThread(myHandler);
        myThread.start();
        mManager = new ManagerRetrofit();
        return START_STICKY;
    }

    private void Run_service() {
        Call<List<Mis_service>> call = mManager.getmService().CallbackService();
        call.enqueue(new Callback<List<Mis_service>>() {
            @Override
            public void onResponse(Call<List<Mis_service>> call, Response<List<Mis_service>> response) {
                if(response.isSuccessful()){
                    List<Mis_service> ListService = response.body();
                    for(int i =0 ; i < ListService.size() ; i++ ){
                        mo_act = ListService.get(i).getMo_act();
                        mc_name = ListService.get(i).getMc_name();
                        mo_pram = ListService.get(i).getMo_pram();
                        status = ListService.get(i).getStatus();
                        status_nt = ListService.get(i).getNotificationStatus();
                        if(status_nt.equals("0") ){
                            Notification(mc_name,mo_pram,mo_act);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mis_service>> call, Throwable t) {
                Log.d("TEST" , "onFailure");
            }
        });
    }
    public class MyThread extends  Thread {
        private int cnt;
        private boolean running;
        MyHandler mainHandler;

        public MyThread(MyHandler myHandler) {
            super();
        }

        @Override
        public void run() {
            running = true;
            while (running){
                try {
                    Run_service();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class MyHandler extends Handler {
        public static final int UPDATE_CNT = 0;
        private BackgroundService parent;

        public MyHandler(BackgroundService parent) {
            super();
            this.parent = parent;
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case UPDATE_CNT:
                    int c = (int)msg.obj;
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }
    public void setRunning(boolean running){
        this.running = running;
    }
    public void Notification(String mc_name,String mo_pram,String mo_act){
        Intent intent = new Intent(this, DetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Close", pendingIntent).build();
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(String.valueOf(mc_name))
                .setContentText(String.valueOf(mo_pram + " : " + mo_act))
                .build();
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1+1, notification);
    }

}
