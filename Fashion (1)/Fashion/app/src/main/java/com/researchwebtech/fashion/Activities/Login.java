package com.researchwebtech.fashion.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText login_email, login_password;
    Button login, register1;
    TextView tv_forget_password;
    private String useremail;
    private String pass;
    public String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        findId();
        clicks();
    }

    ///////////////////////////////Container Ids//////////////////////////////
    private void findId() {
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login);
        register1 = (Button) findViewById(R.id.register1);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
    }

    /////////////Container Clicks////////////////
    private void clicks() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                useremail = login_email.getText().toString();
                pass = login_password.getText().toString();

                if (useremail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                    if (useremail.isEmpty()) {
                        login_email.setError("Email cannot be empty");
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                        login_email.setError("Please enter valid email Address");
                    }
                } else if (pass.isEmpty() || pass.length() <= 4) {
                    login_password.setError("Password length should less than 9 and greater than 5");
                } else {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("email", useremail);
                    editor.putString("pass", pass);

                    Log.e("GETEMAIL", "" + useremail);
                    Log.e("GETPASS", "" + pass);
                    editor.commit();
                    // get Mode of the user
                    String mode = pref.getString("mode", null);

                    Log.e("GET TUPE OF USER", "" + mode);

                    loginApi();


                }
            }
        });
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, Registeration.class);
                startActivity(i);
                finish();
            }
        });

        tv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Forget.class);
                startActivity(i);
                finish();
            }
        });


    }

    ////******API HIT*****//////
    private void loginApi() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {


            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOGIN_URL,
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


                                        id=obj.getString("id");

                                        Log.e("ID",""+id);

                                        editor.putString("id",id);
                                        editor.commit();

                                    Intent i = new Intent(Login.this, Home.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "" + msg, Toast.LENGTH_SHORT).show();
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

                    params.put("email", useremail);
                    params.put("password", pass);

                    return params;
                }

            };
            ;

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
