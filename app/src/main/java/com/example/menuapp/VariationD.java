package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationD extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_d);

        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userID);
    }

    public void openScreenD_1(View view){
        Intent intent = new Intent(this, ScreenD_1.class);
        startActivity(intent);
    }

    public void openScreenD_2(View view){
        Intent intent = new Intent(this, ScreenD_2.class);
        startActivity(intent);
    }

    public void openScreenD_3(View view){
        Intent intent = new Intent(this, ScreenD_3.class);
        startActivity(intent);
    }

    public void openScreenD_4(View view){
        Intent intent = new Intent(this, ScreenD_4.class);
        startActivity(intent);
    }

    public void openScreenD_5(View view){
        Intent intent = new Intent(this, ScreenD_5.class);
        startActivity(intent);
    }

    public void openScreenD_6(View view){
        Intent intent = new Intent(this, ScreenD_6.class);
        startActivity(intent);
    }

    public void openScreenD_7(View view){
        Intent intent = new Intent(this, ScreenD_7.class);
        startActivity(intent);
    }

    public void openScreenD_8(View view){
        Intent intent = new Intent(this, ScreenD_8.class);
        startActivity(intent);
    }

    public void openScreenD_9(View view){
        Intent intent = new Intent(this, ScreenD_9.class);
        startActivity(intent);
    }

    public void openScreenD_10(View view){
        Intent intent = new Intent(this, ScreenD_10.class);
        startActivity(intent);
    }

}
