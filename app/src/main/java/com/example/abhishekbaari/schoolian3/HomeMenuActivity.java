package com.example.abhishekbaari.schoolian3;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishekbaari.schoolian3.adapters.RecyclerViewAdapter;
import com.example.abhishekbaari.schoolian3.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class HomeMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private final String JSON_URL = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json" ;


    //  public  adaptorRecycler;
    boolean doubleBackToExitPressedOnce = false;
    // LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    //private JsonArrayRequest request;

    private List<Anime> lstAnime;

    private String school_id;
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerViewAdapter adaptorRecycler;

    BottomNavigationView bottomNavigationView;
    SessionManger sessionManger;
    // private final String URL_PRODUCTS = "http://schooli.000webhostapp.com/android/getPostData.php";
    private final String URL_PRODUCTS = "http://schoolian.website/android/getPostData.php";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManger = new SessionManger(this);
        sessionManger.checkLogin();

        changImagetest();
//        linearLayout = findViewById(R.id.top);
//        linearLayout.setEnabled(false);


        swipeRefresh = findViewById(R.id.swipe_refresh);

        final SwipeRefreshLayout.OnRefreshListener swpie = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lstAnime.clear();
                demofeed(school_id);
            }
        };
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //if internet is available
                lstAnime.clear();
                demofeed(school_id);
                //else
                //swipe_refresh.setRefreshing(false);
            }
        });
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
              swipeRefresh.setRefreshing(true);
               swpie.onRefresh();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view=navigationView.getHeaderView(0);
        ImageView img=view.findViewById(R.id.imageViewScl);
        TextView txt=view.findViewById(R.id.scl_head);
        img.setImageResource(R.drawable.nav_menu_header_bg);
        txt.setText("Schoolian");






//        postBtn=findViewById(R.id.postbtn);
//        editPost=findViewById(R.id.stpost);
//        postBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HomeMenuActivity.this, "work", Toast.LENGTH_SHORT).show();
//            }
//        });

        //for feed part or adaptor part or recycle par
        setUpBottomNavigationView();
        HashMap<String, String> user = sessionManger.getUserDetail();
        String Ename = user.get(sessionManger.SCL_ID);
        school_id = Ename;

///////////////////////////////////////////////////////////getting the recyclerview from xml//////////////////////////


//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        recyclerView = (RecyclerView) findViewById(R.id.recycleerview);
        lstAnime = new ArrayList<>();

        //   requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data


        //Adding an scroll change listener to recyclerview


        //initializing our adapter

        //Adding adapter to recyclerview
        // recyclerView.setAdapter(adapter);

        //if internet is available
        // demofeed(school_id);
        final String response2 = SessionManger.getString(HomeMenuActivity.this, SessionManger.HOME_FEED_KEY);

        try {
            parseHomeFeed(response2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // loadProducts();
        // jsonrequest();
    }

    private void parseHomeFeed(String response) throws JSONException {

        // stopProgressBar();

        JSONObject jsonObject1 = new JSONObject(response);
        String success = jsonObject1.getString("success");
        JSONArray jsonArray = jsonObject1.getJSONArray("userPost");

        if (success.equals("1")) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Anime anime = new Anime();
                anime.setName(jsonObject.getString("st_name"));
//                        anime.setDescription(jsonObject.getString("description"));
                anime.setRating(jsonObject.getString("stars"));
                anime.setCategorie(jsonObject.getString("posts"));
                anime.setId(jsonObject.getString("post_id"));
                anime.setStudio(jsonObject.getString("profile_pic"));
                anime.setSId(jsonObject.getString("sid"));
                anime.setTime(jsonObject.getString("time"));
                anime.setImage_url(jsonObject.getString("post_pic"));
                lstAnime.add(anime);
            }
            setuprecyclerview(lstAnime);
        }
        else {
            Toast.makeText(this, "No one posted", Toast.LENGTH_SHORT).show();
        }

    }

    private void demofeed(final String id) {


        //showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //stopProgressBar();
                    SessionManger.putString(HomeMenuActivity.this, SessionManger.HOME_FEED_KEY, response);

                    final String response2 = SessionManger.getString(HomeMenuActivity.this, SessionManger.HOME_FEED_KEY);

                    parseHomeFeed(response2);

                    //save home response to shared preference


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeMenuActivity.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeMenuActivity.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
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

    private void setuprecyclerview(List<Anime> userPostsList) {

        if (adaptorRecycler == null) {
            adaptorRecycler = new RecyclerViewAdapter(this, userPostsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adaptorRecycler);
        } else {
            adaptorRecycler.changeDataSet(userPostsList);
            adaptorRecycler.notifyDataSetChanged();
        }

        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }


    private void showProgress() {

        progressDialog = new ProgressDialog(HomeMenuActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void stopProgressBar() {

        if (progressDialog != null)
            progressDialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


//    @Override
  /*  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HomeMenuActivity.this, NotificationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_camera2:
                Intent intent = new Intent(HomeMenuActivity.this, MyPost_NevActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_camera:
                Intent intent1 = new Intent(HomeMenuActivity.this, Schoolina_NevActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_gallery:
                Intent intent2 = new Intent(HomeMenuActivity.this, Message_NevActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_manage:
                Intent intent3 = new Intent(HomeMenuActivity.this, ContactUs_NevActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_slideshow:
                Intent intent4 = new Intent(HomeMenuActivity.this, Feedback_NevActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_send:
                Intent intent5 = new Intent(HomeMenuActivity.this, Help_NevActivity.class);
                startActivity(intent5);

                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // method for bottom navigation view
    private void setUpBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        BottomNavigationViewHelper.enableNavigation(HomeMenuActivity.this, bottomNavigationView);

    }


    public void feedPost(View view) {

        Intent intent = new Intent(HomeMenuActivity.this, feedUpload.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    void changImagetest(){
        LayoutInflater li=LayoutInflater.from(HomeMenuActivity.this);
        View layout = li.inflate(R.layout.nav_header_home_menu,null,false);
        ImageView imageView=layout.findViewById(R.id.imageViewScl);
        imageView.setImageResource(R.drawable.man);
    }
}
