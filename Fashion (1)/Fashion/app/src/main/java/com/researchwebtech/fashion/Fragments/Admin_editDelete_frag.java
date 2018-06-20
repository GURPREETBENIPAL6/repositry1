package com.researchwebtech.fashion.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
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
import com.researchwebtech.fashion.Adapters.Admin_EditDelete_adapter;
import com.researchwebtech.fashion.Bean.Home_user_bean;
import com.researchwebtech.fashion.R;

import java.util.List;


public class Admin_editDelete_frag extends Fragment {

    RecyclerView admin_edit_recyclar;
    Admin_EditDelete_adapter admin_editDelete_adapter;
    private Admin_EditDelete_adapter.OnItemClickListener listener;
    Context context;
    ImageView search_icon;
    TextView toolbar_text;
    private List<Home_user_bean> item_list;
    @SuppressLint("ValidFragment")
    public Admin_editDelete_frag() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_edit_frag, container, false);

        admin_edit_recyclar=(RecyclerView)view.findViewById(R.id.admin_edit_recyclar);
        admin_editDelete_adapter = new Admin_EditDelete_adapter(getActivity(),item_list,listener);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        admin_edit_recyclar.setLayoutManager(mLayoutManager);
        admin_edit_recyclar.setItemAnimator(new DefaultItemAnimator());
        admin_edit_recyclar.setAdapter(admin_editDelete_adapter);

        search_icon=(ImageView)((Home)getActivity()).findViewById(R.id.search_icon);
        toolbar_text=(TextView)((Home)getActivity()).findViewById(R.id.toolbar_text);
        search_icon.setVisibility(View.GONE);
        toolbar_text.setText("Edit or Delete Item");
        return view;
    }


}
