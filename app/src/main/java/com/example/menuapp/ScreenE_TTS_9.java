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
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenE_TTS_9 extends BaseActivity {

    LinearLayout lv,lv1,lv2,lv3,lv4, lv5, lv6 ,lv7;
    int curIndex = -1;
    boolean isInitial = true;
    TextView tv;
    TTS textToSpeech;
    Vibrator v;
    int totalElements;
    Log log = new Log();
    String userid = null;
    int numberOfInteractions;
    long t1,t2;

    public static final String SBU_ACTION = "sbuCustomGesture";
    public static final String EXTRA_SBU_ACTION = "sbuGestureAction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberOfInteractions = 0;
        t1 =new Date().getTime();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_e_tts);

        lv = findViewById(R.id.screenE_lv);

        lv1 = lv.findViewById(R.id.screenE_lv1);
        lv2 = lv.findViewById(R.id.screenE_lv2);
        lv3 = lv.findViewById(R.id.screenE_lv3);
        lv4 = lv.findViewById(R.id.screenE_lv4);
        lv5 = lv.findViewById(R.id.screenE_lv5);
        lv6 = lv.findViewById(R.id.screenE_lv6);
        lv7 = lv.findViewById(R.id.screenE_lv7);

        totalElements = lv1.getChildCount() + lv2.getChildCount() + lv3.getChildCount() + lv4.getChildCount() +lv5.getChildCount() +lv6.getChildCount() +lv7.getChildCount()  ;

        userid = getIntent().getExtras().getString("UserID");


        IntentFilter sbu_filter = new IntentFilter();
        sbu_filter.addAction(SBU_ACTION);
        registerReceiver(sbu_receiver, sbu_filter);

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
            curIndex = totalElements;
            isInitial = false;
        }

        if(curIndex > 0){
            curIndex = curIndex - 1;
            tv = getTextView();
            textToSpeech.speakTextView(tv);
            System.out.println("goLeft" + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation9 " + "Button clicked: Left " + "Item selected: " + tv.getText());

        }else{
            textToSpeech.playErrorSound();
            System.out.println("goLeft " + curIndex + " Out of bounds") ;
            curIndex = totalElements;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation9 " + "Button clicked: Left " + "Item selected: Out of bounds");
        }
    }

    public void goRight(View view) {
        numberOfInteractions+=1;
        if(isInitial){
            curIndex = -1;
            isInitial = false;
        }

        if(curIndex < totalElements - 1){
            curIndex = curIndex + 1;
            tv = getTextView();

            textToSpeech.speakTextView(tv);
            System.out.println("goRight " + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid + " " +"Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation9 " + "Button clicked: Right " + "Item selected: " + tv.getText());



        }else{
            textToSpeech.playErrorSound();
            System.out.println("goRight " + curIndex + " Out of bounds") ;
            curIndex = -1;
            log.append(userid,"UserID: "+ userid+" " +  "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation9 " + "Button clicked: Right " + "Item selected: Out of bounds");
        }
    }

    public void onClick(View view) {
        numberOfInteractions+=1;
        if(!isInitial) {
            tv = (TextView) lv1.getChildAt(0);
            if (curIndex >= 0 && curIndex < totalElements) {
                tv = getTextView();
            } else if (curIndex < 0) {
                tv = (TextView) lv7.getChildAt(1);
            } else {
                tv = (TextView) lv1.getChildAt(0);
            }
            textToSpeech.speakSelectedTextView(tv);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Grid Menu Dial Variation9 " + "Button clicked: Click " + "Item selected: " + tv.getText());
            Intent intent = new Intent(this, ScreenE_TTS_10.class);

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
                log.append2(userid, " Screen: Grid Menu Dial Variation9 " + "Number of interactions: "+numberOfInteractions+" Time taken: "+(t2-t1));
            }
        }
    }

    public TextView getTextView() {
        if (curIndex <= 4) {
            tv = (TextView) lv1.getChildAt(curIndex);
        } else if (curIndex <= 9) {
            tv = (TextView) lv2.getChildAt(curIndex % 5);
        } else if (curIndex <= 14) {
            tv = (TextView) lv3.getChildAt(curIndex % 5);
        } else if (curIndex <= 19) {
            tv = (TextView) lv4.getChildAt(curIndex % 5);
        } else if (curIndex <= 24) {
            tv = (TextView) lv5.getChildAt(curIndex % 5);
        } else if (curIndex <= 29) {
            tv = (TextView) lv6.getChildAt(curIndex % 5);
        } else if (curIndex <= 34) {
            tv = (TextView) lv7.getChildAt(curIndex % 5);
        }
        return tv;
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
                            if (gestureId == 100) {
                                onClick(view);
                            } else if (gestureId == 3) {
                                goLeft(view);
                            } else if (gestureId == 4) {
                                goRight(view);
                            }
                        }
                    }
                };

    }