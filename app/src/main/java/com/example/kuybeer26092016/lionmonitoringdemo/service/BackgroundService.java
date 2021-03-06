package com.example.kuybeer26092016.lionmonitoringdemo.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
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
    Context context;
    private String mc_name,mc_id,mo_pram,mo_min,mo_max,status_nt,mMo_id,mo_startDatatime,mo_act;
    private ManagerRetrofit mManager;
    private Boolean NT;
    private SharedPreferences sp_NT;
    private SharedPreferences.Editor editor_NT;
    String Main_Gone,Detail_Gone,History_Gone,Upload_Gone,Switch_nt;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mManager = new ManagerRetrofit();
        context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                     try {
                        Run_service();
                         Thread.sleep(3000);
                    } catch (Exception e) {
                    }
                }


            }
        }).start();
        return START_STICKY;
    }

    private void Run_service() {
        setSharedPreferences();
        if(Main_Gone.equals("1") && Detail_Gone.equals("1") && History_Gone.equals("1") && Switch_nt.equals("1") && Upload_Gone.equals("1")){
            Call<List<Mis_service>> call = mManager.getmService().CallbackService();
            call.enqueue(new Callback<List<Mis_service>>() {
                @Override
                public void onResponse(Call<List<Mis_service>> call, Response<List<Mis_service>> response) {
                    if(response.isSuccessful()){
                      List<Mis_service> ListService = response.body();
                                        for(int i =0 ; i < ListService.size() ; i++ ){
                                            mc_name = ListService.get(i).getMc_name();
                                            mo_pram = ListService.get(i).getMo_pram();
                                            mo_min = ListService.get(i).getMo_min();
                                            mo_max = ListService.get(i).getMo_max();
                                            mc_id = ListService.get(i).getMc_id();
                                            mMo_id = ListService.get(i).getMo_id();
                                            mo_startDatatime = ListService.get(i).getStart_datetime();
                                            status_nt = ListService.get(i).getNotificationStatus();
                                            mo_act = ListService.get(i).getMo_act();

                                            /************ SET NT *************/
                                            NT = sp_NT.getBoolean("Status_nt",true);
                                            /************ SET NT *************/
                                            if(status_nt.equals("0") && (NT.equals(true) || NT != false)){
                                                Notification(mc_name,mo_pram,mc_id,mo_min,mo_max,mMo_id,mo_startDatatime,mo_act);
                                                editor_NT.putBoolean("Status_nt",false);
                                                editor_NT.commit();
                                            }

                                            new CountDownTimer(2000, 1000) {

                                                public void onTick(long millisUntilFinished) {

                                                }

                                                public void onFinish() {
                                                    if(status_nt.equals("0") && (NT.equals(true) || NT != false)){
                                                        Notification(mc_name,mo_pram,mc_id,mo_min,mo_max,mMo_id,mo_startDatatime,mo_act);
                                                        editor_NT.putBoolean("Status_nt",false);
                                                        editor_NT.commit();
                                                    }
                                                }
                                            }.start();
                                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Mis_service>> call, Throwable t) {
                }
            });
        }
    }

    private void setSharedPreferences() {
        SharedPreferences spCall_service = getSharedPreferences("App_Gone",Context.MODE_PRIVATE);
        Main_Gone = spCall_service.getString("Main_Gone","");
        Detail_Gone = spCall_service.getString("Detail_Gone","");
        History_Gone = spCall_service.getString("History_Gone","");
        Upload_Gone = spCall_service.getString("Upload_Gone","");
        Switch_nt = spCall_service.getString("switch_nt","");
        sp_NT = getSharedPreferences("NOTIFICATION",Context.MODE_PRIVATE);
        editor_NT = sp_NT.edit();
    }

    public void Notification(String mc_name,String mo_pram,String mc_id,String mo_min,String mo_max,String mo_id,String mo_startDatatime
    ,String mo_act){
        NotificationCompat.Builder NT = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Standard over")
                .setContentText(mc_name + " " + mo_pram.toUpperCase() + '\n' +"Standard : " + mo_min + "-" +
                mo_max + '\n' + "Value : " +  mo_act)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        Intent read = new Intent();
        read.setAction("READ_MESSAGE_ACTION");
        read.putExtra("mc_name" , mc_name);
        read.putExtra("mc_id",mc_id);
        read.putExtra("mo_pram", mo_pram);
        read.putExtra("mo_id", mo_id);
        PendingIntent readIntent = PendingIntent.getBroadcast(context,123,read,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent exit = new Intent();
        exit.putExtra("mo_id" , mo_id);

        exit.setAction("EXIT_MESSAGE_ACTION");
        PendingIntent ignoreIntent = PendingIntent.getBroadcast(context,123,exit,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancel = new Intent();
        exit.setAction("CENCEL_MESSAGE_ACTION");
        PendingIntent cencelIntent = PendingIntent.getBroadcast(context,123,exit,PendingIntent.FLAG_UPDATE_CURRENT);

        NT.addAction(0,"Ok",readIntent);
        NT.addAction(0,"Exit",ignoreIntent);
        NT.setDeleteIntent(cencelIntent);
        NT.setContentIntent(ignoreIntent);
        NT.setContentIntent(readIntent);
        NotificationManager manager = (NotificationManager)getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1101,NT.build());
    }
}
