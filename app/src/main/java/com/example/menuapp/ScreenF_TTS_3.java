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

public class ScreenF_TTS_3 extends BaseActivity {

    LinearLayout lv, lv1, lv2, lv3, lv4;

    int curIndex = 0;
    boolean isInitial = true;
    TextView tv;
    TTS textToSpeech;
    TextView text;
    String [] num ={"1","2","3","4","5","6","7","8","9","0","$","!","~","&","=","[","]","{","}","Caps",".","_","-","+","/","*","%","Del","ABC"};
    String [] alphabets = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "Caps", "z", "x", "c", "v", "b", "n", "m","Del","Numbers"};
    Vibrator v;
    boolean isNum;
    boolean isCaps;
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

        isCaps = false;
        isNum = false;
        t1 =new Date().getTime();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        textToSpeech = new TTS();
        textToSpeech.initialize(this, v);

        setContentView(R.layout.activity_screen_f_tts);

        lv = findViewById(R.id.screenF_lv);

        lv1 = lv.findViewById(R.id.screenF_lv1);
        lv2 = lv.findViewById(R.id.screenF_lv2);
        lv3 = lv.findViewById(R.id.screenF_lv3);
        lv4 = lv.findViewById(R.id.screenF_lv4);
        text = lv.findViewById(R.id.textView);

        totalElements = lv1.getChildCount() + lv2.getChildCount() + lv3.getChildCount() + lv4.getChildCount()  ;

        userid = getIntent().getExtras().getString("UserID");

        IntentFilter sbu_filter = new IntentFilter();
        sbu_filter.addAction(SBU_ACTION);
        registerReceiver(sbu_receiver, sbu_filter);
        initial();

        String classname = this.getLocalClassName();
        System.out.println("classname: "+classname);
        int temp_idx = Integer.parseInt(classname.substring(12));
        System.out.println("id is : " +temp_idx);

    }

    public void initial(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                curIndex = 0;
                TextView tv = getTextView();
                System.out.println(tv);
                textToSpeech.speakTextView(tv,isCaps);
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
        if (isInitial) {
            TextView tv = getTextView();
            textToSpeech.speakTextView(tv,isCaps);
            curIndex = totalElements;
            isInitial = false;
        }

        if (curIndex > 0) {
            TextView original = getTextView();
            if(original != null) {
                original.setBackgroundResource(R.drawable.rounded_corner);
            }
            curIndex = curIndex - 1;
            tv = getTextView();
            textToSpeech.speakTextView(tv,isCaps);
            tv.setBackgroundResource(R.drawable.rounded_corner_border);
            System.out.println("goLeft" + curIndex + " " + tv.getText());
            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen:  Grid Menu Dial Variation3 " + "Button clicked: Left " + "Item selected: " + tv.getText());

        } else {
            curIndex = totalElements-1;
//            textToSpeech.playErrorSound();
            tv = getTextView();
            textToSpeech.speakTextView(tv,isCaps);
            System.out.println("goLeft " + curIndex + " Out of bounds");
            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen:  Grid Menu Dial Variation3 " + "Button clicked: Left " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.drawable.rounded_corner_bg_green);
        }
    }

    public void goRight(View view) {
        numberOfInteractions+=1;
        numberOfRightActions+=1;
        if (isInitial) {
            curIndex = 0;
            isInitial = false;
        }

        if (curIndex < totalElements - 1) {
            TextView original = getTextView();
            if(original != null) {
                original.setBackgroundResource(R.drawable.rounded_corner);
            }
            curIndex = curIndex + 1;
            tv = getTextView();

            textToSpeech.speakTextView(tv,isCaps);
            tv.setBackgroundResource(R.drawable.rounded_corner_border);
            System.out.println("goRight " + curIndex + " " + tv.getText());
            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen: Grid Menu Dial Variation3 " + "Button clicked: Right " + "Item selected: " + tv.getText());


        } else {
//            textToSpeech.playErrorSound();
            curIndex = 0;
            tv = getTextView();
            textToSpeech.speakTextView(tv,isCaps);
            System.out.println("goRight " + curIndex + " Out of bounds");
//
            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen:  Grid Menu Dial Variation3 " + "Button clicked: Right " + "Item selected: Out of bounds");
        }
        if(tv.getText().equals(target)){
            tv.setBackgroundResource(R.drawable.rounded_corner_bg_green);
        }
    }

    public void onClick(View view) {
        numberOfInteractions+=1;
        numberOfClicks+=1;
        if (!isInitial) {
            tv = (TextView) lv1.getChildAt(0);
            if (curIndex >= 0 && curIndex < totalElements) {
                tv = getTextView();
            } else if (curIndex < 0) {
                tv = (TextView) lv4.getChildAt(1);
            }  else {
                tv = (TextView) lv1.getChildAt(0);
            }

            textToSpeech.speakSelectedTextView(tv,isCaps);
            log.append(userid, "UserID: " + userid + " " + "Timestamp: " + new Date().getTime() + " " + " Screen:  Grid Menu Dial Variation3 " + "Button clicked: Click " + "Item selected: " + tv.getText());
            Intent intent = new Intent(this, ScreenF_TTS_4.class);

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
            System.out.println(tv.getText()+" "+tv.getText().length());
            if(tv.getText().equals(target)) {
                worker.schedule(task, 2, TimeUnit.SECONDS);
                t2 = new Date().getTime();
                log.append4(userid, "Screen:Grid Menu Dial, Variation:3, Target:"+target + ", Number of interactions:"+numberOfInteractions+
                        ", Time taken:"+(t2-t1)+", Number of Left rotations:"+numberOfLeftActions+
                        ", Number of Right rotations:"+numberOfRightActions+", Number of Clicks:"+
                        numberOfClicks+", Number of wrong clicks:"+numberOfWrongClicks+ ", Text:" + text.getText().toString() +";");
            }
            else if(tv != null && tv.getText().equals("Space")){
                // System.out.println("Space");
                text.append(" ");
            }
            else if(tv != null && tv.getText().equals("Del")){
                //System.out.println(text.getText().toString().length());
                if(text.getText().toString().length()>0) {
                    String s = text.getText().toString().substring(0, text.getText().toString().length() - 1);
                    text.setText(s);
                }
                else{
                    text.setText("");
                }
            }
            else if(tv != null && tv.getText().toString().equalsIgnoreCase("caps")){
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
            else if(tv != null && (tv.getText().equals("Numbers") || tv.getText().equals("ABC"))){
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
            else{
                text.append(!isCaps ? tv.getText() : tv.getText().toString().toUpperCase());
            }

        }
    }

    public TextView getTextView() {
        if (curIndex <= 9) {
            tv = (TextView) lv1.getChildAt(curIndex);
        } else if (curIndex <= 18) {
            tv = (TextView) lv2.getChildAt(curIndex % 10);
        } else if (curIndex <= 27) {
            tv = (TextView) lv3.getChildAt(curIndex % 19);
        } else if (curIndex <= 32) {
            tv = (TextView) lv4.getChildAt(curIndex % 28);
        }
        return tv;
    }

    public TextView getTargetTextView(int index) {
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