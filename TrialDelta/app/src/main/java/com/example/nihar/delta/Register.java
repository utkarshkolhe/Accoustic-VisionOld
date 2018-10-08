package com.example.nihar.delta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    protected int Page_ID = 2;
    protected EditText pass,repass,username;
    protected Button register,skip;
    protected FirebaseDatabase database;
    protected  String userN,passS,repassS;
    protected TextView console;
    protected  boolean corrctUN,correctPW;
    private Map<String,String> users;
    protected DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        GlobalVariables.current_page=Page_ID;
        TTS.Initalize(this);
        username = findViewById(R.id.username);
        skip=findViewById(R.id.btnskiplogin);
        register=findViewById(R.id.btnLinkToRegisterScreen);
        pass = findViewById(R.id.password);
        console = findViewById(R.id.console);
        repass = findViewById(R.id.passwordre);
    }

    public void onRegisterClick(View view){

        userN = username.getText().toString();
        passS = pass.getText().toString();
        repassS = repass.getText().toString();
        console.setVisibility(View.VISIBLE);
        if(userN.equals("") || passS.equals("") || repassS.equals("")){
            console.setText("Fields Should not be empty.");
            return;
        }
        if(passS.equals(repassS)){
           correctPW=true;
        }
        else{
            correctPW=false;
        }
        console.setText("Please Wait. Registering User");
        ref = FirebaseDatabase.getInstance().getInstance().getReference().child("users");

        /*
        Map<String, String> users = new HashMap<>();
        users.put("alanisawesome", "wda");
        users.put("gracehop", "adwd");

        ref.setValue(users);
        */
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        users =(Map<String,String>) dataSnapshot.getValue();
                        corrctUN=checkusername(users,userN);
                        Intent inte;
                        if(corrctUN && correctPW){
                            users.put(userN, passS);
                            ref.setValue(users);
                            SharedPreferences sharedPref;
                            sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor;
                            editor=sharedPref.edit();

                            // Save your string in SharedPref
                            editor.putString("user_id", userN);
                            editor.apply();
                            GlobalVariables.login_status=true;
                            GlobalVariables.username=userN;
                            inte = new Intent(Register.this,MainMenu.class);
                            finish();
                            startActivity(inte);
                        }
                        else if(correctPW && !corrctUN){
                            console.setText("Username already Exists");
                        }
                        else if(corrctUN && !correctPW){
                            console.setText("Passwords don't match");
                        }
                        else{
                            console.setText("Username already Exists and Passwords Dont Match");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }
                });


    }
    private boolean checkusername(Map<String,String> users,String userN) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, String> entry : users.entrySet()){
            if(userN.equals(entry.getKey())){
                    return false;
                }
        }
        return true;


    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }

    public void onSkiplick(View view){
        Intent inte = new Intent(Register.this,MainMenu.class);
        startActivity(inte);
    }
    public void setUsername(String userText){
        username.setText(userText);
    }
    public void setPass(String userText){
        pass.setText(userText);
    }
    public void setrePass(String userText){
        repass.setText(userText);
    }
    public void clickRegister(){
        register.callOnClick();
    }
    public void callBack(){
        finish();
    }
    public void clickSkip(){
        skip.callOnClick();
    }
}
