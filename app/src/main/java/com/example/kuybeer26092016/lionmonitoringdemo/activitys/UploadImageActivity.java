package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class UploadImageActivity extends AppCompatActivity {
    private String Get_URL_IMAGE,Get_ID_IMAGE;
    private Button mBtnCapture,mBtnGallery,mBtnUpload;
    private ImageView mImage;
    private Context mContext;
    private CameraPhoto cameraphoto;
    private GalleryPhoto galleryphoto;
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private String seleletedPhoto = "";
    private SharedPreferences sp_uploadimg,spApp_Gone;
    private SharedPreferences.Editor editor_uploadimg,editor_App_Gone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        mContext = this;
        spApp_Gone = getSharedPreferences("App_Gone", Context.MODE_PRIVATE);
        editor_App_Gone  = spApp_Gone.edit();
        sp_uploadimg = getSharedPreferences("img",Context.MODE_PRIVATE);
        editor_uploadimg = sp_uploadimg.edit();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.7),(int)(height*.6));
        cameraphoto = new CameraPhoto(this);
        galleryphoto = new GalleryPhoto(this);

        /***************** GET INTENT *************************/
        Get_URL_IMAGE = getIntent().getExtras().getString("I_URL_IMAGE");
        Get_ID_IMAGE = getIntent().getExtras().getString("ID_IMAGE");
        Log.d("TAG",Get_ID_IMAGE);
        /***************** GET INTENT *************************/

        /***************** SET XML & INSERT VALUE INTENT  *************************/
        mBtnUpload = (Button)findViewById(R.id.XML_btnupload);
        mBtnGallery = (Button)findViewById(R.id.XML_btnGallery);
        mBtnCapture = (Button)findViewById(R.id.XML_btnCapture);
        mImage = (ImageView)findViewById(R.id.XML_IMAGE);
        Picasso.with(mContext).load(Get_URL_IMAGE)
                .placeholder(R.drawable.ic_me)
                .resize(120, 100)
                .error(R.drawable.ic_me)
                .into(mImage);
        /***************** SET XML & INSERT VALUE INTENT  *************************/

        /*********************** ON CLICK ********************************/

        mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editor_App_Gone.putString("Upload_Gone" , "0");
                    editor_App_Gone.commit();
                    startActivityForResult(cameraphoto.takePhotoIntent(),CAMERA_REQUEST);
                    cameraphoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryphoto.openGalleryIntent(),GALLERY_REQUEST);
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleletedPhoto.equals(null)|| seleletedPhoto.equals("") || seleletedPhoto.trim().length()<=0){
                    Toast.makeText(getApplicationContext(),"Please Select Image !",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
                        if(bitmap == null){
                            Toast.makeText(getApplicationContext(),"Do not use this Picture",Toast.LENGTH_LONG).show();
                        }
                        else{
                            UploadImage();
//                            editor_uploadimg.putBoolean("img_reload",true);
//                            editor_uploadimg.commit();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        /*********************** ON CLICK ********************************/

    }

    @Override
    protected void onStart() {
        super.onStart();
        editor_App_Gone.putString("Upload_Gone" , "0");
        editor_App_Gone.commit();
    }

    private void UploadImage() {
        try{
            Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
                String encodedImage = ImageBase64.encode(bitmap);
                HashMap<String, String> PostData = new HashMap<String, String>();
                PostData.put("image",encodedImage);
                PostData.put("uid",Get_ID_IMAGE);
                PostResponseAsyncTask task = new PostResponseAsyncTask(UploadImageActivity.this, PostData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {

                        Log.i("TAG2",s.toString());

                        if(s.contains("upload_success")){
                            Log.i("Tag","Upload Finish");
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
                task.execute("http://www.thaidate4u.com/service/json/peerapong/upload_images_mc.php");
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

        }catch (FileNotFoundException e){
            Toast.makeText(getApplication(),"Something worng while encoding photo",Toast.LENGTH_SHORT).show();
        }
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
                String photoPath = cameraphoto.getPhotoPath();
                seleletedPhoto = photoPath;
                Log.d("TAG" , seleletedPhoto);
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    mImage.setImageBitmap(getRotateBitmap(bitmap,0));

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
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
