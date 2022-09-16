package com.example.menuapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

public class ScreenE_10 extends Activity {

    TTS textToSpeech;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_e);
    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);
    }

    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.onPause();
        }
        super.onPause();
    }


}