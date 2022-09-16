package com.example.menuapp;

import android.app.Activity;

public class BaseActivity extends Activity {
    String[] orderForC = new String[]{"Mango", "Banana", "Guava", "Pear", "Lychee",
            "Orange", "Kiwi", "Pineapple", "Blueberry", "Peach"};
    String[] orderForD = new String[]{"Banana", "Papaya", "Guava", "Watermelon", "Lychee",
            "Mango", "Pear", "Kiwi", "Pineapple", "Peach"};
    String[] orderForE = new String[]{"Kiwi", "Cherry", "Apple", "Berry", "Melon",
            "Orange", "Grapes", "Banana", "Mango", "Pear"};

    public String returnCorrectTarget(String className) {
        char order = className.charAt(6);
        String Index = className.substring(12);
        int idx = Integer.parseInt(Index);
        if(order=='C'){
            return orderForC[idx-1];
        }
        else if(order=='D'){
            return  orderForD[idx-1];
        }
        else if(order=='E'){
            return  orderForE[idx-1];
        }
        return "";
    }
    public String returnCorrectTargetTalkback(String className) {
        char order = className.charAt(6);
        String Index = className.substring(8);
        int idx = Integer.parseInt(Index);
        if(order=='C'){
            return orderForC[idx-1];
        }
        else if(order=='D'){
            return  orderForD[idx-1];
        }
        else if(order=='E'){
            return  orderForE[idx-1];
        }
        return "";
    }

}
