package com.researchwebtech.fashion.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchwebtech.fashion.Activities.Home;
import com.researchwebtech.fashion.Adapters.Home_admin_adapter;
import com.researchwebtech.fashion.Bean.Home_user_bean;
import com.researchwebtech.fashion.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_Home_Frag extends Fragment {

    private List<Home_user_bean> home_user_beans = new ArrayList<>();
    private RecyclerView home_admin_recycler_view;
    private Home_admin_adapter home_admin_adapter;
    Context context;
    ImageView search_icon;
    TextView toolbar_text;

    public Admin_Home_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__home_, container, false);


        home_admin_recycler_view = (RecyclerView) view.findViewById(R.id.home_admin_recycler_view);
        home_admin_adapter = new Home_admin_adapter(context, home_user_beans);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        home_admin_recycler_view.setLayoutManager(mLayoutManager);
        home_admin_recycler_view.setItemAnimator(new DefaultItemAnimator());
        home_admin_recycler_view.setAdapter(home_admin_adapter);
        search_icon = (ImageView) ((Home) getActivity()).findViewById(R.id.search_icon);
        toolbar_text = (TextView) ((Home) getActivity()).findViewById(R.id.toolbar_text);
        search_icon.setVisibility(View.VISIBLE);
        toolbar_text.setText("Home");
        return view;
    }


}
