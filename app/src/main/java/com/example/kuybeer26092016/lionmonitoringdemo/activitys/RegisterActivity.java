package com.example.kuybeer26092016.lionmonitoringdemo.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kuybeer26092016.lionmonitoringdemo.R;
import com.example.kuybeer26092016.lionmonitoringdemo.manager.ManagerRetrofit;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_menu;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_monitoringitem;
import com.example.kuybeer26092016.lionmonitoringdemo.models.Mis_register;
import com.example.kuybeer26092016.lionmonitoringdemo.service.Service;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.EachExceptionsHandler;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ImageView image;
    private Spinner spDivision;
    private Button btn_register;
    private ManagerRetrofit mManager;
    private Snackbar snackbar;
    private LinearLayout mLinearLayout;
    private ProgressBar progressBar;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private String seleletedPhoto;
    private  List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mManager = new ManagerRetrofit();
        overridePendingTransition(R.anim.popup_slie_in_bottom,R.anim.popup_slie_in_bottom);
        //*********** Set XML ************************************//
        progressBar = (ProgressBar)findViewById(R.id.Pgregister);
        progressBar.setVisibility(View.GONE);
        mLinearLayout = (LinearLayout)findViewById(R.id.activity_register);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPassword_again = (EditText) findViewById(R.id.edPasswordAgain);
        btn_register = (Button)findViewById(R.id.btnregister);
        spDivision = (Spinner)findViewById(R.id.spinner);
        image = (ImageView)findViewById(R.id.image);
        SetSwitch();




        // Spinner item selection Listener  

        //*********** Set XML ************************************//

        //************ Set Class Cemera & ImageCapPic *****************/
        cameraPhoto = new CameraPhoto(getApplicationContext());
        ((ImageView)findViewById(R.id.XML_imagecappic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                }
            }
        });
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        ((ImageView)findViewById(R.id.XML_Gallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);
            }
        });
        //************ Set Class Cemera & ImageCapPic *****************/

    }

    private void SetSwitch(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.thaidate4u.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Mis_monitoringitem register = new Mis_monitoringitem();
        Call<List<Mis_menu>> call = service.CallbackMenu();
        list = new ArrayList<String>();

        call.enqueue(new Callback<List<Mis_menu>>() {
            @Override
            public void onResponse(Call<List<Mis_menu>> call, Response<List<Mis_menu>> response) {
                if(response.isSuccessful()){
                    final List<Mis_menu> Listitem = response.body();
                    try{
                        for (int i = 0 ; i< Listitem.size() ; i ++){
                            list.add(Listitem.get(i).getMc_div());
                            Log.d("C","PASS");
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                (getApplicationContext(), R.layout.spinner_item,list);
                        dataAdapter.setDropDownViewResource
                                (R.layout.spinner_item);
                        spDivision.setAdapter(dataAdapter);

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
                if(username.length()>5 && password.length()>5){
                    if( password.equals(password_again)){
                        if( division != "Select Division"){
                            if( null!=image.getDrawable()){
                                CheckUsername(username,password,division);
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            else{
                                Snackbar("Register unsuccessful Please capture image !");
                            }
                        }else{
                            Snackbar("Register unsuccessful Please select Division !");
                        }
                    }else{
                        Snackbar("Register unsuccessful Because Password does not matc !");
                    }
                }else{
                    Snackbar("Register unsuccessful Because Character username & password less 5 !");
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
                    List<Mis_register> ListItem = response.body();
                    for(int i = 0 ; i<ListItem.size();i++){
                        Snackbar("Username has already");
                        progressBar.setVisibility(View.GONE);
                    }
                    if(ListItem.size() == 0){
                        Snackbar("Register successful");
                        edPassword.setText("");
                        edUsername.setText("");
                        edPassword_again.setText("");
                        UploadImage();
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

       call.enqueue(new Callback<List<Mis_register>>() {
           @Override
           public void onResponse(Call<List<Mis_register>> call, Response<List<Mis_register>> response) {
               if(response.isSuccessful()){
                   List<Mis_register> List = response.body();
                   for(int i = 0 ; i < List.size();i++) {
                       CheckUsername = List.get(i).getUsername();
                   }
               }else {
               }
           }

           @Override
           public void onFailure(Call<List<Mis_register>> call, Throwable t) {

           }
       });
    }
    private void UploadImage(){
        try{
            Bitmap bitmap = ImageLoader.init().from(seleletedPhoto).requestSize(128,128).getBitmap();
            String encodedImage = ImageBase64.encode(bitmap);
            HashMap<String, String> PostData = new HashMap<String, String>();
            PostData.put("image",encodedImage);
            PostData.put("uid",username);
            PostResponseAsyncTask task = new PostResponseAsyncTask(RegisterActivity.this, PostData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if(s.contains("upload_success")){
                        Toast.makeText(getApplication(),"Upload Success",Toast.LENGTH_SHORT).show();
                    }else if(s.contains("upload_failed")){
                        Toast.makeText(getApplication(),"upload_failed",Toast.LENGTH_SHORT).show();
                    } else if(s.contains("image_not_in")){
                        Toast.makeText(getApplication(),"image_not_in",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            task.execute("http://www.thaidate4u.com/service/json/peerapong/upload_images.php");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                seleletedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    Bitmap bitmapRe = Bitmap.createScaledBitmap(bitmap,128,128,true);
                    image.setImageBitmap(getRotateBitmap(bitmapRe,90));

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while loading photos", Toast.LENGTH_SHORT).show();
                }

            }
            else if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                seleletedPhoto = photoPath;
//                UploadImage ();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(128, 128).getBitmap();
                    Bitmap bitmapRe = Bitmap.createScaledBitmap(bitmap,128,128,true);
                    image.setImageBitmap(bitmapRe);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
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
    private void Snackbar(String messages){
        snackbar = Snackbar.make(mLinearLayout,messages,Snackbar.LENGTH_LONG);
        snackbar.show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.popup_silde_exit_bottom,R.anim.anim_no);
    }
}
