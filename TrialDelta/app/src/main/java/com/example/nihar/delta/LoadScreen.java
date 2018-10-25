package com.example.nihar.delta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadScreen extends AppCompatActivity {
    protected Intent inte;
    protected int Page_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_sceen);
        GlobalVariables.current_page=Page_ID;
        TTS.Initalize(getApplicationContext());
        SharedPreferences sharedPref;
        sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String userId=sharedPref.getString("user_id","");
        GlobalVariables.user = new User();
        if(userId.equals("")){
            inte = new Intent(LoadScreen.this,Walkthrough.class);
            GlobalVariables.login_status=false;
            GlobalVariables.username="";
        }
        else{
            inte = new Intent(LoadScreen.this,MainMenu.class);
            GlobalVariables.login_status=true;
            GlobalVariables.username=userId;
            DatabaseReference ref = FirebaseDatabase.getInstance().getInstance().getReference().child("Data");
            ref.child(GlobalVariables.username).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        if (snapshot.getValue() != null) {
                            try {
                                GlobalVariables.user = snapshot.getValue(User.class); // your name values you will get here
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("TAG", " it's null.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                startActivity(inte);
                finish();
            }
        }, 2000);
        //
    }
    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
