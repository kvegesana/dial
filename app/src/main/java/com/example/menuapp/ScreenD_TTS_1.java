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

import org.threeten.bp.LocalDateTime;

import java.time.Duration;
import org.threeten.bp.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenD_TTS_1 extends BaseActivity {

    LinearLayout lv,lv1,lv1_1,lv1_2;
    int curIndexList1 = 0;
    int curIndexList2 = 0;
    boolean isFirstMenu = true;
    Log log = new Log();
    int counter = 0;
    TextView tv;
    boolean outOfBounds = false;
    String[] fruits = {"Orange", "Lychee", "Guava", "Pear", "Mango", "Kiwi", "Strawberry", "Apple", "Blueberry", "Grapes", "Plum", "Peach", "Pineapple", "Papaya", "Banana", "Watermelon"};
    String[] cars = {"Ford","Volkswagen","BMW","Audi","Kia","Cooper","Suzuki","Nissan","Dodge","Ferrari","Tesla", "Lamborghini", "Fiat", "Toyota", "Mercedes", "Subaru"};
    String[] colors = {"Magenta", "Purple", "Green", "Red", "Violet", "Brown", "White", "Pink", "Indigo", "Blue", "Orange", "Maroon", "Black", "Beige", "Yellow", "Gray"};
    String[] countries = {"Argentina", "Australia", "USA", "Canada", "UK", "Japan", "China", "Turkey", "Mexico", "Ireland", "Italy", "Spain", "France", "India", "Brazil", "Indonesia"};
    String[] vegetables = {"Artichoke", "Cabbage", "Corn", "Spinach", "Potato", "Leek", "Radish", "Fennel", "Mushroom", "Turnip", "Cucumber", "Kale", "Arugula", "Beetroot", "Eggplant", "Broccoli"};

    TTS textToSpeech;
    Vibrator v;
    String userid = null;
    int numberOfWrongClicks, numberOfInteractions, numberOfLeftActions, numberOfRightActions, numberOfClicks, numberOfBackClicks;

    long t1,t2;
    String target;
    ScrollView sv;


    public static final String SBU_ACTION = "sbuCustomGesture";
    public static final String EXTRA_SBU_ACTION = "sbuGestureAction";

    private ArrayList<LocalDateTime> clicks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        target = returnCorrectTarget(this.getLocalClassName());
        numberOfInteractions = 0;
        numberOfClicks = 0;
        numberOfLeftActions = 0;
        numberOfRightActions = 0;
        numberOfBackClicks = 0;
        numberOfWrongClicks=0;

        t1 =new Date().getTime();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this,v);

        setContentView(R.layout.activity_screen_d_tts);

        lv = findViewById(R.id.screenD_lv);
        lv1 = lv.findViewById(R.id.screenD_lv1);
        lv1_1 = lv1.findViewById(R.id.screenD_lv1_1);
        lv1_2 = lv1.findViewById(R.id.screenD_lv1_2);

        userid = getIntent().getExtras().getString("UserID");

        IntentFilter sbu_filter = new IntentFilter();
        sbu_filter.addAction(SBU_ACTION);
        registerReceiver(sbu_receiver, sbu_filter);
        sv = findViewById(R.id.ScrollViewHierarchicalID);
        initial();


    }

    public void initial(){

        Runnable task = new Runnable() {
            @Override
            public void run() {
                curIndexList1 = 0;
                TextView tv = (TextView) lv1_1.getChildAt(curIndexList1);
                System.out.println("Initial screen D TTS" + tv.getText());
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


    public void populate(int index, String[] list, LinearLayout listView){
        String classname = this.getLocalClassName();
        System.out.println("classname: "+classname);
        int temp_idx = Integer.parseInt(classname.substring(12));
        System.out.println("id is : " +temp_idx);
        int target_idx = 0;

        switch(temp_idx){
            case 1:
                target_idx = 14;
                break;
            case 2 :
                target_idx = 13;
                break;
            case 3 :
                target_idx = 2;
                break;
            case 4 :
                target_idx = 15;
                break;
            case 5 :
                target_idx = 1;
                break;
            case 6 :
                target_idx = 4;
                break;
            case 7 :
                target_idx = 3;
                break;
            case 8 :
                target_idx = 5;
                break;
            case 9 :
                target_idx = 12;
                break;
            case 10 :
                target_idx = 11;
                break;
        }
        System.out.println(target_idx);
        TextView temp = (TextView) lv1_2.getChildAt(target_idx);
        if(index == 0) {
            temp.setPaintFlags(temp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
        else{
            temp.setPaintFlags(0);
        }

        if(index == 0){
            for(int i=0; i< list.length;i++){
                tv = (TextView) listView.getChildAt(i);
                tv.setText(fruits[i]);
            }
        }
        else if(index == 1){
            for(int i=0; i< list.length;i++){
                tv = (TextView) listView.getChildAt(i);
                tv.setText(colors[i]);
            }
        }
        else if(index == 2){
            for(int i=0; i< list.length;i++){
                tv = (TextView) listView.getChildAt(i);
                tv.setText(cars[i]);
            }
        }
        else if(index == 3) {
            for(int i=0; i< list.length;i++){
                tv = (TextView) listView.getChildAt(i);
                tv.setText(vegetables[i]);
            }
        }else if(index == 4){
            for(int i=0; i< list.length;i++){
                tv = (TextView) listView.getChildAt(i);
                tv.setText(countries[i]);
            }
        }
    }

    public void onClick(View view){
        numberOfInteractions+=1;
        numberOfClicks+=1;
        System.out.println("First Menu "+isFirstMenu);
        System.out.println("Counter "+counter);
        System.out.println(lv1_1.getChildCount() + " "+ lv1_2.getChildCount());
        System.out.println(curIndexList1 +" " +curIndexList2);

        if (isFirstMenu){
            clicks.clear();
            if (curIndexList1 == 0) {
                populate(0, fruits, lv1_2);
            } else if (curIndexList1 == 1) {
                populate(1, colors, lv1_2);
            } else if (curIndexList1 == 2) {
                populate(2, cars, lv1_2);
            } else if(curIndexList1 == 3) {
                populate(3, vegetables, lv1_2);
            }else if(curIndexList1 == 4){
                populate(4, countries, lv1_2);
            }else if(curIndexList1 < 0){
                populate(4, countries, lv1_2);
            }else{
                populate(0, fruits, lv1_2);
            }
            tv = (TextView) lv1_2.getChildAt(0);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
        }
        else {
            if(!outOfBounds) {
                if (curIndexList2 >= 0 && curIndexList2 < 16) {
                    tv = (TextView) lv1_2.getChildAt(curIndexList2);
                    System.out.println("Selected " + curIndexList2 + " " + tv.getText().toString());
                }
//                else if(curIndexList2 < 0){
//                    tv = (TextView) lv1_2.getChildAt(15);
//                }else{
//                    tv = (TextView) lv1_2.getChildAt(0);
//                }
       }
//            else{
//                if(curIndexList2 == 10) {
//                    tv = (TextView) lv1_2.getChildAt(0);
//                }
//                else {
//                    tv = (TextView) lv1_2.getChildAt(15);
//
//            }
            textToSpeech.speakSelectedTextView(tv);
            Intent intent = new Intent(this, ScreenD_TTS_2.class);

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
                worker.schedule(task, 2, TimeUnit.SECONDS);t2 = new Date().getTime();
                t2 = new Date().getTime();
                log.append2(userid, "Screen: Hierarchical Menu Dial, Variation:1, Target:"+target  + ", Number of interactions:"+numberOfInteractions+", Time taken:"+(t2-t1)+", Number of Left rotations:"+numberOfLeftActions+", Number of Right rotations:"+numberOfRightActions+", Number of Clicks:"+numberOfClicks+", Number of wrong clicks:"+numberOfWrongClicks+", Number of Back Clicks:"+numberOfBackClicks+";");}
            else {
                numberOfWrongClicks+=1;
                tv.setBackgroundResource(R.color.red);
            }
        }
        log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Click " + "Item selected: " + tv.getText());

        if(counter == 0){
            isFirstMenu = false;
            counter = 1;
        }

    }

    public void back(View view){
        sv.smoothScrollTo(0,0);
        numberOfInteractions+=1;
        numberOfBackClicks+=1;
        if(counter == 1){
            isFirstMenu = true;
            for(int i=0; i<16 ;i++){
                tv = (TextView) lv1_2.getChildAt(i);
                tv.setBackgroundResource(R.drawable.remove_border);
                tv.setText("");
            }
        }
        counter = 0;
        System.out.println("First Menu "+isFirstMenu);
        System.out.println("Counter "+counter);
        if (curIndexList1 >= 0 && curIndexList1 < lv1_1.getChildCount()) {
            TextView tv = (TextView) lv1_1.getChildAt(curIndexList1);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
        } else if (curIndexList1 < 0) {
            tv = (TextView) lv1_1.getChildAt(lv1_1.getChildCount() - 1);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
        } else {
            tv = (TextView) lv1_1.getChildAt(0);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
        }
        System.out.println("Selected " + curIndexList1 + " " + tv.getText().toString());
        log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() +" " +" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Back " + "Item selected: " + tv.getText());
        curIndexList2 = 0;
    }

    public void goLeft(View view) {
        numberOfInteractions+=1;
        numberOfLeftActions+=1;
        clicks.clear();
        if(isFirstMenu){

            if(curIndexList1 > 0) {
                TextView original = (TextView) lv1_1.getChildAt(curIndexList1);
                if(original != null) {
                    original.setBackgroundResource(R.drawable.remove_border);
                }
                curIndexList1 = curIndexList1 - 1;
            }else{
                //curIndexList1 = lv1_1.getChildCount() - 1;
            }
            TextView tv = (TextView) lv1_1.getChildAt(curIndexList1);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
            System.out.println("goLeft" + curIndexList1 + " " + tv.getText());
//            populate(curIndexList1,curIndexList1 == 0 ? fruits: curIndexList1 == 1 ? cars:curIndexList1 == 2 ? colors:countries,lv1_2);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Left " + "Item selected: " + tv.getText());
        }
        else {
            if(curIndexList2 > 0){
                TextView original = (TextView) lv1_2.getChildAt(curIndexList2);
                if(original != null) {
                    original.setBackgroundResource(R.drawable.remove_border);
                }
                curIndexList2 = curIndexList2 - 1;
                TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
                textToSpeech.speakTextView(tv);
                tv.setBackgroundResource(R.drawable.border);
                System.out.println("goLeft" + curIndexList2 + " " + tv.getText());
                log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Left " + "Item selected: " + tv.getText());

            }else{
//                textToSpeech.playErrorSound();
                TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
                textToSpeech.speakTextView(tv);
                System.out.println("goLeft " + curIndexList2 + " Out of bounds") ;
                //curIndexList2 = lv1_2.getChildCount();
                log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Left " + "Item selected: " + tv.getText());
            }
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ tv.getText());
            TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
            if(tv.getText().equals(target)){
                tv.setBackgroundResource(R.color.green);
            }
            if(curIndexList2 > 8){
                sv.smoothScrollTo(0, sv.getHeight());
            }
            if(curIndexList2 < 7) {
                sv.smoothScrollTo(0,0);
            }
        }
    }

    public void goRight(View view) {
        numberOfInteractions+=1;
        numberOfRightActions+=1;
        clicks.clear();
        if(isFirstMenu){

            if(curIndexList1 < lv1_1.getChildCount() - 1) {
                TextView original = (TextView) lv1_1.getChildAt(curIndexList1);
                if(original != null) {
                    original.setBackgroundResource(R.drawable.remove_border);
                }
                curIndexList1 = curIndexList1 + 1;
            }else{
                //curIndexList1 = 0;

            }
            TextView tv = (TextView) lv1_1.getChildAt(curIndexList1);
            textToSpeech.speakTextView(tv);
            tv.setBackgroundResource(R.drawable.border);
            System.out.println("goRight " + curIndexList1 + " " + tv.getText());
//            populate(curIndexList1,curIndexList1 == 0 ? fruits: curIndexList1 == 1 ? cars:curIndexList1 == 2 ? colors:countries,lv1_2);
            log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Right " + "Item selected: " + tv.getText());
        }
        else{

            if(curIndexList2 < 15){
                TextView original = (TextView) lv1_2.getChildAt(curIndexList2);
                if(original != null) {
                    original.setBackgroundResource(R.drawable.remove_border);
                }
                curIndexList2 = curIndexList2 + 1;
                TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
                textToSpeech.speakTextView(tv);
                tv.setBackgroundResource(R.drawable.border);
                System.out.println("goRight " + curIndexList2 + " " + tv.getText());
                log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime() + " "+" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Right " + "Item selected: " + tv.getText());
            }else{
//                textToSpeech.playErrorSound();
                TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
                textToSpeech.speakTextView(tv);
                System.out.println("goRight " + curIndexList2 + " Out of bounds");
               // curIndexList2 = -1;
                log.append(userid,"UserID: "+ userid+ " " + "Timestamp: " + new Date().getTime()+ " " +" Screen: Hierarchical Menu Dial Variation1 " + "Button clicked: Right " + "Item selected: Out of bounds");
            }
            TextView tv = (TextView) lv1_2.getChildAt(curIndexList2);
            if(tv.getText().equals(target)){
                tv.setBackgroundResource(R.color.green);
            }
            if(curIndexList2 > 8){
                sv.smoothScrollTo(0, sv.getHeight());
            }
            if(curIndexList2 < 7) {
                sv.smoothScrollTo(0,0);
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

                            LocalDateTime curTime = LocalDateTime.now();
                            Iterator<LocalDateTime> iter = clicks.iterator();

                            while (iter.hasNext()){
                                LocalDateTime time = iter.next();
                                System.out.println("Time " + time);
                                if(ChronoUnit.SECONDS.between(time, curTime) > 1) {
                                    iter.remove();
                                    System.out.println("Removing time =" + time);
                                }

                            }

                            clicks.add(curTime);

                            System.out.println("curList " + curIndexList2 + " size "+clicks.size());

                            if(clicks.size() >= 2) {
                                System.out.println("back");
                                back(view);
                                clicks.clear();
                            }
                            else{
                                System.out.println("onClick");
                                onClick(view);
                            }

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