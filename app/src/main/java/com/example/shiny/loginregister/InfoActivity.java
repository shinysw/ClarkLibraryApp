package com.example.shiny.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Not much here, all the info is given through the xml file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
}
