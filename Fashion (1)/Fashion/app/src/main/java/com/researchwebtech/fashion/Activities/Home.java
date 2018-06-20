package com.researchwebtech.fashion.Activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.researchwebtech.fashion.Adapters.SlideAdapter;
import com.researchwebtech.fashion.All_URls.Urls;
import com.researchwebtech.fashion.Fragments.Admin_Home_Frag;
import com.researchwebtech.fashion.Fragments.Admin_addItem_frag;
import com.researchwebtech.fashion.Fragments.Admin_editDelete_frag;
import com.researchwebtech.fashion.Fragments.Change_Pass_Frag;
import com.researchwebtech.fashion.Fragments.DashboardFragment;
import com.researchwebtech.fashion.Fragments.User_profile_Frag;
import com.researchwebtech.fashion.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    boolean doubleBackToExitPressedOnce = false;

    ImageView search_icon, shopping_cart, drawer_menu;
    TextView t__drawer_logout_admin;
    DrawerLayout drawer_layout;
    RelativeLayout search_layout, rv_drawer_editdelete_item, rv_drawer_admin_home, rv_drawer_admin_add_item, t__drawer_logout_user, r_drawer_update, drawer_user_home, r_drawer_change_passpard;
    LinearLayout admin_panel, user_panel;
    EditText search_text;
    String findMode;
    String firstname, lastname, phonenumber, emailaddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment(), "DASHBOARD FRAGMENT").addToBackStack(null).commit();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        checkIds();
        drawerSet();
        allClicks();
        drawerItemClicks();
    }

    private void checkIds() {

        search_icon = findViewById(R.id.search_icon);
        drawer_menu = findViewById(R.id.drawer_menu);
        drawer_layout = findViewById(R.id.drawer_layout);
        admin_panel = findViewById(R.id.admin_panel);
        user_panel = findViewById(R.id.user_panel);
        r_drawer_update = findViewById(R.id.r_drawer_update);
        t__drawer_logout_admin = findViewById(R.id.t__drawer_logout_admin);
        t__drawer_logout_user = findViewById(R.id.t__drawer_logout_user);
        drawer_user_home = findViewById(R.id.drawer_user_home);
        rv_drawer_editdelete_item = findViewById(R.id.rv_drawer_editdelete_item);
        rv_drawer_admin_home = findViewById(R.id.rv_drawer_admin_home);
        rv_drawer_admin_add_item = findViewById(R.id.rv_drawer_admin_add_item);
        r_drawer_change_passpard = findViewById(R.id.r_drawer_change_passpard);

        findMode = pref.getString("mode", null);

    }


    //********* all clicks are present here********//
    private void allClicks() {
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_layout.setBackgroundColor(getResources().getColor(R.color.white));
                search_text.setVisibility(View.VISIBLE);
            }
        });
        drawer_menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer_layout.openDrawer(Gravity.START);

                    }
                });
    }

    //****** to set the Mode if login by administrartor thn will set the admin Drawer othervise viseversa *****//
    private void drawerSet() {

        Log.e("FIND MODE", "" + findMode);
        if (findMode.equals("admin")) {

            admin_panel.setVisibility(View.VISIBLE);
            user_panel.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Admin_Home_Frag()).addToBackStack(null).commit();
        }
        if (findMode.equals("user")) {

            user_panel.setVisibility(View.VISIBLE);
            admin_panel.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment(), "DashboardFragment").addToBackStack(null).commit();
        }

        if (findMode.equals("register_user")) {

            user_panel.setVisibility(View.VISIBLE);
            admin_panel.setVisibility(View.GONE);
        }
    }

    private void alertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Exit ?");


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                logoutApi();


                // Write your code here to invoke YES event
               /* Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Home.this, Login_Mode.class);
                startActivity(i);
                finish();*/
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void drawerItemClicks() {
        r_drawer_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new User_profile_Frag(), "User_Profile_frag").addToBackStack(null).commit();
                viewProfileApi();
                drawer_layout.closeDrawers();


            }
        });

        drawer_user_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment(), "DASHBOARDFRAGMent").addToBackStack(null).commit();
                drawer_layout.closeDrawers();
            }
        });

        r_drawer_change_passpard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Change_Pass_Frag(), "Change_password_fragment").addToBackStack(null).commit();
                drawer_layout.closeDrawers();
            }
        });
        rv_drawer_admin_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Admin_Home_Frag(), "ADmin Home fragment").addToBackStack(null).commit();
                drawer_layout.closeDrawers();
            }
        });
        rv_drawer_admin_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Admin_addItem_frag(), "Admin add item fragmnet").addToBackStack(null).commit();
                drawer_layout.closeDrawers();
            }
        });
        rv_drawer_editdelete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Admin_editDelete_frag(), "Admin Edit delelte fragmnet").addToBackStack(null).commit();
                drawer_layout.closeDrawers();
            }
        });
        t__drawer_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
        t__drawer_logout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs

        //getFragmentManager().beginTransaction().replace(R.id.content_frame, new DashboardFragment()).addToBackStack(null).commit();
        finish();
    }

    /* @Override
     public void onBackPressed() {
         if (doubleBackToExitPressedOnce) {
             super.onBackPressed();
             return;
         }

         this.doubleBackToExitPressedOnce = true;

         Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

         new Handler().postDelayed(new Runnable() {

             @Override
             public void run() {
                 doubleBackToExitPressedOnce = false;
             }
         }, 2000);
     }
 */

    ////////////////////////////////////LOGOUT Api///////////////////////////////
    private void logoutApi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOGOUT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            String responseAPI = response;

                            Log.e("LOGOUT RESPONSE", "" + responseAPI);


                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                String msg = obj.getString("msg");
                                String res = obj.getString("response");

                                if (res.equals("success")) {

                                    Log.e("MSG", "" + msg);

                                    pref.edit().clear().commit();

                                    Intent i = new Intent(Home.this, Login_Mode.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(Home.this, "" + msg, Toast.LENGTH_SHORT).show();
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


            };


            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please turn on Your Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    ////////////////////////////VIEW PROFILE//////////
    private void viewProfileApi() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            String userId = pref.getString("id", null);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://cwpra.online/fashionworld/view_profile.php?id=" + userId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

//Log.e("USER ID   ",""+http://cwpra.online/fashionworld/view_profile.php?id=userId).to
                            String responseAPI = response;

                            Log.e("VIEW PROFILE RESPONSE", "" + responseAPI);


                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);

                                Log.e("OBJ", "" + obj);

                                String msg = obj.getString("msg");
                                String res = obj.getString("response");

                                if (res.equals("success")) {

                                    Log.e("MSG", "" + msg);

                                    Log.e("VIEW PROFILE RESPONSE", "" + response);

                                    firstname = obj.getString("first_name");
                                    lastname = obj.getString("last_name");
                                    phonenumber = obj.getString("phone");
                                    emailaddress = obj.getString("password");

                                    Bundle bundle = new Bundle();
                                    bundle.putString("firstname", firstname);
                                    bundle.putString("lastname", lastname);
                                    bundle.putString("phonenumber", phonenumber);
                                    bundle.putString("emailaddress", emailaddress);
                                    // set Fragmentclass Arguments
                                    User_profile_Frag user_profile_frag = new User_profile_Frag();
                                    user_profile_frag.setArguments(bundle);


                                    /*
                                     Intent i = new Intent(Home.this, Login_Mode.class);
                                    startActivity(i);
                                    finish();*/
                                } else {
                                    Toast.makeText(Home.this, "" + msg, Toast.LENGTH_SHORT).show();
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


            };


            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please turn on Your Internet connection", Toast.LENGTH_LONG).show();
        }
    }

}


