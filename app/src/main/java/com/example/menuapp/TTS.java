package com.example.menuapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener {
    public static boolean textToSpeechIsInitialized = false;
    TextToSpeech textToSpeech;
    Context context;
    MediaPlayer mp;
    Vibrator v;

    public void initialize(Context ttsScreen, Vibrator v) {
        textToSpeech = new TextToSpeech(ttsScreen, this);
        this.context = ttsScreen;
        mp = MediaPlayer.create(ttsScreen, R.raw.complete);
        this.v = v;
    }

    @Override
    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {
            textToSpeechIsInitialized = true;
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED){
                System.out.println((result== TextToSpeech.LANG_NOT_SUPPORTED)+" "+(result== TextToSpeech.LANG_MISSING_DATA));
                CharSequence text = "Language not supported";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } else {
            CharSequence text = "Initialization failed!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }



    public void speakTextView(TextView tv){

        if(TTS.textToSpeechIsInitialized) {

            textToSpeech.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

            long[] pattern = {0, 15, 10,15};
            v.vibrate(pattern, -1);
        }else{
            System.out.println("Not initialized yet");
        }
    }

    public void speakSelectedTextView(TextView tv){

        if(TTS.textToSpeechIsInitialized) {

            textToSpeech.speak(tv.getText().toString() + " is selected", TextToSpeech.QUEUE_FLUSH, null);

            long[] pattern = {0, 10, 20,30};
            v.vibrate(pattern, -1);
        }else{
            System.out.println("Not initialized yet");
        }
    }

    public void playErrorSound(){
        mp.start();
    }



}
