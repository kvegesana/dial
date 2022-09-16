package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationE extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userID = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_e);

        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userID);
    }

    public void openScreenE_1(View view){
        Intent intent = new Intent(this, ScreenE_1.class);
        startActivity(intent);
    }

    public void openScreenE_2(View view){
        Intent intent = new Intent(this, ScreenE_2.class);
        startActivity(intent);
    }

    public void openScreenE_3(View view){
        Intent intent = new Intent(this, ScreenE_3.class);
        startActivity(intent);
    }

    public void openScreenE_4(View view){
        Intent intent = new Intent(this, ScreenE_4.class);
        startActivity(intent);
    }

    public void openScreenE_5(View view){
        Intent intent = new Intent(this, ScreenE_5.class);
        startActivity(intent);
    }

    public void openScreenE_6(View view){
        Intent intent = new Intent(this, ScreenE_6.class);
        startActivity(intent);
    }

    public void openScreenE_7(View view){
        Intent intent = new Intent(this, ScreenE_7.class);
        startActivity(intent);
    }

    public void openScreenE_8(View view){
        Intent intent = new Intent(this, ScreenE_8.class);
        startActivity(intent);
    }

    public void openScreenE_9(View view){
        Intent intent = new Intent(this, ScreenE_9.class);
        startActivity(intent);
    }

    public void openScreenE_10(View view){
        Intent intent = new Intent(this, ScreenE_10.class);
        startActivity(intent);
    }

}
