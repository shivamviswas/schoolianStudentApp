package com.example.abhishekbaari.schoolian3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] items = new String[]{"Select Class","10","11", "12"};
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    List<String> clsases;
    ArrayAdapter<String> dataAdapter;
    String selectedClassId=null,sclid=null;
    Spinner spinner;
    private final String JSON_URL = "http://schoolian.website/android/getClasses.php";
    boolean isSetInitialText=true;
    private EditText school_id,name,lastname,email, pass,phone,clas;
    private ProgressBar progressBar;
    private Button submit;
    Character ch;
    Integer cha;
      // private String Url="http://192.168.43.188/android/reg2.php",getClasss;
       private String Url="http://schoolian.website/android/reg2.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_registration );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setTitle("Register");
        }

        school_id=findViewById(R.id.school_id);
        name = findViewById(R.id.sname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.semail);
        phone=findViewById(R.id.snumber);
        pass=findViewById(R.id.spassword);
        progressBar=findViewById(R.id.prog);
        submit=findViewById(R.id.submit);
        spinner = findViewById(R.id.simpleSpinner);
        spinner.setOnItemSelectedListener( this );
        clsases=new ArrayList<>();
        clsases.add("Select Class");

        school_id.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             // sclid=school_id.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isSetInitialText){
                    isSetInitialText = false;
                } else{
                    // perform your operation
                    sclid=school_id.getText().toString();
                    if(sclid.length()==8)
                    {
                        Toast.makeText(StudentRegistrationActivity.this, sclid, Toast.LENGTH_SHORT).show();
                        getClasses(sclid);
                    }

                }

            }
        });

    }

    public void submit(View view) {
        final String name=this.name.getText().toString();
        final String school_id=this.school_id.getText().toString();
        final String lastname=this.lastname.getText().toString();
        final String phone=this.phone.getText().toString();

        final String email=this.email.getText().toString();
        final String pass=this.pass.getText().toString();


        if(!email.isEmpty()&&!pass.isEmpty()&&!selectedClassId.equals("Select Class")&&selectedClassId!=null
                &&!lastname.isEmpty()&&!lastname.isEmpty()&&!school_id.isEmpty()){
            int charAt = phone.charAt(0);
            if(email.matches(emailPattern)) {
                // Toast.makeText(StudentRegistrationActivity.this,"ehhe",Toast.LENGTH_LONG).show();
                if((isValidPhoneNumber(phone))) {
                    submitMethod(name, school_id, lastname, phone, email, pass, selectedClassId);
                }
                else {
                    this.phone.setError("Plaese Fill All fields");

                }

            }

            else {
                this.email.setError("Plaese Fill All fields");
            }

        }else{
            this.name.setError("Plaese Fill All fields");
            this.school_id.setError("Plaese Fill All fields");
            this.lastname.setError("Plaese Fill All fields");
            this.phone.setError("Plaese Fill All fields");
            this.email.setError("Plaese Fill All fields");
            // this.clas.setError("Plaese Fill All fields");
            this.pass.setError("Plaese Fill All fields");

            this.email.setError("Plaese Fill valid email");

        }


    }

    private void submitMethod(final String name, final String school_id, final String lastname, final String phone, final String email, final String pass, final String clas) {

        progressBar.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1"))
                    {
                        Toast.makeText(StudentRegistrationActivity.this, "Email Already Exist",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    }
                    else if (success.equals("2"))
                    {
                        Toast.makeText(StudentRegistrationActivity.this, "Register Successfully",Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        Intent intent =new Intent(StudentRegistrationActivity.this,HomeMenuActivity.class);
                        startActivity(intent);
                    }
                    else if (success.equals("4"))
                    {
                        Toast.makeText(StudentRegistrationActivity.this, "Invalid School Id! Please Enter Valid Id",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(StudentRegistrationActivity.this, "Register Error"+e.toString(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(StudentRegistrationActivity.this, "Register Error 2:"+error.toString(),Toast.LENGTH_LONG).show();

                        progressBar.setVisibility(View.GONE);
                        submit.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> pram=new HashMap<>();
                pram.put("name",name);
                pram.put("email",email);
                pram.put("password",pass);
                pram.put("phone",phone);
                pram.put("school_id",school_id);
                pram.put("class",clas);
                pram.put("lastname",lastname);
                return pram;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }






    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent( this, Login.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( this,Login.class);
        startActivity( intent );
    }


    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        if(items[position].length()!=0){

            //Toast.makeText(getApplicationContext(), items[position], Toast.LENGTH_LONG).show();
           // getPosition(items[position]);
            selectedClassId = parent.getItemAtPosition(position).toString();

            }


    }




    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    private void getClasses(final String sclid) {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject1 = new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray = jsonObject1.getJSONArray("className");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String classes = jsonObject.getString("className");

                            clsases.add(classes);


                        }
                        setAdapterMethod(clsases);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(StudentRegistrationActivity.this, "Error 1: " + e.toString(), Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentRegistrationActivity.this, "Error 2: " + error.toString(), Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id", sclid);

                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(StudentRegistrationActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setAdapterMethod(List<String> clsases) {

        dataAdapter = new ArrayAdapter<String>(StudentRegistrationActivity.this, android.R.layout.simple_spinner_item, clsases);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }
}

