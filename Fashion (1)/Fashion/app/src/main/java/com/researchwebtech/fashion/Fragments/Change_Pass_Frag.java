package com.researchwebtech.fashion.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.researchwebtech.fashion.Activities.Home;
import com.researchwebtech.fashion.R;


public class Change_Pass_Frag extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button bt_user_submit;

    TextView toolbarText;
    ImageView search_icon;
    EditText ed_user_old_pass, ed_user_new_pass, ed_user_confirm_pass;

    public Change_Pass_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change__pass_, container, false);
        bt_user_submit = (Button) view.findViewById(R.id.bt_user_submit);
        ed_user_new_pass = (EditText) view.findViewById(R.id.ed_user_new_pass);
        ed_user_old_pass = (EditText) view.findViewById(R.id.ed_user_old_pass);
        ed_user_confirm_pass = (EditText) view.findViewById(R.id.ed_user_confirm_pass);
        toolbarText = (TextView) ((Home) getActivity()).findViewById(R.id.toolbar_text);
        search_icon = (ImageView) ((Home) getActivity()).findViewById(R.id.search_icon);
        toolbarText.setText("Change Password");
        search_icon.setVisibility(View.GONE);
        pref = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        return view;


    }


    @Override
    public void onStart() {
        super.onStart();
        allClicks();
    }

    private void allClicks() {
        bt_user_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String old_pass = ed_user_old_pass.getText().toString();
                String confirm_pass = ed_user_confirm_pass.getText().toString();
                String new_pass = ed_user_new_pass.getText().toString();

                if (old_pass.isEmpty()) {
                    ed_user_old_pass.setError("Password cannot be Empty");
                } else if (new_pass.isEmpty()) {
                    ed_user_new_pass.setError("Password cannot be Empty");
                } else if (confirm_pass.isEmpty() || !confirm_pass.equals(new_pass) || confirm_pass.equals(new_pass)) {

                    if (confirm_pass.isEmpty()) {
                        ed_user_confirm_pass.setError("Password cannot be empty");
                    } else if (!confirm_pass.equals(new_pass)) {
                        ed_user_confirm_pass.setError("your password does not matches");
                    } else if (confirm_pass.equals(new_pass)){
                        editor.putString("pass", new_pass);
                        editor.commit();

                        Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}
