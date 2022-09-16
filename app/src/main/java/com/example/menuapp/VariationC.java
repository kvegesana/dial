package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationC extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_c);

        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userID);
    }

    public void openScreenC_1(View view){
        Intent intent = new Intent(this, ScreenC_1.class);
        startActivity(intent);
    }

    public void openScreenC_2(View view){
        Intent intent = new Intent(this, ScreenC_2.class);
        startActivity(intent);
    }

    public void openScreenC_3(View view){
        Intent intent = new Intent(this, ScreenC_3.class);
        startActivity(intent);
    }

    public void openScreenC_4(View view){
        Intent intent = new Intent(this, ScreenC_4.class);
        startActivity(intent);
    }

    public void openScreenC_5(View view){
        Intent intent = new Intent(this, ScreenC_5.class);
        startActivity(intent);
    }

    public void openScreenC_6(View view){
        Intent intent = new Intent(this, ScreenC_6.class);
        startActivity(intent);
    }

    public void openScreenC_7(View view){
        Intent intent = new Intent(this, ScreenC_7.class);
        startActivity(intent);
    }

    public void openScreenC_8(View view){
        Intent intent = new Intent(this, ScreenC_8.class);
        startActivity(intent);
    }

    public void openScreenC_9(View view){
        Intent intent = new Intent(this, ScreenC_9.class);
        startActivity(intent);
    }

    public void openScreenC_10(View view){
        Intent intent = new Intent(this, ScreenC_10.class);
        startActivity(intent);
    }

}
