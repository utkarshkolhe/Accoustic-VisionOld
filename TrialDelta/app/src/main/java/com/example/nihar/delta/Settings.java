package com.example.nihar.delta;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {
    protected int Page_ID = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GlobalVariables.current_page=Page_ID;
    }
    public void onLogOut(View view){
        SharedPreferences sharedPref;
        sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor=sharedPref.edit();

        // Save your string in SharedPref
        editor.putString("user_id", "");
        editor.apply();
    }
}
