package com.voda.voda_admin.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voda.voda_admin.Activity.MenuActivity;
import com.voda.voda_admin.Model.Menu;
import com.voda.voda_admin.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Menu extends RecyclerView.Adapter<RecyclerViewAdapter_Menu.MyViewHolder> {
    private ArrayList<Menu> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_category,tv_name,tv_tag,tv_explanation,tv_price;
        private ImageView iv_picture,iv_settings;
        private Switch switch_sold_out;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_category = itemView.findViewById(R.id.item_menu_category);
            this.tv_name = itemView.findViewById(R.id.item_menu_name);
            this.tv_tag = itemView.findViewById(R.id.item_menu_tag);
            this.tv_explanation = itemView.findViewById(R.id.item_menu_explanation);
            this.tv_price = itemView.findViewById(R.id.item_menu_price);
            this.iv_picture = itemView.findViewById(R.id.item_menu_picture);
            this.iv_settings = itemView.findViewById(R.id.item_menu_settings);
            this.switch_sold_out = itemView.findViewById(R.id.item_menu_soldout);

            this.switch_sold_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 품절표시 처리
                }
            });

            this.iv_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public RecyclerViewAdapter_Menu(ArrayList<Menu> list, Context context) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_menu,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()){
            Menu menu = mList.get(position);
            if(menu!=null){
                holder.tv_name.setText(menu.getName());
                holder.tv_category.setText(menu.getCategory());
                holder.tv_explanation.setText(menu.getExplanation());
                holder.tv_price.setText(menu.getPrice()+"원");
                holder.tv_tag.setText(menu.getTag());
                if(menu.getImageurl()!=null) {
                    Glide.with(holder.itemView.getContext()).load(menu.getImageurl()).into(holder.iv_picture);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
