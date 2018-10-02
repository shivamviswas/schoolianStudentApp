package com.example.abhishekbaari.schoolian3;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Spash_Screen extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
        SessionManger sessionManger;
       ProgressBar progressBar;
       ImageView retry,logopic,sclfont;
       TextView tapto, pst;
    AlertDialog.Builder builder;
    private final String uplod="http://schoolian.website/android/getAppUpdate.php";
    private final String URL_PRODUCTS = "http://schoolian.website/android/getPostData.php";
    private final String URL = "http://schoolian.website/android/login.php";
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash__screen);
       // retry = findViewById(R.id.retry);
       // tapto = findViewById(R.id.tapto);
        logopic = findViewById(R.id.logo);
        sclfont = findViewById(R.id.sclFont);
        pst = findViewById(R.id.slogo);
     //   tapto = findViewById(R.id.tapto);
     //  ImageView imageView = findViewById(R.id.logo);
        sessionManger = new SessionManger(this);
        progressBar = findViewById(R.id.progressBar);

        logopic.animate().translationYBy(-200f).setDuration(1500);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                sclfont.animate().alpha(1f).setDuration(2500);
                pst.animate().alpha(1f).setDuration(2500);


            }

        }, 3*1000);


      //  Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up);
     //   imageView.startAnimation(animation);
//        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down);
//        pst.startAnimation(animation1);
     //  logopic.animate().alpha(1f);
        updateApp();

//        int Permission_All = 1;
//
//        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.MEDIA_CONTENT_CONTROL,
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_NETWORK_STATE};
//        if(!hasPermissions(this, Permissions)){
//            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
//        }

        /*

        if (haveNetworkConnection() == true) {

            progressBar.setVisibility(View.VISIBLE);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);


                        if (!sessionManger.isLoging()) {
                            Intent intent = new Intent(Spash_Screen.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Intent intent = new Intent(Spash_Screen.this, HomeMenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();

        } else {

            retry.setVisibility(View.VISIBLE);
            tapto.setVisibility(View.VISIBLE);
            logopic.setVisibility(View.GONE);
            pst.setVisibility(View.GONE);
        }


///////////////////////////////////////////////////////////////////////////////////////////
    <uses-permission android:name="android.permission.READ_PROFILE" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission
        android:name="android.permission.MEDIA_CONTENT_CONTROL"
        tools:ignore="ProtectedPermissions" />



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
       checkAndroidVersion();

    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();

        } else {
          //  Toast.makeText(this, "Starting...", Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(Spash_Screen.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(Spash_Screen.this,
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Spash_Screen.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (Spash_Screen.this, Manifest.permission.CAMERA)) {

                Snackbar.make(Spash_Screen.this.findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.MEDIA_CONTENT_CONTROL,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            // write your logic code if permission already granted
           // Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraPermission && readExternalFile)
                    {
                        // write your logic here
                    } else {
                        Snackbar.make(Spash_Screen.this.findViewById(android.R.id.content),
                                "Please Grant Permissions to upload profile photo",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(
                                                    new String[]{Manifest.permission
                                                            .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                                    PERMISSIONS_MULTIPLE_REQUEST);
                                        }
                                    }
                                }).show();
                    }
                }
                break;
        }
    }







//    public static boolean hasPermissions(Context context, String... permissions){
//
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
//            for(String permission: permissions){
//                if(ActivityCompat.checkSelfPermission(context, permission)!=PackageManager.PERMISSION_GRANTED){
//                    return  false;
//                }
//            }
//        }
//        return true;
//    }



    @Override
    protected void onPause() {
        super.onPause();
       finish();

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public void retry(View view) {
/*
        retry.setVisibility(View.GONE);
        tapto.setVisibility(View.GONE);
        logopic.setVisibility(View.VISIBLE);
        pst.setVisibility(View.VISIBLE);


        if (haveNetworkConnection()) {

            progressBar.setVisibility(View.VISIBLE);
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);


                        if (!sessionManger.isLoging()) {
                            Intent intent = new Intent(Spash_Screen.this, Login.class);
                            startActivity(intent);
                        } else {

                            Intent intent = new Intent(Spash_Screen.this, HomeMenuActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();

        }
        else {

            retry.setVisibility(View.VISIBLE);
            tapto.setVisibility(View.VISIBLE);
            logopic.setVisibility(View.GONE);
            pst.setVisibility(View.GONE);
        }

    }*/
    }

    public void updateApp(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                          try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                              JSONArray jsonArray = jsonObject.getJSONArray("update");
                              progressBar.setVisibility(View.GONE);

                            //  Toast.makeText(Spash_Screen.this, "Success!"+response, Toast.LENGTH_SHORT).show();
                              int code=0;
                              String link="",msg="";
                              if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    code = object.getInt("version_code");
                                    link = object.getString("updatelink").trim();
                                    msg = object.getString("msg").trim();
                                }

                                Checkupdate(code,link,msg);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(Spash_Screen.this, "Try Again!"+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Spash_Screen.this, "Try Again!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("getupdate","1");
                params.put("app","student");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void Checkupdate(int cod, final String link,final String msg) {
        if(versionCode<cod)
    { progressBar.setVisibility(View.GONE);
        builder = new AlertDialog.Builder(Spash_Screen.this);

        builder.setTitle("Update!");


        builder.setMessage(msg);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Spash_Screen.this, "Updating...", Toast.LENGTH_SHORT).show();
                String url = "http://"+link;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        builder.setCancelable(true);
       final AlertDialog alertDialog= builder.create();
        builder.show();
        Spash_Screen.this.setFinishOnTouchOutside(true);


    }
    else {

        progressBar.setVisibility(View.VISIBLE);
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(5000);
//
//
//                    if (!sessionManger.isLoging()) {
//                        String s=SessionManger.SCL_ID;
//                        demofeed(s);
//                        Intent intent = new Intent(Spash_Screen.this, Login.class);
//                        startActivity(intent);
//
//                    } else {
//
//                        Intent intent = new Intent(Spash_Screen.this, HomeMenuActivity.class);
//                        startActivity(intent);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        thread.start();

            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                   // Toast.makeText(Spash_Screen.this, "work", Toast.LENGTH_SHORT).show();
                    if (!sessionManger.isLoging()) {
                        String s=SessionManger.SCL_ID;

                        demofeed(s);
                       // Toast.makeText(Spash_Screen.this, "work2", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Spash_Screen.this, Login.class);
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(Spash_Screen.this, HomeMenuActivity.class);
                        startActivity(intent);
                    }

                    // close this activity

                    finish();

                }

            }, 5*1000);

        }

    }

    private void demofeed(final String id) {


        //showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //stopProgressBar();
                SessionManger.putString(Spash_Screen.this, SessionManger.HOME_FEED_KEY, response);



            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(HomeMenuActivity.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id", id);

                return param;

            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

}

