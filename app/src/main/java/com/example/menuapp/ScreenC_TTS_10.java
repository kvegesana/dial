package com.example.menuapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
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

public class ScreenC_TTS_10 extends BaseActivity {

    LinearLayout lv;
    int curIndex = 0;
    boolean isInitial = true;

    TTS textToSpeech;
    Vibrator v;
    Log log = new Log();
    String userid = null;
    TextView tv;
    boolean outOfBounds = false;
    int numberOfInteractions, numberOfLeftActions, numberOfRightActions, numberOfClicks;
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
        numberOfClicks = 0;
        numberOfLeftActions = 0;
        numberOfRightActions = 0;
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
        String classname = this.getLocalClassName();
        System.out.println("classname: "+classname);
        int temp_idx = Integer.parseInt(classname.substring(12));
        System.out.println("id is : " +temp_idx);
        int target_idx = 0;
        switch(temp_idx){
            case 1:
                target_idx = 5;
                break;
            case 2 :
                target_idx = 9;
                break;
            case 3 :
                target_idx = 12;
                break;
            case 4 :
                target_idx = 2;
                break;
            case 5 :
                target_idx = 8;
                break;
            case 6 :
                target_idx = 6;
                break;
            case 7 :
                target_idx = 4;
                break;
            case 8 :
                target_idx = 15;
                break;
            case 9 :
                target_idx = 7;
                break;
            case 10 :
                target_idx = 3;
                break;
        }
        System.out.println(target_idx);
        TextView temp = (TextView) lv.getChildAt(target_idx);
        temp.setPaintFlags(temp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
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
        numberOfLeftActions+=1;
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
            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation10 " + "Button clicked: Left " + "Item selected: " + tv.getText());
        }else{
//            textToSpeech.playErrorSound();
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
            System.out.println("goLeft " + curIndex + " Out of bounds") ;
            //curIndex = 16;
            outOfBounds = true;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation10 " + "Button clicked: Left " + "Item selected: Out of bounds");
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
        numberOfRightActions+=1;
        if(isInitial){
           // curIndex = -1;
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
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Linear Menu Dial Variation10 " + "Button clicked: Right " + "Item selected: " + tv.getText());

        }else{
//            textToSpeech.playErrorSound();
            tv = (TextView) lv.getChildAt(curIndex);
            textToSpeech.speakTextView(tv);
            System.out.println("goRight " + curIndex + " Out of bounds");
            //curIndex = -1;
            outOfBounds = true;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation10 " + "Button clicked: Right " + "Item selected: Out of bounds");
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
        numberOfClicks+=1;
        if(!isInitial) {
            if (curIndex >= 0 && curIndex < 16) {
                tv = (TextView) lv.getChildAt(curIndex);
            } else if (curIndex < 0) {
                tv = (TextView) lv.getChildAt(0);
            } else {
                tv = (TextView) lv.getChildAt(15);
            }
            textToSpeech.speakSelectedTextView(tv);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Dial Variation10 " + "Button clicked: Click " + "Item selected: " + tv.getText());
            String target = returnCorrectTarget(this.getLocalClassName());
            if(tv.getText().equals(target)) {
                t2 = new Date().getTime();
                log.append2(userid, "Screen:Linear Menu Dial, Variation:10, " + "Number of interactions:"+numberOfInteractions+", Time taken:"+(t2-t1)+", Number of Left rotations:"+numberOfLeftActions+", Number of Right rotations:"+numberOfRightActions+", Number of Clicks:"+numberOfClicks+";");}
            else {
                tv.setBackgroundResource(R.color.red);
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
