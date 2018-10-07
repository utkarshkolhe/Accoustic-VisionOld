package com.example.nihar.delta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    protected int Page_ID = 3;
    private static final int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        GlobalVariables.current_page=Page_ID;
        SharedPreferences sharedPref;
        sharedPref= getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String userId=sharedPref.getString("user_id","");
        Toast.makeText(this,"Logged in as : "+userId,Toast.LENGTH_SHORT).show();
    }
    public void onTextRecognition(View view){
        Intent inte = new Intent(MainMenu.this,TextRecognition.class);
        startActivity(inte);
    }
    public void onObjectDetection(View view){
        Intent inte = new Intent(MainMenu.this,ObjectDetection.class);
        startActivity(inte);
    }
    public void onSettingsBtn(View view){
        Intent inte = new Intent(MainMenu.this,Settings.class);
        startActivity(inte);
    }
    public void onSosBtnClick(View view) {
        String number = "8605800662";

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel"+number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainMenu.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            return;
        }Uri.parse("tel"+number);
        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number)));
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("8605800662",null,"msg1asjk",null,null);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"completed",Toast.LENGTH_SHORT);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        GlobalVariables.current_page=Page_ID;
    }
}
