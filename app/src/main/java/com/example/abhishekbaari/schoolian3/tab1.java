package com.example.abhishekbaari.schoolian3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishekbaari.schoolian3.adapters.AdapterForComment;
import com.example.abhishekbaari.schoolian3.adapters.AdapterForTecher;
import com.example.abhishekbaari.schoolian3.model.CommentAnime;
import com.example.abhishekbaari.schoolian3.model.teacher_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tab1 extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private final String JSON_URL = "http://schoolian.website/android/subjects.php" ;
    private JsonArrayRequest request ;


    private List<teacher_list> lstAnime ;
    private RecyclerView recyclerView ;
      SessionManger sessionManger;
    String sclid,clls;
    View view;
   // Context


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        sessionManger=new SessionManger(getActivity());
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esid = user.get(sessionManger.SCL_ID);
        String cls = user.get(sessionManger.CLAS);
        sclid=Esid;
        clls=cls;
        shocoments(sclid,clls);
        view=inflater.inflate(R.layout.tab1,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewTeacher);
        lstAnime = new ArrayList<>();

        return view;

    }

    private void shocoments(final String posId,final String cls) {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("teacher");

                    if(success.equals("1"))
                    {
                        for(int i=0 ; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            teacher_list anime = new teacher_list() ;
                            anime.setTeacherName(jsonObject.getString("teacher_name"));
                            anime.setSubject(jsonObject.getString("subject"));
                            //anime.setTeacherId(jsonObject.getString("com_id"));
                            anime.setTimOf(jsonObject.getString("subject_time"));
                            anime.setImage_url_teacher(jsonObject.getString("profile_pic"));
                           // lstAnime = new ArrayList<teacher_list>();
                            lstAnime.add(anime);
                        }
                        setuprecyclerview(lstAnime);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                   Toast.makeText(getActivity(),"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",posId);
                param.put("cls",cls);

                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void setuprecyclerview(List<teacher_list> userPostsList) {


        AdapterForTecher adaptorRecycler = new AdapterForTecher(getActivity(),userPostsList) ;
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adaptorRecycler);

    }


}
