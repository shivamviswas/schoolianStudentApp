package com.example.abhishekbaari.schoolian3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class BottomNavigationViewHelper {
   private static int i=0,j=0,k=0;
    public static void enableNavigation(final Context context, BottomNavigationView view){
       view.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.home:


                       if(i==0) {
                           Intent intent1 = new Intent( context, HomeMenuActivity.class );
                           context.startActivity( intent1 );
                           i=1;
                           j=k=0;
                       }


                       break;
                   case R.id.subject:
                       if (j==0) {
                           Intent intent2 = new Intent( context, SubjectActivity_2.class );
                           context.startActivity( intent2 );
                           i=0;
                           j=1;
                           k=0;
                       }

                        break;
                   case R.id.profile:
                       if(k==0) {
                           Intent intent5 = new Intent( context, NewProfile.class );
                           context.startActivity( intent5 );
                           k=1;
                           i=0;
                           j=0;
                       }

                        break;


               }

               return false;
           }
       } );
    }
}
