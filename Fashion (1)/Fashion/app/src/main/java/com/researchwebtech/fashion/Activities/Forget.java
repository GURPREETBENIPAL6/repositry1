package com.researchwebtech.fashion.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.researchwebtech.fashion.All_URls.Urls;
import com.researchwebtech.fashion.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forget extends AppCompatActivity {

    Button forget_submit;
    EditText forget_email;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        checkIds();
        clickListerners();


    }
    private void checkIds()
    {
        forget_submit=findViewById(R.id.forget_submit);
        forget_email=findViewById(R.id.forget_email);

    }
    private void clickListerners()
    {
        forget_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=forget_email.getText().toString();
                forgetApi();
            }
        });
    }
    private void forgetApi() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.forget_progress);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.FORGET_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion
                            progressBar.setVisibility(View.INVISIBLE);

                            String responseAPI = response;

                            Log.e("LOGIN RESPONSE", "" + responseAPI);


                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                String msg = obj.getString("msg");
                                String res = obj.getString("response");

                                Log.e("RESPONSE", "" + res);
                                if (res.equals("success")) {

                                    Intent i = new Intent(Forget.this, Login.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // Toast.makeText(Login.this, ""+msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", email);


                    return params;
                }

            };


            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(this, "Please turn on Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
