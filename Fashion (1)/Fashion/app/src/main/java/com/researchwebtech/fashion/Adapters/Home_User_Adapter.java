package com.researchwebtech.fashion.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.researchwebtech.fashion.Bean.Home_user_bean;
import com.researchwebtech.fashion.R;

import java.util.List;

public class Home_User_Adapter extends RecyclerView.Adapter<Home_User_Adapter.MyViewHolder> {

    private List<Home_user_bean> item_list;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView t_user_home_item_name;
        ImageView i_user_home_item;

        public MyViewHolder(View view) {
            super(view);
            t_user_home_item_name = (TextView) view.findViewById(R.id.t_user_home_item_name);
            i_user_home_item = (ImageView) view.findViewById(R.id.i_user_home_item);

        }
    }


    public Home_User_Adapter(Context context,List<Home_user_bean> item_list) {
        this.item_list = item_list;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recyclarview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //Home_user_bean home_user_bean = item_list.get(position);
        //holder.t_user_home_item_name.setText(home_user_bean.getItem_name());

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
