package com.researchwebtech.fashion.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchwebtech.fashion.Activities.Home;
import com.researchwebtech.fashion.R;

public class Admin_addItem_frag extends Fragment {

    ImageView search_icon;
    TextView toolbar_text;

    public Admin_addItem_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_add_item_frag, container, false);
        search_icon=(ImageView) ((Home)getActivity()).findViewById(R.id.search_icon);
        toolbar_text=(TextView) ((Home)getActivity()).findViewById(R.id.toolbar_text);
        search_icon.setVisibility(View.GONE);
        toolbar_text.setText("Add Item");

        return view;
    }


}
