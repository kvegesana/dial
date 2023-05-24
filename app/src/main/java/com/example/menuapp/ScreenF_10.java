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

public class ScreenF_10 extends BaseActivity {

    TTS textToSpeech;
    Vibrator v;
    View contentView;
    Log log = new Log();
    private HashMap<String, Integer> mapElementToIndex = new HashMap<>();
    LinearLayout lv, lv1, lv2, lv3, lv4;
    String [] num ={"1","2","3","4","5","6","7","8","9","0","$","!","~","&","=","[","]","{","}","Caps",".","_","-","+","/","*","%","Del","ABC"};
    String [] alphabets = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "Caps", "z", "x", "c", "v", "b", "n", "m","Del","Numbers"};
    int numberOfInteractions;
    int numberOfLeftSwipes;
    int numberOfRightSwipes;
    int numberOfClicks;
    int numberOfWrongClicks;
    int previousIndex = -1;
    int currentIndex = -1;
    long t1, t2;
    String userid = null;
    int totalElements;
    TextView text;
    boolean isCaps;
    boolean isNum;

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
        numberOfWrongClicks = 0;
        isCaps = false;
        isNum = false;
        t1 = new Date().getTime();
        setContentView(R.layout.activity_screen_f_tts);
        lv = findViewById(R.id.screenF_lv);
        userid = getIntent().getExtras().getString("UserID");

        lv1 = lv.findViewById(R.id.screenF_lv1);
        lv2 = lv.findViewById(R.id.screenF_lv2);
        lv3 = lv.findViewById(R.id.screenF_lv3);
        lv4 = lv.findViewById(R.id.screenF_lv4);
        text = lv.findViewById(R.id.textView);
        // lv5 = lv.findViewById(R.id.screenE_lv5);
        totalElements = lv1.getChildCount() + lv2.getChildCount() + lv3.getChildCount() + lv4.getChildCount();

        String target_2 = returnCorrectTargetTalkback(ScreenF_10.this.getLocalClassName());
        for (int i = 0; i < totalElements; ++i) {
            TextView temp = (TextView) getTargetTextView(i);
            String element = temp.getText().toString();
            if (element.equals(target_2)) {
                temp.setPaintFlags(temp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
            mapElementToIndex.put(element, i);
        }
        contentView = findViewById(android.R.id.content);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                System.out.println("Event: " + event);
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    if (previousIndex == -1) {
                        previousIndex = 0;
                    } else {
                        String el = event.getText().toString().substring(1);
                        String element = el.substring(0, el.length() - 1);
                        currentIndex = mapElementToIndex.containsKey(element) ?
                                mapElementToIndex.get(element) : -1;
                        String target = returnCorrectTargetTalkback(ScreenF_10.this.getLocalClassName());
                        TextView tv = (TextView) getTargetTextView(currentIndex);
                        if (tv!= null && tv.getText().equals(target)) {
                            tv.setBackgroundResource(R.color.green);
                        }
                        tv = (TextView) getTargetTextView(previousIndex);
                        tv.setBackgroundResource(R.drawable.rounded_corner);
                        System.out.println(previousIndex+" Hello "+currentIndex+ " Element ? " + element+" "+el);
                        System.out.println(event.getText().toString());
                        if (currentIndex == previousIndex + 1) {
                            numberOfRightSwipes++;
                            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Grid Menu Talkback Variation10 " + "Button clicked: Right " + "Item selected: " + element);
                        } else if (currentIndex == previousIndex - 1) {
                            numberOfLeftSwipes++;
                            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Grid Menu Talkback Variation10 " + "Button clicked: Left " + "Item selected: " + element);
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
        if (index <= 9) {
            tv = (TextView) lv1.getChildAt(index);
        } else if (index <= 18) {
            tv = (TextView) lv2.getChildAt(index % 10);
        } else if (index <= 27) {
            tv = (TextView) lv3.getChildAt(index % 19);
        } else if (index <= 32) {
            tv = (TextView) lv4.getChildAt(index % 28);
        }
        return tv;
    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);
        log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Grid Menu Talkback Variation10 " + "Button clicked: Click " + "Item selected: " + tv.getText());
        numberOfClicks++;
        numberOfInteractions++;
        Intent intent = new Intent(this, ScreenB.class);

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
        if (tv != null && tv.getText().equals(target)) {
            t2 = new Date().getTime();
            worker.schedule(task, 2, TimeUnit.SECONDS);
            log.append4(userid, "Screen:Grid Menu Talkback, Variation:10, Target:" + target
                    + ", Number of interactions:" + numberOfInteractions + ", Time taken:" + (t2 - t1)
                    + ", Number of Left rotations:" + numberOfLeftSwipes + ", Number of Right rotations:"
                    + numberOfRightSwipes + ", Number of Clicks:" + numberOfClicks + ", Number of wrong clicks:"
                    + numberOfWrongClicks + ", Text:" + text.getText().toString() +";");
        }else if(tv != null && tv.getText().toString().toLowerCase().equals("caps")){
            if(isCaps){
                for (int i = 0; i < totalElements; ++i) {
                    if(i>26)continue;
                    TextView temp = (TextView) getTargetTextView(i);

                    //String element = temp.getText().toString();
                    temp.setAllCaps(false);
                    //mapElementToIndex.put(element, i);
                }
            }
            else{
                for (int i = 0; i < totalElements; ++i) {
                    if(i>26)continue;
                    TextView temp = (TextView) getTargetTextView(i);
                    //String element = temp.getText().toString();
                    temp.setAllCaps(true);
                    //mapElementToIndex.put(element, i);
                }
            }
            isCaps = !isCaps;
        }
        else if(tv != null && tv.getText().equals("Del")){
            System.out.println(text.getText().toString().length());
            if(text.getText().toString().length()>0) {
                String s = text.getText().toString().substring(0, text.getText().toString().length() - 1);
                text.setText(s);
            }
            else{
                text.setText("");
            }
        }
        else if(tv != null && (tv.getText().equals("Numbers") || tv.getText().equals("ABC"))){
            System.out.println(num.length+" Hello "+alphabets.length);
            if(isNum){
                for(int i=0;i<totalElements;++i){
                    if(i>28)continue;
                    TextView temp = (TextView) getTargetTextView(i);
                    temp.setText(alphabets[i]);
                }
            }
            else{
                for(int i=0;i<totalElements;++i){
                    if(i>28)continue;
                    TextView temp = (TextView) getTargetTextView(i);
                    temp.setText(num[i]);
                }
            }
            isNum = !isNum;
        }
        else if(tv != null && tv.getText().equals("Space")){
            text.append(" ");
        }
        else {
            text.append(!isCaps ? tv.getText() : tv.getText().toString().toUpperCase());
        }
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.onPause();
        }
        super.onPause();
    }


}