package com.example.kuybeer26092016.lionmonitoringdemo.service;

import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_menu;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_parameter;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_process;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_notification;
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
    @POST("/service/json/android_php/mis_navmenu.php")
    Call<List<Mis_menu>> CallbackMenu();

    @POST("/service/json/android_php/mis_callstation.php")
    Call<List<Mis_menu>> CallbackStation();

    @FormUrlEncoded
    @POST("/service/json/android_php/manager_station/mis_callprocess.php")
    Call<List<Mis_process>> CallbackProcess(@Field("mc_div")String mc_div);

    @FormUrlEncoded
    @POST("/service/json/android_php/manager_station/mis_editstation.php")
    Call<List<Mis_monitoringitem>> SERVICE_EDITSTATION(@Field("stationname")String stationname,
                                                       @Field("newstationname")String newstationname);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_editprocess.php")
    Call<List<Mis_monitoringitem>> Callback_Editprocess(@Field("mc_id")String mc_id,
                                                      @Field("newname")String newname);
    @FormUrlEncoded
    @POST("/service/json/android_php/mis_add_parameter.php")
    Call<List<Mis_monitoringitem>> Callback_AddParameter(@Field("mc_id") String mc_id,
                                                         @Field("mc_name") String mc_name,
                                                         @Field("mc_division") String mc_division,
                                                         @Field("mo_pram") String mo_pram,
                                                         @Field("mo_min") String mo_min,
                                                         @Field("mo_max") String mo_max,
                                                         @Field("mo_unit") String mo_unit);

    @FormUrlEncoded
    @POST("/service/json/android_php/manager_station/mis_add_process.php")
    Call<List<Mis_monitoringitem>> Callback_AddProcess(@Field("mc_div") String mc_div,
                                                         @Field("mc_name") String mc_name);


    @FormUrlEncoded
    @POST("/service/json/android_php/mis_add_station.php")
    Call<List<Mis_monitoringitem>> Callback_AddStation(@Field("mc_div") String mc_div,
                                                         @Field("mc_name") String mc_name);

    @FormUrlEncoded
    @POST("/service/json/android_php/manager_station/mis_callparameter.php")
    Call<List<Mis_parameter>> CallbackParameter(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_callmonitoritem.php")
    Call<List<Mis_monitoringitem>> CallbackMonitoring(@Field("station") String station);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_calldeleteparameter.php")
    Call<List<Mis_monitoringitem>> Callback_DeleteParameter(@Field("mo_id") String mo_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/manager_station/mis_calldeleteprcess.php")
    Call<List<Mis_monitoringitem>> Callback_DeleteProcess(@Field("mc_id") String mc_id);

//    @FormUrlEncoded
//    @POST("/service/json/android_php/mis_calldeleteprocess.php")
//    Call<List<Mis_monitoringitem>> Callback_DeleteProcess(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_calldeletestation.php")
    Call<List<Mis_monitoringitem>> Callback_DeleteStation(@Field("mc_div") String mc_div);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_monitoritem.php")
    Call<List<Mis_descrip>> Callback_Detail(@Field("mc_id") String mc_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_history.php")
    Call<List<Mis_history>> Callback_History(@Field("mo_pram") String mo_pram);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_login.php")
    Call<List<Mis_login>> Callback_Login(@Field("username") String username,
                                         @Field("password") String password);

    @POST("/service/json/android_php/mis_service.php")
    Call<List<Mis_service>> CallbackService();

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_setnotification.php")
    Call<Mis_notification> Callback_Setstatusnotfication(@Field("NotificationStatus") String statusNotification,
                                                         @Field("mo_id") String mo_id);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_check_register.php")
    Call<List<Mis_register>> Callback_CheckRegister( @Field("username") String username);

    @FormUrlEncoded
    @POST("/service/json/android_php/mis_adduser.php")
    Call<List<Mis_register>> Callback_AddRegister(    @Field("username") String username,
                                                      @Field("password") String password,
                                                      @Field("division") String division);

}
