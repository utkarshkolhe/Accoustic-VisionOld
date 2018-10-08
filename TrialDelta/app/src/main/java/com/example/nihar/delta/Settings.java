package com.example.nihar.delta;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    protected int Page_ID = 4;
    protected Switch autospeak,speechinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GlobalVariables.current_page=Page_ID;
        autospeak = findViewById(R.id.AutoSpeak);
        speechinput = findViewById(R.id.SpeechInput);
        autospeak.setChecked(GlobalVariables.user.boolSpeakPeriodic);
        speechinput.setChecked(GlobalVariables.user.boolSpeechInput);
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

    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }
    public void saveChanges(View view){
        GlobalVariables.user.boolSpeakPeriodic = autospeak.isChecked();
        GlobalVariables.user.boolSpeechInput = speechinput.isChecked();

    }
}
