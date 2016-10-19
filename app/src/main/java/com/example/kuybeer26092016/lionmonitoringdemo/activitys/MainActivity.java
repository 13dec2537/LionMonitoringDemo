package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentAccount;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentDk100;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentTower2;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String mUsername,mImage,mPosition,mAnim,mUserDivision;
    private SharedPreferences sp,spApp_Gone,spNT;
    private Toolbar toolbar;
    private  SharedPreferences.Editor editor,editor_App_Gone,editor_NT;
    private  NavigationView navigationView;
    private  ImageView imageView;
    private  DrawerLayout mDrawerlayout;
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

        /***************set Anim *************************/
        mAnim = getIntent().getExtras().getString("Ianim","");
        if(mAnim.equals("1")){
            this.overridePendingTransition(R.anim.anim_silde_out_left,R.anim.anim_silde_out_left);
        }else if(mAnim.equals("2")){
            this.overridePendingTransition(R.anim.anim_silde_in_left,R.anim.anim_silde_in_left);
        }
        /***************set Anim *************************/

        //*************************** SharedPreferences Get Data Account form LoginActivity *******************/
        sp = getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        mUsername = sp.getString("username","No Information");
        mPosition = sp.getString("division","No Information");
        mUserDivision = sp.getString("Userdivision","No Information");
        mImage = sp.getString("imageUrl","No Information");
        //*************************** SharedPreferences Get Data Account form LoginActivity *******************/

        //************************ Get View Navigation ******************************************//
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View Nav_view = navigationView.getHeaderView(0);
        imageView = (ImageView) Nav_view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.ic_me);
        TextView txtname = (TextView) Nav_view.findViewById(R.id.name);
        TextView txtposition = (TextView) Nav_view.findViewById(R.id.position);
        //************************ Get View Navigation ******************************************/

        /***************************** Set privilege Menu Navigation *************************/
        SetMenuNavigation();
        /***************************** Set privilege Menu Navigation *************************/

       /**************************** Check Position Detail and Set Profile Account  *********************/
        if(mPosition.equals("TOWER2")){
            FragTower2();
            toolbar.setTitle("TOWER 2");
        }
        else if(mPosition.equals("TW2")){
            FragTower2();
            toolbar.setTitle("TOWER 2");
        }
        else if(mPosition.equals("DK100")){
            FragDk100();
            toolbar.setTitle("DK 100");
        }
        else if(mPosition.equals("ADMIN")){
            FragTower2();
            toolbar.setTitle("TOWER 2");
        }
        txtname.setText(mUsername.toUpperCase());
        txtposition.setText(mPosition.toUpperCase());
        Picasso.with(this).load(mImage)
                .placeholder(R.drawable.person).error(R.drawable.person).into(imageView);
        navigationView.setNavigationItemSelectedListener(this);
        /**************************** Check Position Detail and Set Profile Account  *********************/

        //*************** SharedPreferences And Set ViewGone  ***********************************//
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        editor_App_Gone.putString("Main_Gone" , "0");
        editor_App_Gone.putString("Detail_Gone" , "1");
        editor_App_Gone.putString("History_Gone" , "1");
        editor_App_Gone.putString("Upload_Gone" , "1");
        editor_App_Gone.commit();
        //*************** SharedPreferences And Set ViewGone  ***********************************//
        Snackbar("Login compile !");
    }

    private void SetMenuNavigation() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_menu = navigationView.getMenu();
        MenuItem menuTw2 = nav_menu.findItem(R.id.nav_tower2);
        MenuItem menuDk100 = nav_menu.findItem(R.id.nav_dk100);
        if(mUserDivision.equals("ADMIN")){
            menuTw2.setVisible(true);
            menuDk100.setVisible(true);
        } else if(mUserDivision.equals("TOWER2")){
            menuTw2.setVisible(true);
            menuDk100.setVisible(false);
        }else if(mUserDivision.equals("DK100")){
            menuDk100.setVisible(true);
            menuTw2.setVisible(false);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        spNT = getSharedPreferences("NOTIFICATION",Context.MODE_PRIVATE);
        editor_NT = spNT.edit();
        Integer returnAc = spNT.getInt("returnAc",0);
        if(returnAc>=1 && returnAc<=4){
            FragTower2();
            editor_NT.clear();
            editor_NT.commit();
        }
        else if(returnAc>=5 && returnAc<=16){
            FragDk100();
            editor_NT.clear();
            editor_NT.commit();
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
                .placeholder(R.drawable.person).error(R.drawable.person).into(imageView);
        navigationView.setNavigationItemSelectedListener(this);
        if (id == R.id.nav_tower2) {
            FragTower2();
            toolbar.setTitle("TOWER 2");
        } else if (id == R.id.nav_dk100) {
            FragDk100();
            toolbar.setTitle("DK 100");
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
    public void FragTower2(){
        FragmentTower2 fragtw2 = new FragmentTower2();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainlayoutFragments,fragtw2);
        ft.commit();
    }
    public void FragDk100(){
        FragmentDk100 fragdk100 = new FragmentDk100();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainlayoutFragments,fragdk100);
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
}
