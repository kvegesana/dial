package com.example.menuapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenC_1 extends BaseActivity {

    TTS textToSpeech;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_c);

    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);

        Intent intent = new Intent(this, ScreenC_2.class);

        Runnable task = new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
            }
        };

        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        String target = returnCorrectTargetTalkback(this.getLocalClassName());
        System.out.println(target+" "+this.getLocalClassName());
        if(tv.getText().equals(target))
            worker.schedule(task, 2, TimeUnit.SECONDS);


    }

    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.onPause();
        }
        super.onPause();
    }


}
