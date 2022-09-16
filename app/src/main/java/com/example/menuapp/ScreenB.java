package com.example.menuapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScreenB extends Activity {
    String userId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getExtras().getString("UserID");
        setContentView(R.layout.activity_screen_b);

        TextView idTxt= findViewById(R.id.userId);
        idTxt.setText("User ID is " + userId);
    }

    public void openCVariation(View view){
        Intent intent = new Intent(this, VariationC.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openDVariation(View view){
        Intent intent = new Intent(this, VariationD.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openEVariation(View view){
        Intent intent = new Intent(this, VariationE.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openCTTSVariation(View view){
        Intent intent = new Intent(this, VariationC_TTS.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openDTTSVariation(View view){
        Intent intent = new Intent(this, VariationD_TTS.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }

    public void openETTSVariation(View view) {
        Intent intent = new Intent(this, VariationE_TTS.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
}
