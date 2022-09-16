package com.example.menuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {


    public static final String SBU_ACTION = "sbuCustomGesture";
    public static final String EXTRA_SBU_ACTION = "sbuGestureAction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(getApplication());
        Spinner dropdown = findViewById(R.id.spinner_userId);
        String[] items = new String[20];
        for(int i = 0; i < 20; i++){
            items[i] = String.valueOf(i+1);
        }

        Intent intent = new Intent(this, DialService.class);
        System.out.println(intent);
        startService(intent);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= 30){
            if (!Environment.isExternalStorageManager()){
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }
    }


    public void taskView(View view){
        Spinner dropdown = findViewById(R.id.spinner_userId);
        String userId = dropdown.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, ScreenB.class);
        intent.putExtra("UserID", userId);
        startActivity(intent);
    }
}