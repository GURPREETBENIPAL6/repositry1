package com.researchwebtech.fashion.Fragments;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class User_profile_Frag extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView camera, i_user_edit;
    ImageView search_icon;
    TextView toolbarText;
    EditText ed_user_profile_fname, ed_user_profile_lname, ed_user_profile_phone;
    TextView tv_user_profile_email;
    Button b_user_profile_update;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int STORAGE_PERMISSION_CODE = 23;

    int REQUEST_CAMERA = 1;

    public User_profile_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_, container, false);
        camera = (ImageView) view.findViewById(R.id.camera);
        //i_user_edit = (ImageView) view.findViewById(R.id.i_user_edit);
        ed_user_profile_fname = (EditText) view.findViewById(R.id.ed_user_profile_fname);
        ed_user_profile_lname = (EditText) view.findViewById(R.id.ed_user_profile_lname);
        ed_user_profile_phone = (EditText) view.findViewById(R.id.ed_user_profile_phone);
        tv_user_profile_email = (TextView) view.findViewById(R.id.tv_user_profile_email);
        b_user_profile_update = (Button) view.findViewById(R.id.b_user_profile_update);
        toolbarText = (TextView) ((Home) getActivity()).findViewById(R.id.toolbar_text);
        search_icon = (ImageView) ((Home) getActivity()).findViewById(R.id.search_icon);
        toolbarText.setText("Update Profile");

        //search_icon.setVisibility(View.GONE);
        search_icon.setImageResource(R.drawable.edit_profile);
        search_icon.setVisibility(View.VISIBLE);
        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        allClicks();

        String First_name = pref.getString("fname", null);
        String last_name = pref.getString("lname", null);
        String phone = pref.getString("phone", null);
        ed_user_profile_fname.setText(First_name);
        ed_user_profile_lname.setText(last_name);
        ed_user_profile_phone.setText(phone);
        String email = pref.getString("email", null);


        tv_user_profile_email.setText(email);


        return view;
    }


    private void allClicks() {

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // selectImage();
            }
        });

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ed_user_profile_fname.requestFocus();
                ed_user_profile_lname.requestFocus();
                ed_user_profile_phone.requestFocus();
                b_user_profile_update.setVisibility(View.VISIBLE);


            }
        });
        b_user_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = ed_user_profile_fname.getText().toString();
                String lname = ed_user_profile_lname.getText().toString();
                String phone1 = ed_user_profile_phone.getText().toString();


                editor.putString("fname", fname);
                editor.putString("lname", lname);
                editor.putString("phone", phone1);
                editor.commit();

                Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   /* File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));*/
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                camera.setImageBitmap(image);
            }

            if (requestCode == 2) {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                    }
                    // Log.d(TAG, String.valueOf(bitmap));

                    camera.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}




