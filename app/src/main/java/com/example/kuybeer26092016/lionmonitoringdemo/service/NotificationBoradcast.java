package com.example.kuybeer26092016.lionmonitoringdemo.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.kuybeer26092016.lionmonitoringdemo.activitys.MainActivity;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_notification;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KuyBeer26092016 on 5/10/2559.
 */

public class NotificationBoradcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String checkAction = String.valueOf(Context.NOTIFICATION_SERVICE);
        manager.cancel(1101);
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
        SharedPreferences sp = context.getSharedPreferences("NOTIFICATION",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(intent.getAction().equals("READ_MESSAGE_ACTION")){
            Intent readMessage = new Intent(context,MainActivity.class);
            readMessage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            readMessage.putExtra("mc_id",intent.getStringExtra("mc_id"));
            editor.putInt("returnAc", Integer.parseInt(intent.getStringExtra("mc_id")));
            editor.commit();
            context.startActivity(readMessage);
            editor.putBoolean("Status_nt" ,true);
            setStatusNotifDB(intent.getStringExtra("mo_id"));
            editor.commit();
        }
        else if(intent.getAction().equals("EXIT_MESSAGE_ACTION")){
            editor.putBoolean("Status_nt" ,true);
            editor.commit();
            setStatusNotifDB(intent.getStringExtra("mo_id"));
        }
        else if(intent.getAction().equals("CENCEL_MESSAGE_ACTION")){
            editor.putBoolean("Status_nt" ,true);
            editor.commit();
        }
    }
    private void setStatusNotifDB(String mMo_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Mis_history misdata = new Mis_history();
        Call<Mis_notification> call = service.Callback_Setstatusnotfication("1", mMo_id);
        call.enqueue(new Callback<Mis_notification>() {
            @Override
            public void onResponse(Call<Mis_notification> call, Response<Mis_notification> response) {

            }

            @Override
            public void onFailure(Call<Mis_notification> call, Throwable t) {

            }
        });
    }
}
