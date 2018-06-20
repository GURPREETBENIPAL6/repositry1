package com.researchwebtech.fashion.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
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

public class Registeration extends AppCompatActivity {

    EditText fname, lname, contact, register_email, register_password;
    Button register_btn;
    String first_name, last_name, phone, email1, pass;


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        findIds();
        clicks();
    }
///////Container Ids///////

    public void findIds() {
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        contact = (EditText) findViewById(R.id.contact);
        register_email = (EditText) findViewById(R.id.register_email);
        register_password = (EditText) findViewById(R.id.register_password);
        register_btn = (Button) findViewById(R.id.register_btn);

    }

    ////////Container Clicks/////////
    private void clicks() {
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                first_name = fname.getText().toString();
                last_name = lname.getText().toString();
                phone = contact.getText().toString();
                email1 = register_email.getText().toString();
                pass = register_password.getText().toString();
                if (first_name.isEmpty()) {
                    fname.setError("First name cannot be empty");
                } else if (last_name.isEmpty()) {
                    lname.setError("Last name cannot be empty");
                } else if (email1.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    if (email1.isEmpty()) {
                        register_email.setError("Email cannot be empty");
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                        register_email.setError("Please enter valid email Address");
                    }
                } else if (phone.isEmpty() || phone.length() <= 9) {
                    contact.setError("Enter Valid Phone Number");
                } else if (pass.isEmpty() || pass.length() <= 4) {
                    register_password.setError("Password length should less than 9 and greater than 5");
                } else {

                    editor.putString("fname", first_name);
                    editor.putString("lname", last_name);
                    editor.putString("phone", phone);
                    editor.putString("email", email1);
                    editor.putString("pass", pass);

                    registerApi();

                }
            }
        });
    }

    private void registerApi() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.register_progress);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion
                            progressBar.setVisibility(View.INVISIBLE);

                            String responseAPI = response;

                            Log.e("REGISTER RESPONSE", "" + responseAPI);


                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);
                                String msg = obj.getString("msg");
                                String res = obj.getString("response");


                                Log.e("RESPONSE", "" + res);
                                Log.e("MSG", "" + msg);

                                if (res.equals("success")) {
                                    Intent i = new Intent(Registeration.this, Home.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Toast.makeText(Registeration.this, "" + msg, Toast.LENGTH_SHORT).show();
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

                    params.put("first_name", first_name);
                    params.put("last_name", last_name);
                    params.put("email", email1);
                    params.put("phone", phone);
                    params.put("password", pass);

                    return params;
                }

            };

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(this, "Please Turn on your internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
