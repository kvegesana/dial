package com.example.menuapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

public class ScreenC_1 extends BaseActivity {

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
        userid = getIntent().getExtras().getString("UserID");

        int childCount = lv.getChildCount();
        String target_2 = returnCorrectTargetTalkback(ScreenC_1.this.getLocalClassName());
        for (int i = 0; i < childCount; ++i) {
            TextView temp = (TextView) lv.getChildAt(i);
            String element = (String) temp.getText().toString();
            if(element.equals(target_2)){
                temp.setPaintFlags(temp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            }
            mapElementToIndex.put(element, i);
        }
        contentView = findViewById(android.R.id.content);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                System.out.println("Event : " + event.getText().toString() + " " + event.getEventTime() + " " + event.getEventType());
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    if (previousIndex == -1) {
                        previousIndex = 0;
                    } else {
                        String el = event.getText().toString().substring(1);
                        String element = el.substring(0, el.length() - 1);
                        currentIndex = mapElementToIndex.containsKey(element) ?
                                mapElementToIndex.get(element) : -1;
                        String target = returnCorrectTargetTalkback(ScreenC_1.this.getLocalClassName());
                        TextView tv = (TextView) lv.getChildAt(currentIndex);
                        if(tv.getText().equals(target)){
                            tv.setBackgroundResource(R.color.green);
                        }
                        tv = (TextView) lv.getChildAt(previousIndex);
                        tv.setBackgroundResource(R.drawable.remove_border);
                        if (currentIndex == previousIndex + 1) {
                            numberOfRightSwipes++;
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation1 " + "Button clicked: Right " + "Item selected: " + element);
                        } else if (currentIndex == previousIndex - 1) {
                            numberOfLeftSwipes++;
                            log.append(userid,"UserID: "+ userid +  " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation1 " + "Button clicked: Left " + "Item selected: " + element);
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
        log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Linear Menu Talkback Variation1 " + "Button clicked: Click " + "Item selected: " + tv.getText());
        numberOfClicks++;
        numberOfInteractions++;
        Intent intent = new Intent(this, ScreenC_2.class);

        Runnable task = new Runnable() {

            @Override
            public void run() {
                intent.putExtra("UserID", userid);
                startActivity(intent);
            }
        };

        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        String target = returnCorrectTargetTalkback(this.getLocalClassName());
        System.out.println(target + " " + this.getLocalClassName());
        if (tv.getText().equals(target)) {
            t2 = new Date().getTime();
            worker.schedule(task, 2, TimeUnit.SECONDS);
            log.append2(userid, " Screen:Linear Menu Talkback"+","+ "Variation:1" +","+
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
