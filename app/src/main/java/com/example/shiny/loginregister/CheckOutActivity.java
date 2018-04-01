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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //Creates an intent to get the values from the UserAreaActivity
        Intent intent = getIntent();
        //Retrieves the values from the intent
        final int student_ID = intent.getIntExtra("student_ID", -1);

        //Obtains the current date
        final String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //Adds 7 days to the current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 7);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        final String checkout1 = sdf1.format(c.getTime());

        //Field for checking out a book
        final EditText etBook = (EditText) findViewById(R.id.etBook);
        final Button bConfirm = (Button) findViewById(R.id.bConfirm);

        //Listens for when the button is clicked
        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Puts the string inputted by the user in the textfield into a variable
                final String book1 = etBook.getText().toString();

                //Creates a response listener
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //Checks if the checkout was a success
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //If success, go to the My Books page
                                Intent intent = new Intent(CheckOutActivity.this, UserAreaActivity.class);
                                intent.putExtra("student_ID", student_ID);
                                CheckOutActivity.this.startActivity(intent);

                            } else {
                                //Error button if checkout fails
                                AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                                builder.setMessage("Checkout Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        //Catches an error if one occurs
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                CheckoutRequest checkoutRequest = new CheckoutRequest(book1, student_ID, checkout1, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CheckOutActivity.this);
                queue.add(checkoutRequest);
            }
        });
    }
}
