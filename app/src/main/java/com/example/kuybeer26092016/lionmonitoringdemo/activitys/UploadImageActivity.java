package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.squareup.picasso.RequestCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class UploadImageActivity extends AppCompatActivity {
    private String Get_URL_IMAGE,Get_ID_IMAGE;
    private Button mBtnCapture,mBtnGallery,mBtnUpload;
    private ImageView mBtnover90,mBtn90;
    private ImageView mImage;
    private Context mContext;
    private CameraPhoto cameraphoto;
    private GalleryPhoto galleryphoto;
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private String seleletedPhoto = "";
    private SharedPreferences sp_uploadimg,spApp_Gone;
    private SharedPreferences.Editor editor_uploadimg,editor_App_Gone;
    private RequestCreator picasso;
    private int ActionClick = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_upload_image);
        mContext = this;
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        sp_uploadimg = getSharedPreferences("img",Context.MODE_PRIVATE);
        editor_uploadimg = sp_uploadimg.edit();
        cameraphoto = new CameraPhoto(this);
        galleryphoto = new GalleryPhoto(this);
        SetIntent();
        SetFinaById();
        SetView();
        SetOnClick();

    }

    private void SetView() {
        picasso = Picasso.with(mContext).load(Get_URL_IMAGE);
        picasso.memoryPolicy(MemoryPolicy.NO_CACHE);
        picasso.networkPolicy(NetworkPolicy.NO_CACHE);
        picasso.placeholder(R.drawable.person);
        picasso.centerCrop();
        picasso.resize(128, 128);
        picasso.error(R.drawable.person);
        picasso.into(mImage);
    }

    private void SetIntent() {
        Get_URL_IMAGE = getIntent().getExtras().getString("I_URL_IMAGE");
        Get_ID_IMAGE = getIntent().getExtras().getString("ID_IMAGE");
    }
    private void SetFinaById() {
        mBtnUpload = (Button)findViewById(R.id.XML_btnupload);
        mBtnGallery = (Button)findViewById(R.id.XML_btnGallery);
        mBtnCapture = (Button)findViewById(R.id.XML_btnCapture);
        mBtnover90 = (ImageView)findViewById(R.id.XML_rotateover90);
        mBtn90 = (ImageView)findViewById(R.id.XML_rotate90);
        mImage = (ImageView)findViewById(R.id.XML_IMAGE);
    }
    private void SetOnClick() {
        mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                try {
                    ActionClick = 0;
                    editor_App_Gone.putString("Upload_Gone" , "0");
                    editor_App_Gone.commit();
                    startActivityForResult(cameraphoto.takePhotoIntent(),CAMERA_REQUEST);
                    editor_uploadimg.putString("cameraphoto",cameraphoto.getPhotoPath());
                    editor_uploadimg.commit();
                    cameraphoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionClick = 0;
                startActivityForResult(galleryphoto.openGalleryIntent(),GALLERY_REQUEST);
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionClick = 0;

                if(seleletedPhoto.equals(null)|| seleletedPhoto.equals("") || seleletedPhoto.trim().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Select Image !",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
                        Log.d("log",String.valueOf("Check " + seleletedPhoto + " | " + String.valueOf(bitmap)));
                        if(bitmap == null){
                            Toast.makeText(getApplicationContext(),"Do not use this Picture",Toast.LENGTH_LONG).show();
                        }
                        else{
                            UploadImage();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.d("log","ERROR");
                    }

                }

            }
        });
        mBtnover90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionClick++;
                picasso.placeholder(R.drawable.person);
                switch (ActionClick){
                    case 1:
                        picasso.rotate(-90);
                        break;
                    case 2:
                        picasso.rotate(-180);
                        break;
                    case 3:
                        picasso.rotate(-270);
                        break;
                    case 4:
                        picasso.rotate(-360);
                        ActionClick = 0;
                        break;
                }

                picasso.resize(128, 128);
                picasso.error(R.drawable.person);
                picasso.into(mImage);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        editor_App_Gone.putString("Upload_Gone" , "0");
        editor_App_Gone.commit();
    }

    private void UploadImage() {
        //                Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
        Bitmap bitmap = ((BitmapDrawable)mImage.getDrawable()).getBitmap();
        Log.v("log",String.valueOf(bitmap));
        String encodedImage = ImageBase64.encode(bitmap);
        HashMap<String, String> PostData = new HashMap<String, String>();
        PostData.put("image",encodedImage);
        PostData.put("uid",Get_ID_IMAGE);
        PostResponseAsyncTask task = new PostResponseAsyncTask(UploadImageActivity.this, PostData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("upload_success")){
                    editor_uploadimg.putBoolean("img_reload",true);
                    editor_uploadimg.commit();
                    finish();
                    Toast.makeText(getApplication(),"Upload Success : ",Toast.LENGTH_SHORT).show();
                }else if(s.contains("upload_failed")){
                    Toast.makeText(getApplication(),"upload_Failed",Toast.LENGTH_SHORT).show();
                } else if(s.contains("image_not_in")){
                    Toast.makeText(getApplication(),"image_Not_in",Toast.LENGTH_SHORT).show();
                }

            }
        });
        task.execute("http://www.thaidate4u.com/service/json/android_php/upload_images.php");
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getApplication(),"Error while uploading 1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getApplication(),"Error while uploading 2",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getApplication(),"Error while uploading 3",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast.makeText(getApplication(),"Error while uploading 4",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Bitmap getRotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1 = Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
        return bitmap1;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = sp_uploadimg.getString("cameraphoto","null");
                seleletedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    mImage.setImageBitmap(getRotateBitmap(bitmap,90));
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
            else if(requestCode == GALLERY_REQUEST){
                editor_uploadimg.putString("galleryphoto",String.valueOf(data.getData()));
                editor_uploadimg.commit();
                Uri uri = Uri.parse(sp_uploadimg.getString("galleryphoto","null"));
                galleryphoto.setPhotoUri(uri);
                String photoPath = galleryphoto.getPath();
                seleletedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    mImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while choosing photos", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor_App_Gone.putString("Upload_Gone" , "1");
        editor_App_Gone.commit();

    }

}
