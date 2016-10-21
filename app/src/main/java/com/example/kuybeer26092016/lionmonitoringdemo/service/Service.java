package com.example.kuybeer26092016.lionmonitoringdemo.service;

import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_adddata;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_descrip;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_register;
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
    @FormUrlEncoded
    @POST("/service/json/android_php/edit_mcname.php")
    Call<List<Mis_monitoringitem>> CallbackMcname(@Field("mc_name") String mc_name);

    @FormUrlEncoded
    @POST("/service/json/android_php/add_newmcname.php")
    Call<List<Mis_monitoringitem>> Callback_AddMcname(@Field("mc_id")String mc_id,
                                                      @Field("newname")String newname);

    @POST("/service/json/android_php/mis_tw2.php")
    Call<List<Mis_monitoringitem>> CallbackTower2();

    @POST("/service/json/android_php/mis_dk100.php")
    Call<List<Mis_monitoringitem>> CallbackDk100();
    @FormUrlEncoded
    @POST("/service/json/android_php/mis_monitoritem.php")
    Call<List<Mis_descrip>> Callback_Detail(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_history.php")
    Call<List<Mis_history>> Callback_History(@Field("mo_pram") String mo_pram);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_login.php")
    Call<List<Mis_login>> Callback_Login(@Field("username") String username, @Field("password") String password);

    @POST("/service/json/android_php/mis_service.php")
    Call<List<Mis_service>> CallbackService();

    @FormUrlEncoded
    @POST("/service/json/android_php/set_status_nt.php")
    Call<Mis_adddata> Callback_AddData(@Field("NotificationStatus") String statusNotification,
                                       @Field("mo_id") String mo_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/check_register.php")
    Call<List<Mis_register>> Callback_CheckRegister( @Field("username") String username);

    @FormUrlEncoded
    @POST("/service/json/android_php/add_register.php")
    Call<List<Mis_register>> Callback_AddRegister(    @Field("username") String username,
                                                      @Field("password") String password,
                                                      @Field("division") String division);

}
