package com.example.kuybeer26092016.lionmonitoringdemo.service;

import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_detail;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public interface Service {
    @POST("/service/json/peerapong/JsonNew250959.php")
    Call<List<Mis_monitoringitem>> CallbackTower2();
    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_monitoritem.php")
    Call<List<Mis_detail>> Callback_Detail(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_monitoritem.php")
    Call<List<Mis_detail>> Callback_Story(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_login.php")
    Call<List<Mis_login>> Callback_Login(@Field("username") String username, @Field("password") String password);
}
