package com.example.kuybeer26092016.lionmonitoringdemo.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.HistoryActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.MainActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_adddata;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Notification.DEFAULT_SOUND;

/**
 * Created by KuyBeer26092016 on 29/9/2559.
 */

public class BackgroundService extends android.app.Service {
    Context context;
    private String mc_name,mc_id,mo_pram,mo_min,mo_max,status_nt,mMo_id,mo_startDatatime;
    private ManagerRetrofit mManager;
    private Boolean NT;
    private SharedPreferences sp_NT;
    private SharedPreferences.Editor editor_NT;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TEST" , "StartService");
        mManager = new ManagerRetrofit();
        context = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5000);
                        Run_service();
                    } catch (Exception e) {
                    }
                }


            }
        }).start();
        return START_STICKY;
    }

    private void Run_service() {

        SharedPreferences spCall_service = getSharedPreferences("App_Gone",Context.MODE_PRIVATE);
        /*****************************status Activity & run service if *************************/
        String Main_Gone = spCall_service.getString("Main_Gone","");
        String Detail_Gone = spCall_service.getString("Detail_Gone","");
        String History_Gone = spCall_service.getString("History_Gone","");
        String Switch_nt = spCall_service.getString("switch_nt","");
        /*****************************status Activity & run service if *************************/
        sp_NT = getSharedPreferences("NOTIFICATION",Context.MODE_PRIVATE);
        editor_NT = sp_NT.edit();
        if(Main_Gone.equals("1") && Detail_Gone.equals("1") && History_Gone.equals("1") && Switch_nt.equals("1")){
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

                            /************ SET NT *************/
                            NT = sp_NT.getBoolean("Status_nt",true);
                            /************ SET NT *************/

                            if(status_nt.equals("0") && (NT.equals(true) || NT != false)){
                                Notification(mc_name,mo_pram,mc_id,mo_min,mo_max,mMo_id,mo_startDatatime);
                                editor_NT.putBoolean("Status_nt",false);
                                editor_NT.commit();
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
    }
    public void Notification(String mc_name,String mo_pram,String mc_id,String mo_min,String mo_max,String mo_id,String mo_startDatatime){

        NotificationCompat.Builder NT = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle(mc_name)
                .setContentText(mc_name + " " + mo_pram + " DateTime : " + mo_startDatatime )
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        Intent read = new Intent();
        read.setAction("READ_MESSAGE_ACTION");
        read.putExtra("mc_name" , mc_name);
        read.putExtra("mo_pram", mo_pram);
        read.putExtra("mo_id", mo_id);
        PendingIntent readIntent = PendingIntent.getBroadcast(context,123,read,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent exit = new Intent();
        exit.putExtra("mo_id" , mo_id);
        exit.setAction("EXIT_MESSAGE_ACTION");
        PendingIntent ignoreIntent = PendingIntent.getBroadcast(context,123,exit,PendingIntent.FLAG_UPDATE_CURRENT);

        NT.addAction(0,"Ok",readIntent);
        NT.addAction(0,"Exit",ignoreIntent);
        NT.setContentIntent(ignoreIntent);
        NT.setContentIntent(readIntent);
        NotificationManager manager = (NotificationManager)getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1101,NT.build());
    }
}
