package com.example.abhishekbaari.schoolian3.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.abhishekbaari.schoolian3.HomeMenuActivity;
import com.example.abhishekbaari.schoolian3.R;
import com.example.abhishekbaari.schoolian3.SessionManger;
import com.example.abhishekbaari.schoolian3.activities.AnimeActivity;
import com.example.abhishekbaari.schoolian3.feedUpload;
import com.example.abhishekbaari.schoolian3.model.Anime;
import com.example.abhishekbaari.schoolian3.model.MyStar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aws on 11/03/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Anime> mData ;
    RequestOptions option;
    private final String URL_PRODUCTS = "http://schoolian.website/android/getStar.php";
    //String URL_PRODUCTS="http://schooli.000webhostapp.com/android/getStar.php" http://schoolian.website/android/getPostData.php;
    int status=1,star=1,getStatus;
    String postId,Sid,valueOfstar;
    private List<Anime> lstAnime ;
    MyViewHolder viewHolder;
    SessionManger sessionManger;
    MyStar anime5 =new MyStar();


    public RecyclerViewAdapter(Context mContext, List<Anime> mData) {
        this.mContext = mContext;
        this.mData = mData;


        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    public void changeDataSet(List<Anime> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        sessionManger=new SessionManger(mContext);
        HashMap<String, String> user = sessionManger.getUserDetail();
        String Ename = user.get(sessionManger.SID);
        Sid = Ename;
        View view ;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.test,parent,false) ;
       viewHolder = new MyViewHolder(view) ;

//        if(getStatus==1)
//        {
//            viewHolder.star.setImageResource(android.R.drawable.btn_star_big_on);
//        }
//        else
//        {
//            viewHolder.star.setImageResource(android.R.drawable.btn_star_big_off);
//        }

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, AnimeActivity.class);
                i.putExtra("name",mData.get(viewHolder.getAdapterPosition()).getName());
               // i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("proPic",mData.get(viewHolder.getAdapterPosition()).getStudio());
                i.putExtra("posts",mData.get(viewHolder.getAdapterPosition()).getCategorie());
               i.putExtra("posId",mData.get(viewHolder.getAdapterPosition()).getIdd());
                i.putExtra("star",mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("sid",mData.get(viewHolder.getAdapterPosition()).getSId());
                i.putExtra("postPic",mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);

            }
        });




        return viewHolder;



    }




    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_name.setText(mData.get(position).getName());
       holder.tv_rating.setText(mData.get(position).getRating());
      //  holder.tv_studio.setText(mData.get(position).getStudio());
      holder.tv_category.setText(mData.get(position).getCategorie());
      holder.tv_time.setText(mData.get(position).getTime());

        // Load Image from the internet and set it into Imageview using Glide

        valueOfstar=String.valueOf(mData.get(position).getRating());

       holder.star.setImageResource(android.R.drawable.btn_star_big_on);

    Glide.with(mContext).load(mData.get(position).getImage_url()).into(holder.img_thumbnail);
        Glide.with(mContext).load(mData.get(position).getStudio()).into(holder.tv_pro1);

        holder.getStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int get=anime5.getImageStar();
            if(get!=1){

             //  SessionManger.putString(mContext,SessionManger.STAR,"0");
                anime5.setImageStar(1);
                int tempstar=Integer.parseInt(mData.get(position).getRating());
                tempstar=tempstar+1;
               anime5.setSetStar(tempstar);
                mData.get(position).setRating(String.valueOf(anime5.getSetStar()));
                setStars(mData.get(position).getIdd(),anime5.getSetStar(),status,Sid);




                }
                else {
             //   Toast.makeText(mContext, "work + "+get, Toast.LENGTH_SHORT).show();
                anime5.setImageStar(0);
               // SessionManger.putString(mContext,SessionManger.STAR,"1");

               int tempstar= Integer.parseInt(mData.get(position).getRating());
               tempstar=tempstar-1;
               anime5.setSetStar(tempstar);
               mData.get(position).setRating(String.valueOf(anime5.getSetStar()));

                setStars(mData.get(position).getIdd(),anime5.getSetStar(),status,Sid);

                }
            }
        });



    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ;
        TextView tv_rating ;
        TextView tv_time ;
        TextView tv_category;
        ImageView img_thumbnail,star,staron;
        CircleImageView tv_pro1;
        LinearLayout view_container;
        LinearLayout getStar;





        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            getStar=itemView.findViewById(R.id.setStar5);
            tv_name = itemView.findViewById(R.id.anime_name);
            tv_time = itemView.findViewById(R.id.timestamp);
           tv_category = itemView.findViewById(R.id.postss);
            tv_rating = itemView.findViewById(R.id.starGetss);
            tv_pro1 = itemView.findViewById(R.id.profile_pic);
            star=itemView.findViewById(R.id.starIMG2);
           // staron=itemView.findViewById(R.id.starIMG25);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

    private void setStars(final String id, final int star, final int status,final String sid) {


        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                  //  JSONArray jsonArray= jsonObject1.getJSONArray("allStars");

                   if(success.equals("1")) {
////                        for (int i = 0; i < jsonArray.length(); i++) {
////                            JSONObject jsonObject = jsonArray.getJSONObject(i);
////                            Anime anime = new Anime();
////                            anime.setRating(jsonObject.getString("stars"));
////                            getStatus = Integer.parseInt(jsonObject.getString("status"));
                           Toast.makeText(mContext,": "+star,Toast.LENGTH_LONG).show();
////
////                        }
                 }


//                    if(success.equals("1")) {
//
//                        Anime anime = new Anime() ;
//                        anime.setRating(jsonObject1.getString("starsss"));
//                        String s=jsonObject1.getString("status");
//                        Toast.makeText(mContext,": "+s,Toast.LENGTH_LONG).show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   Toast.makeText(mContext,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      Toast.makeText(mContext,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
             param.put("post_id",id);
                param.put("stars", ""+star);
                param.put("status", ""+status);
                param.put("sid", sid);
                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }


}