package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.activitys.UploadImageActivity;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccount extends Fragment {
    private static final String IMAGEURL = "http://www.thaidate4u.com/service/json/img/";
    private String mId;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;
    private String seleletedPhoto;
    private LinearLayout mLinear;
    private SweetAlertDialog sweetAlertDialog;
    private ImageView mImage;
    private RequestCreator picasso;
    public FragmentAccount() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mLinear = (LinearLayout)getView().findViewById(R.id.XML_LinearAccount);
        sp = getActivity().getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        editor = sp.edit();
        cameraPhoto = new CameraPhoto(getActivity());
        galleryPhoto = new GalleryPhoto(getActivity());
        sweetAlertDialog = new SweetAlertDialog(getActivity());
        ((TextView)getView().findViewById(R.id.txtUsername)).setText(sp.getString("username",""));
        ((TextView)getView().findViewById(R.id.txtDivision)).setText(sp.getString("Userdivision",""));
        mId = String.valueOf(sp.getString("username",""));
        mImage = (ImageView)getView().findViewById(R.id.XML_images);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(),UploadImageActivity.class);
                intent.putExtra("I_URL_IMAGE" ,sp.getString("imageUrl",""));
                intent.putExtra("ID_IMAGE",mId);
                startActivity(intent);
            }
        });
        SetImage();
        SetOnClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        SetImage();
    }

    private void SetOnClick() {
        ((Button)getView().findViewById(R.id.editimage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getString("Userdivision","").equals("ADMIN")){
                    mAlertDialogSetting();
                }
                else{
                    Snackbar("For Administrator");
                }
            }
        });
    }

    private void Snackbar(String messages){
        Snackbar snackbar = Snackbar.make(mLinear,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static FragmentAccount newInstent(String username) {
        FragmentAccount fragAc = new FragmentAccount();
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        fragAc.setArguments(bundle);
        return fragAc;
    }
    private void SetImage(){
        Log.d("C","SETIMAGE");
        picasso = Picasso.with(getContext()).load(sp.getString("imageUrl",""));
        Log.d("C",String.valueOf(sp.getString("imageUrl","")));
        picasso.networkPolicy(NetworkPolicy.NO_CACHE);
        picasso.memoryPolicy(MemoryPolicy.NO_CACHE);
        picasso.placeholder(R.drawable.progress_aniloadimg);
        picasso.error(R.drawable.person);
        picasso.into(mImage);
    }

    public void mAlertDialogSetting(){
        new BottomSheet.Builder(getActivity()).title("SETTING").sheet(R.menu.list).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.username:

                        break;
                    case R.id.password:;

                        break;
                    case R.id.edit_station:
                        Toast.makeText(getActivity(),"EDIT STATION",Toast.LENGTH_LONG).show();
                        Tran_FragmentEditstation();
                        break;
                }
            }
        }).show();

    }
    private void Tran_FragmentEditstation(){
        FragmentEditstation FragEdit = new FragmentEditstation();
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FrameAccount,FragEdit);
        ft.commit();
    }

}
