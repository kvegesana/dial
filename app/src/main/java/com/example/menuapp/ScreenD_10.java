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
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenD_10 extends BaseActivity {

    LinearLayout lv, lv1, lv1_1, lv1_2;

    String[] fruits = {"Mango", "Peach", "Banana", "Kiwi", "Apple", "Pear", "Plum", "Guava", "Grapes", "Strawberry", "Blueberry", "Orange", "Lychee", "Papaya", "Pineapple", "Watermelon"};
    String[] cars = {"Mercedes", "BMW", "Volkswagen", "Ferrari", "Lamborghini", "Audi", "Suzuki", "Cooper", "Toyota", "Nissan", "Tesla", "Kia", "Fiat", "Dodge", "Ford", "Subaru"};
    String[] colors = {"Blue", "Black", "White", "Pink", "Yellow", "Red", "Green", "Brown", "Gray", "Magenta", "Beige", "Maroon", "Purple", "Orange", "Violet", "Indigo"};
    String[] countries = {"USA", "UK", "India", "France", "Spain", "Japan", "China", "Indonesia", "Mexico", "Argentina", "Brazil", "Canada", "Australia", "Turkey", "Italy", "Ireland"};
    String[] vegetables = {"Eggplant", "Cucumber", "Corn", "Mushroom", "Radish", "Spinach", "Leek", "Potato", "Broccoli", "Turnip", "Arugula", "Kale", "Artichoke", "Beetroot", "Cabbage", "Fennel"};

    TTS textToSpeech;
    Vibrator v;

    View contentView;
    Log log = new Log();
    int numberOfInteractions;
    int numberOfLeftSwipes;
    int numberOfRightSwipes;
    int numberOfClicks;
    int previousIndex = -1;
    int currentIndex = -1;
    long t1, t2;
    String userid = null;
    String previousElement = null;
    String levelOneElement = null;

    HashMap<String, String[]> map;
    HashMap<String, Integer> mapElementToIndex;
    HashMap<String, HashMap<String, Integer>> mapSingleElement;

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

        setContentView(R.layout.activity_screen_d);

        map = new HashMap<>();
        map.put("Fruits", fruits);
        map.put("Cars", cars);
        map.put("Colors", colors);
        map.put("Countries", countries);
        map.put("Vegetables", vegetables);

        userid = getIntent().getExtras().getString("UserID");
        mapElementToIndex = new HashMap<>();
        mapElementToIndex.put("Cars", 0);
        mapElementToIndex.put("Countries", 1);
        mapElementToIndex.put("Colors", 2);
        mapElementToIndex.put("Fruits", 3);
        mapElementToIndex.put("Vegetables", 4);

        mapSingleElement = new HashMap<String, HashMap<String, Integer>>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String level_1 = entry.getKey();
            String[] list = entry.getValue();
            HashMap<String, Integer> mapped = new HashMap<>();
            for (int i = 0; i < list.length; ++i) {
                mapped.put(list[i], i);
            }
            mapSingleElement.put(level_1, mapped);
        }

        lv = findViewById(R.id.screenD_lv);
        lv1 = lv.findViewById(R.id.screenD_lv1);
        lv1_1 = lv1.findViewById(R.id.screenD_lv1_1);
        lv1_2 = lv1.findViewById(R.id.screenD_lv1_2);

        TextView tv = (TextView) lv1_1.getChildAt(0);
        System.out.println("Textview is " + tv.getText());
        tv.requestFocus();
        tv.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);

        System.out.println("focus " + getCurrentFocus());
        contentView = findViewById(android.R.id.content);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
                System.out.println(event);
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    String el = event.getText().toString().substring(1);
                    String element = el.substring(0, el.length() - 1);
                    System.out.println(element + " " + previousElement + " " + previousIndex);
                    if (element.equals(""))
                        return super.onRequestSendAccessibilityEvent(host, child, event);
                    if (previousIndex == -1 && mapElementToIndex.containsKey(element)) {
                        previousIndex = 0;
                        previousElement = element;
                    } else if (mapElementToIndex.containsKey(element)) {
                        currentIndex = mapElementToIndex.get(element);
                        previousIndex = mapElementToIndex.get(previousElement);
                        if (previousIndex != currentIndex) {
                            if (currentIndex == previousIndex + 1) {
                                numberOfRightSwipes++;
                                log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Right " + "Item selected: " + element);
                            } else if (currentIndex == previousIndex - 1) {
                                numberOfLeftSwipes++;
                                log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Left " + "Item selected: " + element);
                            }
                            numberOfInteractions++;
                            previousElement = element;
                        }
                    } else {
                        if (previousIndex == -1) {
                            previousIndex = 0;
                            previousElement = element;
                        } else {
                            currentIndex = mapSingleElement.get(levelOneElement).get(element);
                            previousIndex = mapSingleElement.get(levelOneElement).get(previousElement);
                            String target = returnCorrectTargetTalkback(ScreenD_10.this.getLocalClassName());
                            TextView tv = (TextView) lv1_2.getChildAt(currentIndex);
                            if(tv != null && tv.getText().equals(target)){
                                tv.setBackgroundResource(R.color.green);
                            }
                            tv = (TextView) lv1_2.getChildAt(previousIndex);
                            tv.setBackgroundResource(R.drawable.remove_border);
                            if (previousIndex != currentIndex) {
                                if (currentIndex == previousIndex + 1) {
                                    numberOfRightSwipes++;
                                    log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Right " + "Item selected: " + element);
                                } else if (currentIndex == previousIndex - 1) {
                                    numberOfLeftSwipes++;
                                    log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Left " + "Item selected: " + element);
                                }
                                numberOfInteractions++;
                            }
                            previousElement = element;
                        }

                    }
                } else if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    String el = event.getText().toString().substring(1);
                    String element = el.substring(0, el.length() - 1);
                    if (mapElementToIndex.containsKey(element)) {
                        previousIndex = -1;
                        levelOneElement = element;
                        numberOfClicks++;
                        numberOfInteractions++;
                    }
                    log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Click " + "Item selected: " + tv.getText());
                    previousElement = element;
                }
                System.out.println("Number of Interactions: " + numberOfInteractions);
                return super.onRequestSendAccessibilityEvent(host, child, event);
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        System.out.println("onPostResume");

        Runnable task = new Runnable() {

            @Override
            public void run() {

                TextView tv = (TextView) lv1_1.getChildAt(0);
                System.out.println("Textview is " + tv.getText());
                tv.requestFocus();
                tv.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            }
        };

        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

        worker.schedule(task, 1, TimeUnit.SECONDS);

    }

    public void populate(View view) {
        TextView tv = (TextView) view;
        String list[] = map.get(tv.getText().toString());
        String target_2 = returnCorrectTargetTalkback(ScreenD_10.this.getLocalClassName());
        for (int i = 0; i < list.length; i++) {
            tv = (TextView) lv1_2.getChildAt(i);
            tv.setText(list[i]);
            String element = (String) tv.getText().toString();
            if(element.equals(target_2)){
                tv.setPaintFlags(tv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            }
        }

        tv = (TextView) lv1_2.getChildAt(0);
        tv.setFocusableInTouchMode(true);
        tv.requestFocus();
        System.out.println(tv.getText());
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.onPause();
        }
        super.onPause();
    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);
        numberOfClicks++;
        numberOfInteractions++;
        log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Hierarchical Menu Talkback Variation10 " + "Button clicked: Click " + "Item selected: " + tv.getText());
        Intent intent = new Intent(this, ScreenD_10.class);

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
            log.append2(userid, " Screen:Hierarchical Menu Talkback" + "," + "Variation:1" + "," +
                    "Number of interactions:" + numberOfInteractions + "," +
                    "Time taken:" + (t2 - t1) + "," + " Number of LeftSwipes:" + numberOfLeftSwipes + "," +
                    "Number of RightSwipes:" + numberOfRightSwipes + "," +
                    "Number of Clicks:" + numberOfClicks);
        }
    }


}