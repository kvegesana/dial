package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VariationE_TTS extends Activity {
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_variation_e_tts);
        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userId);
    }

    public void openScreenE_TTS_1(View view){
        Intent intent = new Intent(this, ScreenE_TTS_1.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_2(View view){
        Intent intent = new Intent(this, ScreenE_TTS_2.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_3(View view){
        Intent intent = new Intent(this, ScreenE_TTS_3.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
    public void openScreenE_TTS_4(View view){
        Intent intent = new Intent(this, ScreenE_TTS_4.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_5(View view){
        Intent intent = new Intent(this, ScreenE_TTS_5.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_6(View view){
        Intent intent = new Intent(this, ScreenE_TTS_6.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_7(View view){
        Intent intent = new Intent(this, ScreenE_TTS_7.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_8(View view){
        Intent intent = new Intent(this, ScreenE_TTS_8.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_9(View view){
        Intent intent = new Intent(this, ScreenE_TTS_9.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openScreenE_TTS_10(View view){
        Intent intent = new Intent(this, ScreenE_TTS_10.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

}
