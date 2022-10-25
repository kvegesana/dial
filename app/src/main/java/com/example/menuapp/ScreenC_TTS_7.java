package com.example.menuapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenC_TTS_7 extends BaseActivity {

    LinearLayout lv;
    int curIndex = 0;
    boolean isInitial = true;

    TTS textToSpeech;
    Vibrator v;
    Log log = new Log();
    String userid = null;
    TextView tv;
    boolean outOfBounds = false;
    int numberOfInteractions;
    long t1,t2;
    String target;
    ScrollView sv;

    public static final String SBU_ACTION = "sbuCustomGesture";
    public static final String EXTRA_SBU_ACTION = "sbuGestureAction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        target = returnCorrectTarget(this.getLocalClassName());
        numberOfInteractions = 0;
        t1 =new Date().getTime();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_c_tts);

        lv = findViewById(R.id.textList);
        System.out.println("Total children are " + lv.getChildCount());
        userid = getIntent().getExtras().getString("UserID");

        IntentFilter sbu_filter = new IntentFilter();
        sbu_filter.addAction(SBU_ACTION);
        registerReceiver(sbu_receiver, sbu_filter);
        sv = findViewById(R.id.ScrollViewID);
        initial();
    }

    public void initial(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                curIndex = 0;
                TextView tv = (TextView) lv.getChildAt(curIndex);
                System.out.println(tv);
                textToSpeech.speakTextView(tv);
                tv.requestFocus();
                tv.setBackgroundResource(R.drawable.border);
            }
        };
        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        worker.schedule(task, 1, TimeUnit.SECONDS);
    }

    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.onPause();
        }
        try {
            unregisterReceiver(sbu_receiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        super.onPause();
    }


    public void goLeft(View view) {
        numberOfInteractions+=1;
        if(isInitial){
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
//            textToSpeech.playErrorSound();
            outOfBounds = true;
            //curIndex = 16;
            isInitial = false;
            return;
        }

        if(outOfBounds && curIndex == 0) {
            //curIndex = 16;
        }

        if(curIndex > 0){
            TextView original = (TextView) lv.getChildAt(curIndex);
            if(original != null) {
                original.setBackgroundResource(R.drawable.remove_border);
            }
            curIndex = curIndex - 1;
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
            outOfBounds = false;
            System.out.println("goLeft" + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation7 " + "Button clicked: Left " + "Item selected: " + tv.getText());
        }else{
//            textToSpeech.playErrorSound();
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
            System.out.println("goLeft " + curIndex + " Out of bounds") ;
            //curIndex = 16;
            outOfBounds = true;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation7 " + "Button clicked: Left " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.color.green);
        }
        if(curIndex > 9){
            sv.smoothScrollTo(0, sv.getHeight());
        }
        if(curIndex < 6) {
            sv.smoothScrollTo(0,0);
        }
    }

    public void goRight(View view) {
        numberOfInteractions+=1;
        if(isInitial){
            //curIndex = -1;
            isInitial = false;
        }

        System.out.println(curIndex + 1 + " " + lv.getChildCount());

        if(outOfBounds && curIndex == 16) {
            //curIndex = 0;
        }

        if(curIndex < 15){
            TextView original = (TextView) lv.getChildAt(curIndex);
            if(original != null) {
                original.setBackgroundResource(R.drawable.remove_border);
            }
            curIndex = curIndex + 1;
            tv = (TextView) lv.getChildAt(curIndex);
            System.out.println(getCurrentFocus());
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
            outOfBounds = false;
            System.out.println("goRight " + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Linear Menu Dial Variation7 " + "Button clicked: Right " + "Item selected: " + tv.getText());

        }else{
//            textToSpeech.playErrorSound();
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
            System.out.println("goRight " + curIndex + " Out of bounds");
            //curIndex = -1;
            outOfBounds = true;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation7 " + "Button clicked: Right " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.color.green);
        }
        if(curIndex > 9){
            sv.smoothScrollTo(0, sv.getHeight());
        }
        if(curIndex < 6) {
            sv.smoothScrollTo(0,0);
        }
    }


    public void onClick(View view) {
        numberOfInteractions+=1;
        if(!isInitial) {
            if (curIndex >= 0 && curIndex < 16) {
                tv = (TextView) lv.getChildAt(curIndex);
            } else if (curIndex < 0) {
                tv = (TextView) lv.getChildAt(0);
            } else {
                tv = (TextView) lv.getChildAt(15);
            }
            textToSpeech.speakSelectedTextView(tv);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation7 " + "Button clicked: Click " + "Item selected: " + tv.getText());

            Intent intent = new Intent(this, ScreenC_TTS_8.class);

            Runnable task = new Runnable() {

                @Override
                public void run() {
                    intent.putExtra("UserID", userid);
                    startActivity(intent);
                }
            };

            final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
            String target = returnCorrectTarget(this.getLocalClassName());
            System.out.println(target+" "+this.getLocalClassName());
            if(tv.getText().equals(target)) {
                worker.schedule(task, 2, TimeUnit.SECONDS);
                t2 = new Date().getTime();
                log.append2(userid, " Screen: Linear Menu Dial Variation7 " + "Number of interactions: "+numberOfInteractions+" Time taken: "+(t2-t1));
            }
        }
    }


    final BroadcastReceiver sbu_receiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    System.out.println("Received some intent " + intent);
                    final String action = intent.getAction();

                    View view = findViewById(R.id.textList);

                        if (SBU_ACTION.equals(action)) {
                            int gestureId =
                                    intent.getIntExtra(EXTRA_SBU_ACTION, 0);
                            if (gestureId == 100){
                                onClick(view);
                            }
                            else if(gestureId == 3){
                                goLeft(view);
                            }else if(gestureId == 4){
                                goRight(view);
                            }
                    }
                }
            };

}
