package com.researchwebtech.fashion.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.researchwebtech.fashion.Activities.Home;
import com.researchwebtech.fashion.Adapters.Home_User_Adapter;
import com.researchwebtech.fashion.Adapters.SlideAdapter;
import com.researchwebtech.fashion.Bean.Home_user_bean;
import com.researchwebtech.fashion.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class DashboardFragment extends Fragment {

    private List<Home_user_bean> home_user_beans = new ArrayList<>();
    private RecyclerView recyclerView;
    private Home_User_Adapter mAdapter;
    Context context;
    ImageView search_icon;
    TextView toolbarText;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.fashion3, R.drawable.fashion1, R.drawable.fashion2, R.drawable.fashion3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    CircleIndicator indicator;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        toolbarText = (TextView) ((Home)getActivity()).findViewById(R.id.toolbar_text);
        search_icon = (ImageView) ((Home)getActivity()).findViewById(R.id.search_icon);
        toolbarText.setText("Home");
        search_icon.setVisibility(View.VISIBLE);

        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        init();

        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        context = getActivity();
        mAdapter = new Home_User_Adapter(context, home_user_beans);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    private void init() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);


        mPager.setAdapter(new SlideAdapter(getActivity(), ImagesArray));

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;


        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
        }
}
