package com.voda.voda_admin;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter_Menu extends RecyclerView.Adapter<RecyclerViewAdapter_Menu.MyViewHolder> {
    private ArrayList<Menu> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_category,tv_name,tv_tag,tv_explanation;
        private ImageView iv_picture;
        private ImageButton ib_settings;
        private ToggleButton tb_sold_out;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_category = itemView.findViewById(R.id.item_menu_category);
            this.tv_name = itemView.findViewById(R.id.item_menu_name);
            this.tv_tag = itemView.findViewById(R.id.item_menu_tag);
            this.tv_explanation = itemView.findViewById(R.id.item_menu_explanation);
            this.iv_picture = itemView.findViewById(R.id.item_menu_picture);
            this.ib_settings = itemView.findViewById(R.id.item_menu_settings);
            this.tb_sold_out = itemView.findViewById(R.id.item_menu_toggle);

            this.tb_sold_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            this.ib_settings.setOnClickListener(new View.OnClickListener() {
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
        if (mList.isEmpty()){
            Menu menu = new Menu();
            menu = mList.get(position);
            if(menu!=null){
                holder.tv_name.setText(menu.getName());
                holder.tv_category.setText(menu.getCategory());
                holder.tv_explanation.setText(menu.getExplanation());
                holder.tv_tag.setText(menu.getTag());
                holder.iv_picture.setImageURI(Uri.parse(menu.getImageurl()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
