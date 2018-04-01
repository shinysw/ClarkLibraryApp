package com.example.shiny.loginregister;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tells which .xml file to use
        setContentView(R.layout.activity_register);

        //Variable initialization with the widgets, final makes it so that the variable cannot be changed
        //FindView finds which field to collect from
        final EditText etStudentID = (EditText) findViewById(R.id.etStudentID);
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etGrade = (EditText) findViewById(R.id.etGrade);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        //For the register button
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        //Adds a listener to the register button
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets all the text from the fields obtained in the EditText fields
                //For the numerical fields, it converts the strings into ints
                final int student_ID = Integer.parseInt(etStudentID.getText().toString());
                final String first_name = etFirstName.getText().toString();
                final String last_name = etLastName.getText().toString();
                final int grade = Integer.parseInt(etGrade.getText().toString());
                final String password = etPassword.getText().toString();

                //Creates a response listener
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    //String response is the response obtained from the php file
                    public void  onResponse(String response)
                    {
                        try {
                            //Converts the string into a JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            //Gets the success field from the response
                            boolean success = jsonResponse.getBoolean("success");

                            //Checks if the registration is successful
                            if (success){
                                //If successful, creates an intent to transfer the user to the Login page
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            }else {
                                //Else it tells the user that the Register Failed
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //Message that tells the user Register Failed
                                builder.setMessage("Register Failed")
                                        //Creates and shows a button
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        //Catches the error if something goes wrong
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //Creates a request and passes
                RegisterRequest registerRequest = new RegisterRequest(student_ID, first_name, last_name, grade, password, responseListener);

                //Creates and adds the queue the volley
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
