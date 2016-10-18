package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterLogin;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;
import com.example.kuybeer26092016.lionmonitoringdemo.service.BackgroundService;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.fabric.sdk.android.Fabric;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String BASE_IMAGE = "http://www.thaidate4u.com/service/json/img/";
    private String Username, Password, imageUrl, Division = "0";
    private Boolean ClearDataAccount = false;
    private Boolean mLogin_Again;
    private Button btnlogin,btnregister;
    private EditText edUsername,edPassword;
    private ProgressBar progressBar;
    //Manager
    private LinearLayout mLinearLayout;
    private ManagerRetrofit mManager;
    private AdapterLogin mAdapter;
    private AlertDialog.Builder mAlertDialog;
    //SharedPreferences
    private SharedPreferences sp;
    private  SharedPreferences.Editor editor;
    private String UsernameShared,PasswordShared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        mLinearLayout = (LinearLayout)findViewById(R.id.XML_Coorlayout);
        mManager = new ManagerRetrofit();
        mAdapter = new AdapterLogin();
        edUsername = (EditText)findViewById(R.id.edUsername);
        edPassword = (EditText)findViewById(R.id.edPassword);
        progressBar = (ProgressBar)findViewById(R.id.PBLogin);
        progressBar.setVisibility(View.GONE);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnregister = (Button)findViewById(R.id.btnLinkToRegisterScreen);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        sp = getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowListMc();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ClearDataAccount = getIntent().getExtras().getBoolean("ClearDataAccount",false);
        }
        CheckInternet();
    }

    private void CheckInternet() {
        ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf=cn.getActiveNetworkInfo();
        if(nf != null && nf.isConnected()==true )
        {
            SetEnable(true,true,true,true);
        }
        else
        {
            btnlogin.setText("Exit");
            btnregister.setText("Network Not Available");
            SnackbarAlaert();
            SetEnable(false,false,true,false);
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }

    private void SnackbarAlaert() {
        final LinearLayout coordinatorLayout = (LinearLayout) findViewById(R.id
                .XML_Coorlayout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Network Not Available", Snackbar.LENGTH_LONG)
                .setAction("Undo",null);
        snackbar.setActionTextColor(Color.RED);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
          startService(new Intent(getBaseContext(), BackgroundService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLogin_Again = sp.getBoolean("mLogin_Again",false);
        if(ClearDataAccount.equals(true)){
            edUsername.setText("");
            edPassword.setText("");
            ClearDataAccount = false;
        }else{
            UsernameShared  = sp.getString("UsernameShared","");
            PasswordShared  = sp.getString("PasswordShared","");
            edUsername.setText(UsernameShared);
            edPassword.setText(PasswordShared);
            if(mLogin_Again.equals(true)){
                ShowListMc();
            }
        }

    }

    private void ShowListMc(){
        progressBar.setVisibility(View.VISIBLE);

        Call<List<Mis_login>> call = mManager.getmService().Callback_Login(edUsername.getText().toString(),
                edPassword.getText().toString());
        call.enqueue(new Callback<List<Mis_login>>() {
            @Override
            public void onResponse(Call<List<Mis_login>> call, Response<List<Mis_login>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_login> tower2List = response.body();
                    for(int i = 0 ; i < tower2List.size();i++){
                        SetEnable(true,true,true,true);
                        editor.putBoolean("mLogin_Again",true);
                        editor.commit();
                        Username = tower2List.get(i).getUsername();
                        Password = tower2List.get(i).getPassword();
                        imageUrl = String.valueOf(BASE_IMAGE+Username+".jpg");
                        Log.d("TEST","imageUrl : " + imageUrl);
                        if(imageUrl.trim().length()<0){
                            imageUrl = "http://www.thaidate4u.com/service/json/img/aoh.jpg";
                        }
                        Division = tower2List.get(i).getDivision();
                        Mis_login misMclist = tower2List.get(i);
                        mAdapter.addLogin(misMclist);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        intent.putExtra("username",Username);
//                        intent.putExtra("imageUrl",imageUrl);
//                        intent.putExtra("position",Position);
                        editor.putString("username",Username);
                        editor.putString("imageUrl",imageUrl);
                        editor.putString("division",Division);
                        editor.putString("Userdivision",Division);
                        editor.commit();
                        intent.putExtra("Ianim","1");
                        startActivity(intent);
                        finish();
                    }
                    if(tower2List.size() == 0){
                        Snackbar("Your Login Name or Password is invalid !");
                        SetEnable(true,true,true,true);
                    }

                }
                else{
                }
            }

            @Override
            public void onFailure(Call<List<Mis_login>> call, Throwable t) {
                Snackbar("Your Login Name or Password is invalid !");
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    public void SetEnable(Boolean bl1,Boolean bl2,Boolean bl3,Boolean bl4){
        edUsername.setEnabled(bl1);
        edPassword.setEnabled(bl2);
        btnlogin.setEnabled(bl3);
        btnregister.setEnabled(bl4);
    }
    @Override
    public void onBackPressed() {
        mAlertDialogExit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putString("UsernameShared",edUsername.getText().toString());
        editor.putString("PasswordShared",edPassword.getText().toString());
        editor.commit();
    }

    private void mAlertDialogExit(){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setTitleText("Exit !");
        sweetAlertDialog.setContentText("Are you sure went to Exit Application !");
        sweetAlertDialog.setCancelText("Cancel");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        new CountDownTimer(2000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        //ป้องกันการกดปุ้ม confirm อีกครั้ง ในระหว่าง show ani //
                                    }
                                });
                            }

                            public void onFinish() {
                                sDialog.cancel();
                            }
                        }.start();
                    }
                });
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {

                        new CountDownTimer(2000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                sDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }

                            public void onFinish() {
                                finish();
                            }
                        }.start();
                    }
                });
        sweetAlertDialog.show();
    }
    private void Snackbar(String messages){
        Snackbar snackbar = Snackbar.make(mLinearLayout,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}