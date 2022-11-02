package com.example.menuapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenE_1 extends BaseActivity {

    TTS textToSpeech;
    Vibrator v;
    View contentView;
    Log log = new Log();
    private HashMap<String, Integer> mapElementToIndex = new HashMap<>();
    LinearLayout lv, lv1, lv2, lv3, lv4, lv5;
    int numberOfInteractions;
    int numberOfLeftSwipes;
    int numberOfRightSwipes;
    int numberOfClicks;
    int previousIndex = -1;
    int currentIndex = -1;
    long t1, t2;
    String userid = null;
    int totalElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this, v);
        numberOfInteractions = 0;
        numberOfLeftSwipes = 0;
        numberOfClicks = 0;
        numberOfRightSwipes = 0;
        t1 = new Date().getTime();
        setContentView(R.layout.activity_screen_e);
        lv = findViewById(R.id.screenE_lv);
        userid = getIntent().getExtras().getString("UserID");

        lv1 = lv.findViewById(R.id.screenE_lv1);
        lv2 = lv.findViewById(R.id.screenE_lv2);
        lv3 = lv.findViewById(R.id.screenE_lv3);
        lv4 = lv.findViewById(R.id.screenE_lv4);
        lv5 = lv.findViewById(R.id.screenE_lv5);
        totalElements = lv1.getChildCount() + lv2.getChildCount() + lv3.getChildCount() + lv4.getChildCount() + lv5.getChildCount();

        for (int i = 0; i < totalElements; ++i) {
            TextView temp = (TextView) getTargetTextView(i);
            String element = temp.getText().toString();
            mapElementToIndex.put(element,i);
        }
        contentView = findViewById(android.R.id.content);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate(){
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                System.out.println("Event: "+event);
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    if (previousIndex == -1) {
                        previousIndex = 0;
                    } else {
                        String el = event.getText().toString().substring(1);
                        String element = el.substring(0, el.length() - 1);
                        currentIndex = mapElementToIndex.containsKey(element) ?
                                mapElementToIndex.get(element) : -1;
                        if (currentIndex == previousIndex + 1) {
                            numberOfRightSwipes++;
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Grid Menu Talkback Variation1 " + "Button clicked: Right " + "Item selected: " + element);
                        } else if (currentIndex == previousIndex - 1) {
                            numberOfLeftSwipes++;
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Grid Menu Talkback Variation1 " + "Button clicked: Left " + "Item selected: " + element);
                        }
                        numberOfInteractions++;
                        previousIndex = currentIndex;
                    }

                }
                return super.onRequestSendAccessibilityEvent(host, child, event);
            }
        });

    }

    public TextView getTargetTextView(int index) {
        TextView tv = null;
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

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);
        log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Grid Menu Talkback Variation1 " + "Button clicked: Click " + "Item selected: " + tv.getText());
        numberOfClicks++;
        numberOfInteractions++;
        Intent intent = new Intent(this, ScreenE_2.class);

        Runnable task = new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
            }
        };

        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        String target = returnCorrectTargetTalkback(this.getLocalClassName());
        System.out.println(target + " " + this.getLocalClassName());
        if (tv.getText().equals(target)) {
            t2 = new Date().getTime();
            worker.schedule(task, 2, TimeUnit.SECONDS);
            log.append2(userid, " Screen:Grid Menu Talkback"+","+ "Variation:1" +","+
                    "Number of interactions:" + numberOfInteractions +","+
                    "Time taken:" + (t2 - t1) + ","+" Number of LeftSwipes:" + numberOfLeftSwipes +","+
                    "Number of RightSwipes:" + numberOfRightSwipes +","+
                    "Number of Clicks:" + numberOfClicks);
        }
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.onPause();
        }
        super.onPause();
    }


}