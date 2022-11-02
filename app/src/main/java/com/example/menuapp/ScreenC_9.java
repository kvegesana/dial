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

public class ScreenC_9 extends BaseActivity {

    TTS textToSpeech;
    Vibrator v;
    View contentView;
    Log log = new Log();
    private HashMap<String, Integer> mapElementToIndex = new HashMap<>();
    LinearLayout lv;
    int numberOfInteractions;
    int numberOfLeftSwipes;
    int numberOfRightSwipes;
    int numberOfClicks;
    int previousIndex = -1;
    int currentIndex = -1;
    long t1, t2;
    String userid = null;

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

        setContentView(R.layout.activity_screen_c);
        lv = findViewById(R.id.textList);
        int childCount = lv.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            TextView temp = (TextView) lv.getChildAt(i);
            String element = (String) temp.getText().toString();
            mapElementToIndex.put(element, i);
        }
        contentView = findViewById(android.R.id.content);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                System.out.println("Event : " + event.getText() + " " + event.getEventTime() + " " + event.getEventType());
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
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation9 " + "Button clicked: Right " + "Item selected: " + element);
                        } else if (currentIndex == previousIndex - 1) {
                            numberOfLeftSwipes++;
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation9 " + "Button clicked: Left " + "Item selected: " + element);
                        }
                        numberOfInteractions++;
                        previousIndex = currentIndex;
                    }

                }
                return super.onRequestSendAccessibilityEvent(host, child, event);
            }
        });
    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);
        log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation9 " + "Button clicked: Click " + "Item selected: " + tv.getText());
        Intent intent = new Intent(this, ScreenC_10.class);

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
            log.append2(userid, " Screen:Linear Menu Talkback"+","+ "Variation:9" +","+
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
