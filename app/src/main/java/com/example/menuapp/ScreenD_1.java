package com.example.menuapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenD_1 extends BaseActivity {

    LinearLayout lv,lv1,lv1_1,lv1_2;
    String[] fruits = {"Mango", "Peach", "Banana", "Kiwi", "Apple", "Pear", "Plum", "Guava", "Grapes", "Strawberry", "Blueberry", "Orange", "Lychee", "Papaya", "Pineapple", "Watermelon"};
    String[] cars = {"Mercedes", "BMW", "Volkswagen", "Ferrari", "Lamborghini", "Audi", "Suzuki", "Cooper", "Toyota", "Nissan", "Tesla", "Kia", "Fiat", "Dodge", "Ford", "Subaru"};
    String[] colors = {"Blue", "Black", "White", "Pink", "Yellow", "Red", "Green", "Brown", "Gray", "Magenta", "Beige", "Maroon", "Purple", "Orange", "Violet", "Indigo"};
    String[] countries = {"USA", "UK", "India", "France", "Spain", "Japan", "China", "Indonesia", "Mexico", "Argentina", "Brazil", "Canada", "Australia", "Turkey", "Italy", "Ireland"};
    String[] vegetables = {"Eggplant", "Cucumber", "Corn", "Mushroom", "Radish", "Spinach", "Leek", "Potato", "Broccoli", "Turnip", "Arugula", "Kale", "Artichoke", "Beetroot", "Cabbage", "Fennel"};

    TTS textToSpeech;
    Vibrator v;

    HashMap<String, String[]> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_d);

        map = new HashMap<>();
        map.put("Fruits", fruits);
        map.put("Cars", cars);
        map.put("Colors", colors);
        map.put("Countries", countries);
        map.put("Vegetables",vegetables);

        lv = findViewById(R.id.screenD_lv);
        lv1 = lv.findViewById(R.id.screenD_lv1);
        lv1_1 = lv1.findViewById(R.id.screenD_lv1_1);
        lv1_2 = lv1.findViewById(R.id.screenD_lv1_2);

        TextView tv = (TextView) lv1_1.getChildAt(0);
        System.out.println("Textview is "+tv.getText());
        tv.requestFocus();
        tv.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);

        System.out.println("focus " + getCurrentFocus());

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        System.out.println("onPostResume");

        Runnable task = new Runnable() {

            @Override
            public void run() {

                TextView tv = (TextView) lv1_1.getChildAt(0);
                System.out.println("Textview is "+tv.getText());
                tv.requestFocus();
                tv.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            }
        };

        final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

        worker.schedule(task, 1, TimeUnit.SECONDS);

    }

    public void populate(View view){
        TextView tv = (TextView) view;
        String list[] = map.get(tv.getText().toString());

        for(int i=0; i< list.length;i++){
            tv = (TextView) lv1_2.getChildAt(i);
            tv.setText(list[i]);
        }

        tv = (TextView)  lv1_2.getChildAt(0);
        tv.setFocusableInTouchMode(true);
        tv.requestFocus();
        System.out.println(tv.getText());
    }

    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.onPause();
        }
        super.onPause();
    }

    public void onClick(View view) {
        TextView tv = (TextView) view;
        textToSpeech.speakSelectedTextView(tv);

        Intent intent = new Intent(this, ScreenD_2.class);

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



}