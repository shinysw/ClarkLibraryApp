package com.example.shiny.loginregister;


import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tells which .xml file to use
        setContentView(R.layout.activity_account);

        //Variable initialization with the widgets, final makes it so that the variable cannot be changed
        final TextView tvbook1 = (TextView) findViewById(R.id.tvbook1);
        final TextView tvcheckout1 = (TextView) findViewById(R.id.tvcheckout1);
        final Button bReturn = (Button) findViewById(R.id.bReturn);


        //Creates an intent to get the values from the LoginRequest
        Intent intent = getIntent();

        //Retrieves the values from UserAreaActivity
        final int student_ID = intent.getIntExtra("student_ID", -1);

        //Creates the response listener
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Converts the string into JSON object
                    JSONObject jsonResponse = new JSONObject(response);

                    //Used to check if the login was successful or not
                    boolean success = jsonResponse.getBoolean("success");

                    if (success){
                        //Takes info from the database
                        String book1 = jsonResponse.getString("book1");
                        String checkout1 = jsonResponse.getString("checkout1");
                        if (Objects.equals("", book1)) {
                            tvbook1.setText("No current books. Go check some out! :)");
                            tvcheckout1.setText("None");
                        }
                        else{
                            tvbook1.setText(book1);
                            tvcheckout1.setText(checkout1);
                        }

                    }else{
                        //Error message if Login fails
                        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                        builder.setMessage("Failed to retrieve books.")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //Passes the fields
        AccountRequest AccountRequest = new AccountRequest(student_ID, responseListener);

        //Adds this to the queue
        RequestQueue queue = Volley.newRequestQueue(AccountActivity.this);
        queue.add(AccountRequest);

        //Listens for a click on the login button, and begins communication with the SQL database
        bReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Creates the response listener
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Converts the string into JSON object
                            JSONObject jsonResponse = new JSONObject(response);

                            //Used to check if the login was successful or not
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                                builder.setMessage("Books Successfully Returned!")
                                        .setNegativeButton("Ok", null)
                                        .create()
                                        .show();
                                tvbook1.setText("No current books. Go check some out! :)");
                                tvcheckout1.setText("None");

                            }else{
                                //Error message if Login fails
                                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                                builder.setMessage("Return Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //Passes the fields
                ReturnRequest returnRequest = new ReturnRequest(student_ID, responseListener);

                //Adds this to the queue
                RequestQueue queue = Volley.newRequestQueue(AccountActivity.this);
                queue.add(returnRequest);
            }
        });



    }



}
