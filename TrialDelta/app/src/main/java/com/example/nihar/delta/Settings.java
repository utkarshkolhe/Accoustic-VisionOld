package com.example.nihar.delta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    protected int Page_ID = 4;
    protected Switch autospeak,speechinput,detectLabels,detectExpressions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        GlobalVariables.current_page=Page_ID;
        TTS.Initalize(this);
        autospeak = findViewById(R.id.AutoSpeak);
        speechinput = findViewById(R.id.SpeechInput);
        detectLabels = findViewById(R.id.LabelDetection);
        detectExpressions= findViewById(R.id.ExpressionDetection);


        autospeak.setChecked(GlobalVariables.user.boolSpeakPeriodic);
        speechinput.setChecked(GlobalVariables.user.boolSpeechInput);
        detectLabels.setChecked(GlobalVariables.user.boolDetectLabels);
        detectExpressions.setChecked(GlobalVariables.user.boolDetectExpressions);

    }
    public void onLogOut(View view){
        SharedPreferences sharedPref;
        sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor=sharedPref.edit();

        // Save your string in SharedPref
        editor.putString("user_id", "");
        editor.apply();
        Intent intent = new Intent(Settings.this,
                LoadScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }
    public void saveChanges(View view){
        GlobalVariables.user.boolSpeakPeriodic = autospeak.isChecked();
        GlobalVariables.user.boolSpeechInput = speechinput.isChecked();
        GlobalVariables.user.boolDetectLabels = detectLabels.isChecked();
        GlobalVariables.user.boolDetectExpressions=detectExpressions.isChecked();
        recreate();

    }
}
