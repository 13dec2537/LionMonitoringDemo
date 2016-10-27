package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentAccount;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentMonitorItem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_menu;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private String mUsername,mImage,mPosition,mAnim,mUserDivision;
    private SharedPreferences sp,spApp_Gone,spNT,spmenu;
    private Toolbar toolbar;
    private  SharedPreferences.Editor editor,editor_App_Gone,editor_NT,edit_spmenu;
    private  NavigationView navigationView;
    private  ImageView imageView;
    private  DrawerLayout mDrawerlayout;
    private TextView txtname,txtposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        runAnim();
        setSharedPreferences();
        setFindByIdAndSetView();
        Picasso.with(this).load(mImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .rotate(90)
                .resize(128,128)
                .centerCrop()

                .placeholder(R.drawable.placeholder_img_engine).error(R.drawable.placeholder_img_engine).into(imageView);
        Snackbar("Login compile !");
        CallManu();
        CallFragByPosition();
    }

    private void runAnim() {
        mAnim = getIntent().getExtras().getString("Ianim","");
        if(mAnim.equals("1")){
            this.overridePendingTransition(R.anim.anim_silde_out_left,R.anim.anim_silde_out_left);
        }else if(mAnim.equals("2")){
            this.overridePendingTransition(R.anim.anim_silde_in_left,R.anim.anim_silde_in_left);
        }
    }

    private void setFindByIdAndSetView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View Nav_view = navigationView.getHeaderView(0);
        imageView = (ImageView) Nav_view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_me);
        txtname = (TextView) Nav_view.findViewById(R.id.name);
        txtposition = (TextView) Nav_view.findViewById(R.id.position);
        SetMenuNavigation();
        txtname.setText(mUsername.toUpperCase());
        txtposition.setText(mUserDivision.toUpperCase());
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setSharedPreferences() {
        sp = getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor_App_Gone  = spApp_Gone.edit();
        mUsername = sp.getString("username","No Information");
        mPosition = sp.getString("division","No Information");
        mUserDivision = sp.getString("Userdivision","No Information");
        mImage = sp.getString("imageUrl","No Information");
        editor_App_Gone.putString("Main_Gone" , "0");
        editor_App_Gone.putString("Detail_Gone" , "1");
        editor_App_Gone.putString("History_Gone" , "1");
        editor_App_Gone.putString("Upload_Gone" , "1");
        editor_App_Gone.commit();
    }

    private void CallFragByPosition() {
        Log.d("C",mPosition);
        if(mPosition.equals("ADMIN")){
            FragMtitem("TW2");
        }
        else{
            FragMtitem(mPosition);
        }

    }


    private void SetMenuNavigation() {
        MenuItem[] menuArray = new MenuItem[20];
        MenuItem[] menuFindId = new MenuItem[8];
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_menu = navigationView.getMenu();
        CallManu();
        menuFindId[0] = nav_menu.findItem(R.id.menu_1);
        menuFindId[1] = nav_menu.findItem(R.id.menu_2);
        menuFindId[2] = nav_menu.findItem(R.id.menu_3);
        menuFindId[3] = nav_menu.findItem(R.id.menu_4);
        menuFindId[4] = nav_menu.findItem(R.id.menu_5);
        menuFindId[5] = nav_menu.findItem(R.id.menu_6);
        menuFindId[6] = nav_menu.findItem(R.id.menu_7);
        menuFindId[7] = nav_menu.findItem(R.id.menu_8);
        if(mUserDivision.equals("ADMIN")){
        }
        else{
            switch (mUserDivision) {
                case "TW2":
                    for(int i = 0 ; i < 7 ; i ++){
                        if(i != 0){
                            menuFindId[i].setVisible(false);
                        }
                    }
                    break;
                case "DK100":
                    for(int i = 0; i < 7 ; i ++){
                        if(i != 1){
                            menuFindId[i].setVisible(false);
                        }
                    }
                    break;
                case "PK200":
                    for(int i = 0 ; i < 7 ; i ++){
                        if(i != 2){
                            menuFindId[i].setVisible(false);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        editor_App_Gone.putString("Main_Gone" , "1");
        editor_App_Gone.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spNT = getSharedPreferences("DataAccount",Context.MODE_PRIVATE);
        editor_NT = spNT.edit();
        String returnAc = spNT.getString("returnAc","");
        if(returnAc.equals("TW2")){
            FragMtitem("TW2");
            spNT.edit().remove("returnAc").commit();
        }
        else if(returnAc.equals("DK100")){
            FragMtitem("DK100");
            spNT.edit().remove("returnAc").commit();
        }
        else if (returnAc.equals("PK200")){
            FragMtitem("PK200");
            spNT.edit().remove("returnAc").commit();
        }
        editor_App_Gone.putString("Main_Gone" , "0");
        editor_App_Gone.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Picasso.with(this).load(mImage)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .rotate(90)
                .placeholder(R.drawable.person).error(R.drawable.person).into(imageView);
        String menu[] = new String[20];
        for (int i = 0 ; i< menu.length ; i++){
            menu[i] = spmenu.getString(String.valueOf("menu_" + i),"");
        }
        if (id == R.id.menu_1) {
            FragMtitem(menu[0]);
            toolbar.setTitle( menu[0]);
        } else if (id == R.id.menu_2) {
            FragMtitem(menu[1]);
            toolbar.setTitle( menu[1]);
        } else if (id == R.id.menu_3) {
            FragMtitem(menu[2]);
            toolbar.setTitle(menu[2]);
        } else if (id == R.id.menu_4) {
            FragMtitem(menu[3]);
            toolbar.setTitle(menu[3]);
        } else if (id == R.id.menu_5) {
            FragMtitem(menu[4]);
            toolbar.setTitle(menu[4]);
        } else if (id == R.id.menu_6) {
            FragMtitem(menu[5]);
            toolbar.setTitle(menu[5]);
        } else if (id == R.id.menu_7) {
            FragMtitem(menu[6]);
            toolbar.setTitle(menu[6]);
        } else if (id == R.id.menu_8) {
            FragMtitem(menu[7]);
            toolbar.setTitle(menu[7]);
        } else if (id == R.id.nav_me) {
            FragAccount();
            toolbar.setTitle("ACCOUNT");
        } else if (id == R.id.nav_logout) {
            mAlertDialogLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void FragMtitem(String process){
        FragmentMonitorItem FragMtitem = FragmentMonitorItem.newInstant(process);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainlayoutFragments,FragMtitem);
        ft.commit();
    }
    public void FragAccount(){
        FragmentAccount fragAcc = FragmentAccount.newInstent(mUsername);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainlayoutFragments,fragAcc);
        ft.commit();
    }
    public void mAlertDialogLogout(){

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Log Out!");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setContentText("Are you sure went tto log out!");
        sweetAlertDialog.setCancelText("Cancel");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        new CountDownTimer(2000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    }
                                });
                                sDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
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
                                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                                i.putExtra("ClearDataAccount" , true);
                                editor_App_Gone.putString("Main_Gone","0");
                                editor_App_Gone.putString("Detail_Gone","0");
                                editor_App_Gone.putString("History_Gone","0");
                                editor_App_Gone.commit();
                                editor.putBoolean("mLogin_Again",false);
                                editor.putString("UsernameShared","");
                                editor.putString("PasswordShared","");
                                editor.commit();
                                sDialog.cancel();
                                startActivity(i);
                                finish();
                            }
                        }.start();
                    }
                });
        sweetAlertDialog.show();
    }
    private void Snackbar(String messages){
        Snackbar snackbar = Snackbar.make(mDrawerlayout,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    private void CallManu(){
        spmenu = getSharedPreferences("MENU",Context.MODE_PRIVATE);
        edit_spmenu = spmenu.edit();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Mis_monitoringitem register = new Mis_monitoringitem();
        Call<List<Mis_menu>> call = service.CallbackMenu();
        call.enqueue(new Callback<List<Mis_menu>>() {
            @Override
            public void onResponse(Call<List<Mis_menu>> call, Response<List<Mis_menu>> response) {
                MenuItem[] menuArray = new MenuItem[20];
                MenuItem[] menuFindId = new MenuItem[8];
                if(response.isSuccessful()){
                    final List<Mis_menu> Listitem = response.body();
                    navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu nav_menu = navigationView.getMenu();
                    try{
                        int SIZEJSON = Listitem.size();
                        int SIZEID = menuFindId.length-1;
                        menuFindId[0] = nav_menu.findItem(R.id.menu_1);
                        menuFindId[1] = nav_menu.findItem(R.id.menu_2);
                        menuFindId[2] = nav_menu.findItem(R.id.menu_3);
                        menuFindId[3] = nav_menu.findItem(R.id.menu_4);
                        menuFindId[4] = nav_menu.findItem(R.id.menu_5);
                        menuFindId[5] = nav_menu.findItem(R.id.menu_6);
                        menuFindId[6] = nav_menu.findItem(R.id.menu_7);
                        menuFindId[7] = nav_menu.findItem(R.id.menu_8);
                        for (int i = 0 ; i< Listitem.size() ; i ++){
                            menuArray[i]  = menuFindId[i];
                            menuArray[i].setTitle(Listitem.get(i).getMc_div());
                            edit_spmenu.putString(String.valueOf("menu_"+i),Listitem.get(i).getMc_div()).commit();
                            if(i == SIZEJSON-1){
                                i = i+1;
                                for(int j = i ; j <= SIZEID ; j++){
                                    menuFindId[j].setVisible(false);
                                }
                            }
                        }
                    }catch (Exception e){
                        Log.d("C","ERROR");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mis_menu>> call, Throwable t) {

            }
        });
    }
}
