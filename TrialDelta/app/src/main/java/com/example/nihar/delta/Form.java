package com.example.nihar.delta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Form extends AppCompatActivity {
    EditText name,address,gname,gnumber,enum1,enum2,age;
    Spinner bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.NameET);
        age = findViewById(R.id.AgeET);
        address = findViewById(R.id.AddressET);
        gname = findViewById(R.id.GNameET);
        gnumber = findViewById(R.id.GNumberET);
        enum1 = findViewById(R.id.ENumber1ET);
        enum2 = findViewById(R.id.ENumber2ET);
        bg = findViewById(R.id.spinner);


        name.setText(GlobalVariables.user.Name);
        age.setText(GlobalVariables.user.age);
        address.setText(GlobalVariables.user.Address);
        gname.setText(GlobalVariables.user.GName);
        gnumber.setText(GlobalVariables.user.GNumber);
        enum1.setText(GlobalVariables.user.msgnumber1);
        enum2.setText(GlobalVariables.user.msgnumber2);
        switch (GlobalVariables.user.BloodGroup){
            case "A-":
                bg.setSelection(1);
                break;
            case "A+":
                bg.setSelection(2);
                break;
            case "B-":
                bg.setSelection(3);
                break;
            case "B+":
                bg.setSelection(4);
                break;
            case "AB-":
                bg.setSelection(5);
                break;
            case "AB+":
                bg.setSelection(6);
                break;
            case "O-":
                bg.setSelection(7);
                break;
            case "O+":
                bg.setSelection(8);
                break;
            default:
                bg.setSelection(0);
                break;
        }

    }
    public void onsaveBtn(View view){
        GlobalVariables.user.Name = name.getText().toString();
        GlobalVariables.user.Address = address.getText().toString();
        GlobalVariables.user.GName = gname.getText().toString();
        GlobalVariables.user.GNumber = gnumber.getText().toString();
        GlobalVariables.user.msgnumber1 = enum1.getText().toString();
        GlobalVariables.user.msgnumber2 = enum2.getText().toString();
        GlobalVariables.user.age = age.getText().toString();
        GlobalVariables.user.BloodGroup = bg.getSelectedItem().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getInstance().getReference().child("Data");
        ref.child(GlobalVariables.username).setValue(GlobalVariables.user);

        Intent inte = new Intent(Form.this,MainMenu.class);
        startActivity(inte);
        finish();
    }
    public void onSkip(View view){
        Intent inte = new Intent(Form.this,MainMenu.class);
        startActivity(inte);
        finish();
    }

}
