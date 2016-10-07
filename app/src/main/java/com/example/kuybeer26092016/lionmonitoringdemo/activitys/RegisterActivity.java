package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_adddata;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_history;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_login;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_register;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private String username,password,password_again,division;
    private String CheckUsername;
    private EditText edUsername,edPassword,edPassword_again;
    private Spinner spDivision;
    private Button btn_register;
    private ManagerRetrofit mManager;
    private Snackbar snackbar;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mManager = new ManagerRetrofit();
        //*********** Set XML ************************************//
        progressBar = (ProgressBar)findViewById(R.id.Pgregister);
        progressBar.setVisibility(View.GONE);
        relativeLayout = (RelativeLayout)findViewById(R.id.activity_register);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPassword_again = (EditText) findViewById(R.id.edPasswordAgain);
        btn_register = (Button)findViewById(R.id.btnregister);
        spDivision = (Spinner)findViewById(R.id.spDivision);
        List<String> list = new ArrayList<String>();
        list.add("Select Division");
        list.add("Admin");
        list.add("Staff");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spDivision.setAdapter(dataAdapter);

        // Spinner item selection Listener  
        addListenerOnSpinnerItemSelection();
        //*********** Set XML ************************************//
    }

    private void addListenerOnSpinnerItemSelection() {
        spDivision.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


    @Override
    protected void onStart() {
        super.onStart();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edUsername.getText().toString();
                password = edPassword.getText().toString();
                password_again = edPassword_again.getText().toString();
                division = spDivision.getSelectedItem().toString();
                if(username.length()>5 && (password.length()>5 && password.equals(password_again)) && division != ""){
                    CheckUsername(username,password,division);
                    progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    snackbar = Snackbar.make(relativeLayout,"Register unsuccessful",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
    public void CheckUsername(String username,String password,String division){
        final String mUsername = username;
        final String mPassword = password;
        final String mDivision = division;
        Call<List<Mis_register>> call = mManager.getmService().Callback_CheckRegister(username);
        call.enqueue(new Callback<List<Mis_register>>() {
            @Override
            public void onResponse(Call<List<Mis_register>> call, Response<List<Mis_register>> response) {
                if(response.isSuccessful()){
                    Log.d("TEST" , "compile");
                    List<Mis_register> ListItem = response.body();
                    for(int i = 0 ; i<ListItem.size();i++){
                        snackbar = Snackbar.make(relativeLayout,"Username has already",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        progressBar.setVisibility(View.GONE);
                    }
                    if(ListItem.size() == 0){
                        snackbar = Snackbar.make(relativeLayout,"Register successful",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        edPassword.setText("");
                        edUsername.setText("");
                        edPassword_again.setText("");
                        Add_Account(mUsername,mPassword,mDivision);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true){
                                    try {
                                        Thread.sleep(4000);
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                    }
                                }


                            }
                        }).start();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mis_register>> call, Throwable t) {

            }
        });
    }
    private void Add_Account(final String username, String password, String division) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Mis_register register = new Mis_register();
        Call<List<Mis_register>> call = service.Callback_AddRegister(username,password,division);
//        username, password,division
       call.enqueue(new Callback<List<Mis_register>>() {
           @Override
           public void onResponse(Call<List<Mis_register>> call, Response<List<Mis_register>> response) {
               if(response.isSuccessful()){
                   List<Mis_register> List = response.body();
                   for(int i = 0 ; i < List.size();i++) {
                       CheckUsername = List.get(i).getUsername();
                       Log.d("TEST", "CheckUsername " + CheckUsername);
                   }
               }else {
                   Log.d("TEST","CheckUsername False");
               }
           }

           @Override
           public void onFailure(Call<List<Mis_register>> call, Throwable t) {

           }
       });
    }
}
