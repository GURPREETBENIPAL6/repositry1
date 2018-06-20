package com.researchwebtech.fashion.Activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.researchwebtech.fashion.Fragments.Admin_Home_Frag;
import com.researchwebtech.fashion.R;

public class Admin_home extends AppCompatActivity {

DrawerLayout admin_drawer_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        checkIds();
        openfragments();

    }

    private void checkIds() {
            admin_drawer_layout=findViewById(R.id.admin_drawer_layout);
    }

    public void openfragments() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new Admin_Home_Frag(), "DashboardFragment").addToBackStack(null).commit();

    }
}
