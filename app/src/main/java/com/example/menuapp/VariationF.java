package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationF extends Activity {
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_f);
        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userId);
    }

    public void openScreenF_1(View view){
        Intent intent = new Intent(this, ScreenF_1.class);
        System.out.println("Screen F");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_2(View view){
        Intent intent = new Intent(this, ScreenF_2.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_3(View view){
        Intent intent = new Intent(this, ScreenF_3.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_4(View view){
        Intent intent = new Intent(this, ScreenF_4.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_5(View view){
        Intent intent = new Intent(this, ScreenF_5.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_6(View view){
        Intent intent = new Intent(this, ScreenF_6.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_7(View view){
        Intent intent = new Intent(this, ScreenF_7.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_8(View view){
        Intent intent = new Intent(this, ScreenF_8.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_9(View view){
        Intent intent = new Intent(this, ScreenF_9.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_10(View view){
        Intent intent = new Intent(this, ScreenF_10.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

}
