package com.example.kuybeer26092016.lionmonitoringdemo.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
    private String mUsername;
    private ImageView image;
    private Context mContext;
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

    /*********************Tab Layout ******************/
    /*********************Tab Layout ******************/
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
        image = (ImageView)getView().findViewById(R.id.image);
        ((TextView)getView().findViewById(R.id.txtUsername)).setText(sp.getString("username",""));
        ((TextView)getView().findViewById(R.id.txtDivision)).setText(sp.getString("division",""));
        mImage = (ImageView)getView().findViewById(R.id.image);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogEditImage();
            }
        });
        SetImage();
        ((Button)getView().findViewById(R.id.editimage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getString("division","").equals("ADMIN")){
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
    private void UploadImage(){
        try{
            Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
            String encodedImage = ImageBase64.encode(bitmap);
            //post image to server
            HashMap<String, String> PostData = new HashMap<String, String>();
            PostData.put("image",encodedImage);
            PostData.put("uid",sp.getString("username",""));
            PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), PostData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if(s.contains("upload_success")){
                        Log.i("Tag","Upload Finish");
                        Toast.makeText(getActivity(),"Upload Success",Toast.LENGTH_SHORT).show();
                        SetImage();
                    }else if(s.contains("upload_failed")){
                        Toast.makeText(getActivity(),"upload_failed",Toast.LENGTH_SHORT).show();
                    } else if(s.contains("image_not_in")){
                        Toast.makeText(getActivity(),"image_not_in",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            task.execute("http://www.thaidate4u.com/service/json/peerapong/upload_images_mc.php");
            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {
                    Toast.makeText(getActivity(),"Error while uploading 1",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Toast.makeText(getActivity(),"Error while uploading 2",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Toast.makeText(getActivity(),"Error while uploading 3",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Toast.makeText(getActivity(),"Error while uploading 4",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (FileNotFoundException e){
            Toast.makeText(getActivity(),"Something worng while encoding photo",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                seleletedPhoto = photoPath;
                UploadImage ();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    mImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                seleletedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    mImage.setImageBitmap(bitmap);
                    UploadImage();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(),
                            "Something Wrong while choosing photos", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Bitmap getRotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1 = Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
        return bitmap1;
    }
    private void SetImage(){
        Picasso.with(getContext()).load(sp.getString("imageUrl",""))
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.progress_aniloadimg)
                .error(R.drawable.person)
                .into(mImage);
    }
    public void mAlertDialogEditImage(){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setTitleText("CAMERA");
        sweetAlertDialog.setContentText("Select Take photos OR Gallery ?");
        sweetAlertDialog.setCancelText("Gallery");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
                        sweetAlertDialog.dismiss();
                    }
                });
        sweetAlertDialog .setConfirmText("Take photo");
        sweetAlertDialog .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        try {
                            startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                            cameraPhoto.addToGallery();
                            sweetAlertDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        sweetAlertDialog.show();

    }
    public void mAlertDialogSetting(){
        new BottomSheet.Builder(getActivity()).title("SETTING").sheet(R.menu.list).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.username:
                        Toast.makeText(getActivity(),"Change Username",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.password:
                        Toast.makeText(getActivity(),"Change Password",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.edit_engine:
                        Toast.makeText(getActivity(),"Edit_InformationEngine",Toast.LENGTH_LONG).show();
                        Tran_FragEditprofile();
                        break;
                }
            }
        }).show();

    }
    private void Tran_FragEditprofile(){
        FragmentEditmcname FragEdit = new FragmentEditmcname();
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.FrameAccount,FragEdit);
        ft.commit();
    }

}
