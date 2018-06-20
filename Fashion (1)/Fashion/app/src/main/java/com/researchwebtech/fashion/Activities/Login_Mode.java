package com.researchwebtech.fashion.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.researchwebtech.fashion.All_URls.Urls;
import com.researchwebtech.fashion.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Mode extends AppCompatActivity {

    RelativeLayout t_select_admin, t_select_user, t_select_create_account;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__mode);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        checkIds();
        allClicks();

    }

    private void checkIds() {
        t_select_admin = findViewById(R.id.t_select_admin);
        t_select_user = findViewById(R.id.t_select_user);
        t_select_create_account = findViewById(R.id.t_select_create_account);
    }

    private void allClicks() {

        t_select_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "1";
                editor.putString("mode", "admin");
                editor.commit();
                load_Mode_Api();


            }
        });
        t_select_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "2";
                editor.putString("mode", "user");
                editor.commit();
                load_Mode_Api();

            }
        });

        t_select_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "3";
                editor.putString("mode", "register_user");
                editor.commit();
                load_Mode_Api();

            }
        });
    }

    private void load_Mode_Api() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {


            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOGIN_MODE_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion
                            progressBar.setVisibility(View.INVISIBLE);

                            String responseAPI = response;

                            Log.e("LOGIN MODE RESPONSE", "" + responseAPI);


                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                String status_mode = obj.getString("status");

                                Log.e("status_mode", "" + status_mode);

                                if (status_mode.equals("1")) {
                                    Intent i = new Intent(Login_Mode.this, Login.class);
                                    startActivity(i);
                                    finish();
                                }
                                if (status_mode.equals("2")) {
                                    Intent i = new Intent(Login_Mode.this, Login.class);
                                    startActivity(i);
                                    finish();
                                }
                                if (status_mode.equals("3")) {
                                    Intent i = new Intent(Login_Mode.this, Registeration.class);
                                    startActivity(i);
                                    finish();
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
                    if (status.equals("1")) {
                        params.put("admin", "admin");
                    }
                    if (status.equals("2")) {
                        params.put("user", "user");
                    }
                    if (status.equals("3")) {
                        params.put("signup", "signup");
                    }
                    return params;
                }

            };


            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(this, "Plaese turn on your Internet connection ", Toast.LENGTH_SHORT).show();
        }
    }

}

