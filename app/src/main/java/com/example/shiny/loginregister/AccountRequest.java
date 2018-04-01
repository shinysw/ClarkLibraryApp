package com.example.shiny.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiny on 3/27/2018.
 */
//This class allows you to make a request to the register.php file on the server
//The extends allows for a response as a string
public class AccountRequest extends StringRequest {

    //Provides the URL
    private static final String ACCOUNT_REQUEST_URL = "http://clarkchargerslibrary.000webhostapp.com/account.php";

    //Creates a map
    private Map<String, String> params;

    //Creates a constructor that asks for certain fields
    public AccountRequest(int student_ID, Response.Listener<String> listener){
        //Pass some data to volley
        //(Uses POST to send data, Sends the URL, Gives the listener which informs when the activity has finished)
        super(Request.Method.POST, ACCOUNT_REQUEST_URL, listener, null);

        //Create the params and a hashmap
        params = new HashMap<>();

        //Puts all the necessary fields. "" is used to convert integers into strings
        params.put("student_ID", student_ID + "");
    }

    //When the request is executed, volley calls getParams, which returns the params which includes all the data
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}