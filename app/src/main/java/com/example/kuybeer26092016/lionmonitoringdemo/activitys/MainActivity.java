package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentAccount;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentDk100;
import com.example.kuybeer26092016.lionmonitoringdemo.fragments.FragmentTower2;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String mUsername,mImage,mPosition;
    private AlertDialog.Builder mAlertDialog;
    private SharedPreferences sp,spApp_Gone;
    private String mMc_name = "";
    private Toolbar toolbar;
    private  SharedPreferences.Editor editor,editor_App_Gone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //************************ Get View Navigation ******************************************//
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View Nav_view = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView) Nav_view.findViewById(R.id.imageView);
        TextView txtname = (TextView) Nav_view.findViewById(R.id.name);
        TextView txtposition = (TextView) Nav_view.findViewById(R.id.position);
        //************************ Get View Navigation ******************************************/

        //*************************** SharedPreferences Get Data Account form LoginActivity *******************/
        sp = getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        mUsername = sp.getString("username","No Information");
        mPosition = sp.getString("position","No Information");
        mImage = sp.getString("imageUrl","No Information");
//        mUsername = getIntent().getExtras().getString("username");
//        mImage = getIntent().getExtras().getString("imageUrl");
//        mPosition = getIntent().getExtras().getString("position");
        //*************************** SharedPreferences Get Data Account form LoginActivity *******************/

        /**************************** Check Position Detail and Set Profile Account  *********************/
        if(mPosition.equals("staff")){
            FragTower2();
        }else if(mPosition.equals("admin")){
            FragDk100();
        }
        txtname.setText(mUsername);
        txtposition.setText(mPosition);
        Picasso.with(this).load(mImage)
                .placeholder(R.drawable.ic_me).error(R.drawable.ic_me).into(imageView);
        navigationView.setNavigationItemSelectedListener(this);
        /**************************** Check Position Detail and Set Profile Account  *********************/

        //*************** SharedPreferences ***********************************//
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        editor_App_Gone.putString("Main_Gone" , "0");
        editor_App_Gone.commit();
        //*************** SharedPreferences ***********************************//

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
        editor_App_Gone.putString("Main_Gone" , "0");
        editor_App_Gone.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
        mAlertDialog = new AlertDialog.Builder(MainActivity.this);

        mAlertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        mAlertDialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                i.putExtra("ClearDataAccount" , true);
                editor.putBoolean("mLogin_Again",false);
                editor.commit();
                startActivity(i);
                finish();
            }
        });

        AlertDialog alertDialog = mAlertDialog.create();
        alertDialog.show();
    }
}
