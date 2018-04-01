package com.example.shiny.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    //Initial setup with onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Allows code to run in background
        super.onCreate(savedInstanceState);
        //Calls the correct activity
        setContentView(R.layout.activity_login);

        //Variable initialization with widgets
        final EditText etStudentID = (EditText) findViewById(R.id.etStudentID);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView RegisterHere = (TextView) findViewById(R.id.tvRegisterHere);

        //Listens for a click on the register here text, and redirects the user to the register activity
        RegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creates an intent to open the RegisterActivity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                //Tells this page to execute the intent
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Listens for a click on the login button, and begins communication with the SQL database
        bLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Stores the data inputted in the text fields from student_ID and password
                final int student_ID = Integer.parseInt(etStudentID.getText().toString());
                final String password = etPassword.getText().toString();

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
                                String first_name = jsonResponse.getString("first_name");
                                String last_name = jsonResponse.getString("last_name");
                                int grade = jsonResponse.getInt("grade");

                                //Creates an intent to go to the UserAreaActivity page
                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);

                                //Passes info from the database to the UserAreaActivity page
                                intent.putExtra("first_name", first_name);
                                intent.putExtra("last_name", last_name);
                                intent.putExtra("grade", grade);
                                intent.putExtra("student_ID", student_ID);

                                //Goes to the UserAreaActivity page
                                LoginActivity.this.startActivity(intent);
                                finish();

                            }else{
                                //Error message if Login fails
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
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
                LoginRequest loginRequest = new LoginRequest(student_ID, password, responseListener);

                //Adds this to the queue
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
