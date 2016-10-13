package com.example.kuybeer26092016.lionmonitoringdemo.service;

import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public interface Service {
    @POST("/service/json/peerapong/JsonNew250959.php")
    Call<List<Mis_monitoringitem>> CallbackTower2();
}
