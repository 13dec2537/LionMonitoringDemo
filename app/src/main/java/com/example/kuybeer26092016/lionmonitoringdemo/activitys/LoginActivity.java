package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.adapters.AdapterLogin;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnlogin;
    private EditText edUsername,edPassword;
    private ProgressBar progressBar;
    //Manager
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
        setContentView(R.layout.activity_login);
        mManager = new ManagerRetrofit();
        mAdapter = new AdapterLogin();
        edUsername = (EditText)findViewById(R.id.edUsername);
        edPassword = (EditText)findViewById(R.id.edPassword);
        progressBar = (ProgressBar)findViewById(R.id.PBLogin);
        progressBar.setVisibility(View.GONE);
        btnlogin = (Button) findViewById(R.id.btnlogin);
         sp = getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowListMc();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        UsernameShared  = sp.getString("UsernameShared","");
        PasswordShared  = sp.getString("PasswordShared","");
        edUsername.setText(UsernameShared);
        edPassword.setText(PasswordShared);
    }

    private void ShowListMc(){
        Call<List<Mis_login>> call = mManager.getmService().Callback_Login(edUsername.getText().toString(),
                edPassword.getText().toString());
        call.enqueue(new Callback<List<Mis_login>>() {
            @Override
            public void onResponse(Call<List<Mis_login>> call, Response<List<Mis_login>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<Mis_login> tower2List = response.body();
                    for(int i = 0 ; i < tower2List.size();i++){
                        String Username = tower2List.get(i).getUsername();
                        String Password = tower2List.get(i).getPassword();
                        Toast.makeText(LoginActivity.this,"Login compile !",Toast.LENGTH_SHORT).show();
                        Log.d("TEST" , "user : " + String.valueOf(Username) + " pass : " + String.valueOf(Password));
                        Mis_login misMclist = tower2List.get(i);
                        mAdapter.addLogin(misMclist);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("username",Username);
                        startActivity(intent);
                        finish();
                    }
                    if(tower2List.size() == 0){
                        Toast.makeText(LoginActivity.this,"Your Login Name or Password is invalid !",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                }
            }

            @Override
            public void onFailure(Call<List<Mis_login>> call, Throwable t) {
                Log.d("TEST" , "False");
            }
        });
    }

    @Override
    public void onBackPressed() {
        mAlertDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putString("UsernameShared",edUsername.getText().toString());
        editor.putString("PasswordShared",edPassword.getText().toString());
        editor.commit();
    }

    public void mAlertDialog(){
        mAlertDialog = new AlertDialog.Builder(LoginActivity.this);
        mAlertDialog.setTitle("Close App !");
        mAlertDialog.setMessage("TEST Alert");
        mAlertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        mAlertDialog.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = mAlertDialog.create();
        alertDialog.show();
    }
}