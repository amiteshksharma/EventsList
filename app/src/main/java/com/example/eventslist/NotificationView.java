package com.example.eventslist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;

@SuppressLint("Registered")
public class NotificationView extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }
}