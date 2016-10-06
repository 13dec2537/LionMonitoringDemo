package com.example.kuybeer26092016.lionmonitoringdemo.service;

import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_adddata;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_descrip;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public interface    Service {
    @POST("/service/json/peerapong/JsonNew250959.php")
    Call<List<Mis_monitoringitem>> CallbackTower2();
    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_monitoritem.php")
    Call<List<Mis_descrip>> Callback_Detail(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_history.php")
    Call<List<Mis_history>> Callback_History(@Field("mo_pram") String mo_pram);

    @FormUrlEncoded
    @POST("/service/json/peerapong/mis_login.php")
    Call<List<Mis_login>> Callback_Login(@Field("username") String username, @Field("password") String password);

    @POST("/service/json/peerapong/service.php")
    Call<List<Mis_service>> CallbackService();

    @FormUrlEncoded
    @POST("/service/json/peerapong/adddata.php")
    Call<Mis_adddata> Callback_AddData(@Field("NotificationStatus") String statusNotification,
                                       @Field("mo_id") String mo_id);
}
