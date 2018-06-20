package com.researchwebtech.fashion.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.researchwebtech.fashion.Bean.Home_user_bean;
import com.researchwebtech.fashion.R;

import java.util.List;

public class Admin_EditDelete_adapter extends RecyclerView.Adapter<Admin_EditDelete_adapter.MyViewHolder> {

    private List<Home_user_bean> item_list;
    Activity context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Home_user_bean item);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView t_user_home_item_name;
        ImageView i_user_home_item;
        RelativeLayout rv_admin_editdelete_item, admin_edit;
        LinearLayout ll_admin_edit_delete;


        public MyViewHolder(View view) {
            super(view);

            rv_admin_editdelete_item = (RelativeLayout) view.findViewById(R.id.rv_admin_editdelete_item);
            ll_admin_edit_delete = (LinearLayout) view.findViewById(R.id.ll_admin_edit_delete);

        }

        public void bind(final Home_user_bean item, final OnItemClickListener listener) {
            t_user_home_item_name.setText(item.getItem_name());
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                    ll_admin_edit_delete.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    public Admin_EditDelete_adapter(Activity context, List<Home_user_bean> items, OnItemClickListener listener) {
        this.context = context;
        this.item_list = items;
        this.listener = listener;

    }

    @Override
    public Admin_EditDelete_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_edit_delete_item, parent, false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(Admin_EditDelete_adapter.MyViewHolder holder, int position) {
        //Home_user_bean home_user_bean = item_list.get(position);
        //holder.t_user_home_item_name.setText(home_user_bean.getItem_name());
        holder.rv_admin_editdelete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "hiiiiiiii", Toast.LENGTH_SHORT).show();

            }
        });
       // holder.bind(item_list.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return 18;
    }
}