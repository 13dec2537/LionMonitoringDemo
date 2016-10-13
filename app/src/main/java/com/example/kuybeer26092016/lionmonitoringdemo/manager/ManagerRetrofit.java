package com.example.kuybeer26092016.lionmonitoringdemo.manager;

import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class ManagerRetrofit {
    private static final String BASE_URL = "http://www.thaidate4u.com/";
    private Service mService;
    public Service getmService(){
        if(mService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = retrofit.create(Service.class);
        }
        return mService;
    }
}
