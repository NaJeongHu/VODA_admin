package com.voda.voda_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voda.voda_admin.Model.Order;
import com.voda.voda_admin.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Order_Menus extends RecyclerView.Adapter<RecyclerViewAdapter_Order_Menus.MyViewHolder> {
    private String[] mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_num = itemView.findViewById(R.id.item_order_menus_num);
            this.tv_name = itemView.findViewById(R.id.item_order_menus_name);
        }
    }

    public RecyclerViewAdapter_Order_Menus(String[] list, Context context) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_order_menus, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String menu = mList[position];
        if (menu != null) {
            //임시
            String[] temp = menu.split(":", 2);
            holder.tv_name.setText(temp[0]);
            holder.tv_num.setText(temp[1]);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.length : 0);
    }

}
