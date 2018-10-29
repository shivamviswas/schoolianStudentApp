package com.wikav.student.studentapp.MainActivties;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wikav.student.studentapp.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification );
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent( NotificationActivity.this,HomeMenuActivity.class );
        startActivity( intent );
    }
}
