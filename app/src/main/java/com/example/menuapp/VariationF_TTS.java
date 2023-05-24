package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationF_TTS extends Activity {

    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_f_tts);
        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userId);
    }

    public void openScreenF_TTS_1(View view){
        Intent intent = new Intent(this, ScreenF_TTS_1.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenF_TTS_2(View view){
        Intent intent = new Intent(this, ScreenF_TTS_2.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_3(View view){
        Intent intent = new Intent(this, ScreenF_TTS_3.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_4(View view){
        Intent intent = new Intent(this, ScreenF_TTS_4.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_5(View view){
        Intent intent = new Intent(this, ScreenF_TTS_5.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_6(View view){
        Intent intent = new Intent(this, ScreenF_TTS_6.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_7(View view){
        Intent intent = new Intent(this, ScreenF_TTS_7.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_8(View view){
        Intent intent = new Intent(this, ScreenF_TTS_8.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_9(View view){
        Intent intent = new Intent(this, ScreenF_TTS_9.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenF_TTS_10(View view){
        Intent intent = new Intent(this, ScreenF_TTS_10.class);
        System.out.println("Screen F_TTS");
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
}
