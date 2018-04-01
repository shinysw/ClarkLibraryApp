package com.example.shiny.loginregister;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiny on 3/28/2018.
 */

//This class allows you to make a request to the register.php file on the server
//The extends allows for a response as a string
public class ReturnRequest extends StringRequest {

    //Provides the URL
    private static final String RETURN_REQUEST_URL = "http://clarkchargerslibrary.000webhostapp.com/return.php";
    private Map<String, String> params;

    public ReturnRequest(int student_ID, Response.Listener<String> listener){
        super(Request.Method.POST, RETURN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("student_ID", student_ID + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}