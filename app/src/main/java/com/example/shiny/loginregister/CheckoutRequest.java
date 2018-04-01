package com.example.shiny.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


//This class allows you to make a request to the register.php file on the server
//The extends allows for a response as a string
public class CheckoutRequest extends StringRequest {

    //Provides the URL
    private static final String CHECKOUT_REQUEST_URL = "http://clarkchargerslibrary.000webhostapp.com/checkout.php";
    //Creates a map
    private Map<String, String> params;

    //Creates a constructor that asks for certain fields
    public CheckoutRequest(String book1, int student_ID, String checkout1, Response.Listener<String> listener){
        //Pass some data to volley
        //(Uses POST to send data, Sends the URL, Gives the listener which informs when the activity has finished)
        super(Method.POST, CHECKOUT_REQUEST_URL, listener, null);

        //Create the params and a hashmap
        params = new HashMap<>();

        //Puts all the necessary fields. "" is used to convert integers into strings
        params.put("student_ID", student_ID + "");
        params.put("book1", book1);
        params.put("checkout1", checkout1);
    }

    @Override
    //When the request is executed, volley calls getParams, which returns the params which includes all the data
    public Map<String, String> getParams() {
        return params;
    }
}
