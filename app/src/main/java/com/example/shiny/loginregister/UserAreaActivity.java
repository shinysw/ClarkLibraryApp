package com.example.shiny.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etGrade = (EditText) findViewById(R.id.etGrade);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final Button bCheckOut = (Button) findViewById(R.id.bCheckOut);
        final Button bAccount = (Button) findViewById(R.id.bAccount);
        final Button bInfo = (Button) findViewById(R.id.bInfo);

        //Creates an intent to get the values from the LoginRequest
        Intent intent = getIntent();

        //Retrieves the values from LoginActivity
        String first_name = intent.getStringExtra("first_name");
        String last_name = intent.getStringExtra("last_name");
        final int student_ID = intent.getIntExtra("student_ID", -1);

        //Default values in case user wants to view the app as a guest
        if(first_name != null && !first_name.isEmpty()) {
        }else{
            first_name = "Clark";
        }

        if(last_name != null && !last_name.isEmpty()) {
        }else{
            last_name = "Guest";
        }

        //Displays the welcome message
        String message = "Welcome " + first_name + " " + last_name + "!";
        welcomeMsg.setText(message);

        bInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserAreaActivity.this, InfoActivity.class);
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });

        bAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserAreaActivity.this, AccountActivity.class);
                registerIntent.putExtra("student_ID", student_ID);
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });

        bCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserAreaActivity.this, CheckOutActivity.class);
                registerIntent.putExtra("student_ID", student_ID);
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });



    }
}
