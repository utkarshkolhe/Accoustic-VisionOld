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

import java.util.Map;

public class Login extends AppCompatActivity {
    protected int Page_ID = 1;
    protected Button login,register,skip;
    protected EditText username,pass;
    protected TextView console;
    protected  boolean corrctUN,correctPW;
    protected int loginStatus=-1;
    private Map<String,String> users;
    protected String userN,passS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GlobalVariables.current_page=Page_ID;
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnLinkToRegisterScreen);
        skip = findViewById(R.id.btnskiplogin);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        console = findViewById(R.id.console);

    }
    public void onRegisterClick(View view){
        Intent inte = new Intent(Login.this,Register.class);
        startActivity(inte);
    }
    public void onSkiplick(View view){
        Intent inte = new Intent(Login.this,MainMenu.class);
        startActivity(inte);
    }
    public void onloginClick(View view){
        userN = username.getText().toString();
        passS = pass.getText().toString();
        console.setVisibility(View.VISIBLE);
        if(userN.equals("") || passS.equals("")){
            console.setText("Fields Cannot be Empty");
            return;
        }

        console.setText("Logging in. Please Wait");
        //console.append("0");
        Toast.makeText(this,userN+passS,Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        users =(Map<String,String>) dataSnapshot.getValue();
                        //console.append("1");
                        loginStatus=checkLogin(users,userN,passS);
                        switch (loginStatus){
                            case -1:
                                console.setText("Login Failed");
                                break;
                            case 0:
                                console.setText("Username Not Found");
                                break;
                            case 2:
                                console.setText("Inncorect Password");
                                break;
                            case 1:
                                console.setText("Successful Login");
                                SharedPreferences sharedPref;
                                sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor;
                                editor=sharedPref.edit();

                                // Save your string in SharedPref
                                editor.putString("user_id", userN);
                                editor.apply();
                                Intent inte = new Intent(Login.this,MainMenu.class);
                                startActivity(inte);
                                finish();
                                break;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        console.setText("The read failed: " + databaseError.getCode());
                    }
                });



        //console.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }

    public int checkLogin(Map<String,String> users, String userN, String passS) {
        if(users==null){
            return -1;
        }
        //console.append(users.size()+"");
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, String> entry : users.entrySet()){
            //console.append(entry.getKey());
            if(userN.equals(entry.getKey())){
                if(passS.equals(entry.getValue())){
                    return 1;
                }
                else{
                    return 2;
                }
            }
        }
        return 0;


    }
    public void setUsername(String userText){
        username.setText(userText);
    }
    public void setPass(String userText){
        pass.setText(userText);
    }
    public void clickLogin(){
        login.callOnClick();
    }
    public void clickRegister(){
        register.callOnClick();
    }
    public void clickSkip(){
        skip.callOnClick();
    }
    public void callBack(){
        finish();
    }
}
