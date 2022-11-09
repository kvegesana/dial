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
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenE_TTS_3 extends BaseActivity {

    LinearLayout lv,lv1,lv2,lv3, lv4, lv5;
    int curIndex = 0;
    boolean isInitial = true;
    TextView tv;
    TTS textToSpeech;
    Vibrator v;
    int totalElements;
    Log log = new Log();
    String userid = null;
    int numberOfWrongClicks, numberOfInteractions, numberOfLeftActions, numberOfRightActions, numberOfClicks;
    long t1,t2;
    String target;

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
        numberOfWrongClicks=0;

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

        totalElements = lv1.getChildCount() + lv2.getChildCount() + lv3.getChildCount() + lv4.getChildCount() +lv5.getChildCount()   ;

        userid = getIntent().getExtras().getString("UserID");


        IntentFilter sbu_filter = new IntentFilter();
        sbu_filter.addAction(SBU_ACTION);
        registerReceiver(sbu_receiver, sbu_filter);
        initial();
        String classname = this.getLocalClassName();
        System.out.println("classname: "+classname);
        int temp_idx = Integer.parseInt(classname.substring(12));
        System.out.println("id is : " +temp_idx);
        int target_idx = 0;
        switch(temp_idx){
            case 1:
                target_idx = 9;
                break;
            case 2 :
                target_idx = 12;
                break;
            case 3 :
                target_idx = 17;
                break;
            case 4 :
                target_idx = 11;
                break;
            case 5 :
                target_idx = 14;
                break;
            case 6 :
                target_idx = 19;
                break;
            case 7 :
                target_idx = 16;
                break;
            case 8 :
                target_idx = 6;
                break;
            case 9 :
                target_idx = 5;

                break;
            case 10 :
                target_idx = 15;

                break;
        }
        System.out.println(target_idx);
        TextView temp = getTargetTextView(target_idx);
        temp.setPaintFlags(temp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);


    }

    public void initial(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                curIndex = 0;
                TextView tv = getTextView();
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
//            curIndex = totalElements;
            TextView tv = getTextView();
            textToSpeech.speakTextView(tv);
            isInitial = false;
        }

        if(curIndex > 0){
            TextView original = getTextView();
            if(original != null) {
                original.setBackgroundResource(R.drawable.rounded_corner);
            }
            curIndex = curIndex - 1;
            tv = getTextView();
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.rounded_corner_border);
            System.out.println("goLeft" + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation3 " + "Button clicked: Left " + "Item selected: " + tv.getText());

        }else{
//            textToSpeech.playErrorSound();
            tv = getTextView();
            textToSpeech.speakTextView(tv);
            System.out.println("goLeft " + curIndex + " Out of bounds") ;
            //curIndex = totalElements;
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation3 " + "Button clicked: Left " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.drawable.rounded_corner_bg_green);
        }
    }

    public TextView getTargetTextView(int index) {
        if (index <= 3) {
            tv = (TextView) lv1.getChildAt(index);
        } else if (index <= 7) {
            tv = (TextView) lv2.getChildAt(index % 4);
        } else if (index <= 11) {
            tv = (TextView) lv3.getChildAt(index % 4);
        } else if (index <= 15) {
            tv = (TextView) lv4.getChildAt(index % 4);
        } else if (index <= 19) {
            tv = (TextView) lv5.getChildAt(index % 4);
        }
        return tv;
    }

    public void goRight(View view) {
        numberOfInteractions+=1;
        numberOfRightActions+=1;
        if(isInitial){
//            curIndex = -1;
            isInitial = false;
        }

        if(curIndex < totalElements - 1){
            TextView original = getTextView();
            if(original != null) {
                original.setBackgroundResource(R.drawable.rounded_corner);
            }
            curIndex = curIndex + 1;
            tv = getTextView();

            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.rounded_corner_border);
            System.out.println("goRight " + curIndex + " " + tv.getText());
            log.append(userid,"UserID: "+ userid + " " +"Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation3 " + "Button clicked: Right " + "Item selected: " + tv.getText());



        }else{
//            textToSpeech.playErrorSound();
            tv = getTextView();
            textToSpeech.speakTextView(tv);
            System.out.println("goRight " + curIndex + " Out of bounds") ;
            //curIndex = -1;
            log.append(userid,"UserID: "+ userid+" " +  "Timestamp: " + new Date().getTime() +" " +" Screen: Grid Menu Dial Variation3 " + "Button clicked: Right " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.drawable.rounded_corner_bg_green);
        }
    }

    public void onClick(View view) {
        numberOfInteractions+=1;
        numberOfClicks+=1;
        if(!isInitial) {
            tv = (TextView) lv1.getChildAt(0);
            if (curIndex >= 0 && curIndex < totalElements) {
                tv = getTextView();
            } else if (curIndex < 0) {
                tv = (TextView) lv5.getChildAt(1);
            } else {
                tv = (TextView) lv1.getChildAt(0);
            }
            textToSpeech.speakSelectedTextView(tv);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Grid Menu Dial Variation3 " + "Button clicked: Click " + "Item selected: " + tv.getText());
            Intent intent = new Intent(this, ScreenE_TTS_4.class);

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
                log.append2(userid, "Screen:Grid Menu Dial, Variation:3, Target:"+target + ", Number of interactions:"+numberOfInteractions+", Time taken:"+(t2-t1)+", Number of Left rotations:"+numberOfLeftActions+", Number of Right rotations:"+numberOfRightActions+", Number of Clicks:"+numberOfClicks+", Number of wrong clicks:"+numberOfWrongClicks+";");}
            else {
                numberOfWrongClicks+=1;
                tv.setBackgroundResource(R.color.red);
            }
        }
    }

    public TextView getTextView() {
        if (curIndex <= 3) {
            tv = (TextView) lv1.getChildAt(curIndex);
        } else if (curIndex <= 7) {
            tv = (TextView) lv2.getChildAt(curIndex % 4);
        } else if (curIndex <= 11) {
            tv = (TextView) lv3.getChildAt(curIndex % 4);
        } else if (curIndex <= 15) {
            tv = (TextView) lv4.getChildAt(curIndex % 4);
        } else if (curIndex <= 19) {
            tv = (TextView) lv5.getChildAt(curIndex % 4);
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