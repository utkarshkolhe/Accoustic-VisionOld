package com.example.nihar.delta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    protected int Page_ID = 3;
    private static final int REQUEST_CALL=1;
    protected Button textrecog,objectdet,settingsbtn,profilebtn,sosbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TTS.Initalize(this);
        GlobalVariables.current_page=Page_ID;
        textrecog = findViewById(R.id.textreg);
        objectdet = findViewById(R.id.objdet);
        settingsbtn = findViewById(R.id.Settings);
        profilebtn = findViewById(R.id.Profile);
        sosbtn = findViewById(R.id.button4);
        if(GlobalVariables.login_status)
            Toast.makeText(this,"Logged in as : "+GlobalVariables.username,Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Guest Login", Toast.LENGTH_SHORT).show();
        }
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
    public void onProfileBtn(View view){
        Intent inte = new Intent(MainMenu.this,Form.class);
        startActivity(inte);
    }
    public void onSosBtnClick(View view) {
        if(!GlobalVariables.login_status){
            Toast.makeText(getApplicationContext(),"You must Login",Toast.LENGTH_SHORT).show();
            return;
        }
        if(GlobalVariables.user.GNumber==null){
            Toast.makeText(getApplicationContext(),"You must Login",Toast.LENGTH_SHORT).show();
            return;
        }
        String number = GlobalVariables.user.GNumber;
        String enum1 = GlobalVariables.user.msgnumber1;
        String enum2 = GlobalVariables.user.msgnumber2;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel"+number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainMenu.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            return;
        }Uri.parse("tel"+number);
        String message= "Need Help. Emergency. Here's my location: ";
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, new LocationListener() {
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
            @Override
            public void onLocationChanged(final Location location) {}
        });
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        double longitude = myLocation.getLongitude();
        double latitude = myLocation.getLatitude();
        message+="http://maps.google.com/?q="+latitude+","+longitude;
        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number)));
        SmsManager smsManager = SmsManager.getDefault();
        try {
            if(!number.equals(""))
                smsManager.sendTextMessage(number, null, message, null, null);
            if(!enum1.equals(""))
                smsManager.sendTextMessage(enum1, null, message, null, null);
            if(!enum2.equals(""))
                smsManager.sendTextMessage(enum2, null, message, null, null);
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Incorrect Numbers",Toast.LENGTH_SHORT).show();
        }
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
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //recreate();
    }
    public void clickTextRecog(){
        textrecog.callOnClick();
    }
    public void clickObjectDet(){
        objectdet.callOnClick();
    }
    public void clickSettings(){
        settingsbtn.callOnClick();
    }
    public void clickProfile(){
        profilebtn.callOnClick();
    }
    public void clickSOS(){sosbtn.callOnClick();}
    public  void callBack(){
        finish();
    }

}
