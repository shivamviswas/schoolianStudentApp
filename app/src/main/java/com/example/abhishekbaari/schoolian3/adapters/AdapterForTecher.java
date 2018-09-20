package com.example.abhishekbaari.schoolian3.adapters;

/**
 * Created by wikav-pc on 7/4/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.abhishekbaari.schoolian3.R;
import com.example.abhishekbaari.schoolian3.model.CommentAnime;
import com.example.abhishekbaari.schoolian3.model.teacher_list;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Aws on 11/03/2018.
 */

public class AdapterForTecher extends RecyclerView.Adapter<AdapterForTecher.MyViewHolder> {

    private Context mContext ;
    private List<teacher_list> myData ;
    RequestOptions option;
    String ComId;


    public AdapterForTecher(Context mContext, List<teacher_list> mData) {
        this.mContext = mContext;
        this.myData = mData;


        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view ;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.teacherfeed,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;


        return viewHolder;
    }




    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(myData.get(position).getTeacherName());
        holder.tv_category.setText(myData.get(position).getTimeOf());
        holder.tv_time.setText(myData.get(position).getSubject());
        ComId=String.valueOf(myData.get(position).getTeacherId());
        Glide.with(mContext).load(myData.get(position).getImage_url_teacher()).into(holder.tv_pro);


    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ,tv_time;
        TextView tv_category;
        ImageView tv_pro;






        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.teacherName);
            tv_time = itemView.findViewById(R.id.Tsubject);
            tv_category = itemView.findViewById(R.id.subjectTime);
            tv_pro = itemView.findViewById(R.id.icon);


        }
    }


}

