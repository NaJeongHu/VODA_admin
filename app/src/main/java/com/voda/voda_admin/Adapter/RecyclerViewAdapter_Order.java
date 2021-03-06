package com.voda.voda_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voda.voda_admin.Model.Order;
import com.voda.voda_admin.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Order extends RecyclerView.Adapter<RecyclerViewAdapter_Order.MyViewHolder> {
    private ArrayList<Order> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_num,tv_type,tv_time,tv_request;
        private RecyclerView recycle_item_order;
        private ImageView iv_glasses,iv_complete,iv_cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_num = itemView.findViewById(R.id.item_order_num);
            this.tv_type = itemView.findViewById(R.id.item_order_type);
            this.tv_time = itemView.findViewById(R.id.item_order_time);
            this.tv_request = itemView.findViewById(R.id.item_order_request);
            this.iv_glasses = itemView.findViewById(R.id.item_order_glasses);
            this.iv_complete = itemView.findViewById(R.id.item_order_complete);
            this.iv_cancel = itemView.findViewById(R.id.item_order_cancel);

            this.recycle_item_order = itemView.findViewById(R.id.recycle_item_order);

            this.iv_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            this.iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public RecyclerViewAdapter_Order(ArrayList<Order> list, Context context) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_order,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!mList.isEmpty()){
            Order order = mList.get(position);
            if(order!=null){
                //TODO : Order ????????? child?????? ?????? ????????? ????????? Binding ?????????
                //??????
                holder.tv_num.setText("???????????? "+(position+1)+"???");
                holder.tv_type.setText(order.getType());
                holder.tv_time.setText(order.getTime().toString());
                String request = order.getRequest();

                holder.tv_request.setText(order.getRequest());

                String menus = order.getMenus();
                String arrayMenus[] = menus.split("&");
                holder.recycle_item_order.setAdapter(new RecyclerViewAdapter_Order_Menus(arrayMenus, mContext));
                holder.recycle_item_order.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
                holder.recycle_item_order.setHasFixedSize(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    //?????? split?????? ???????????? ??????
    private void splitRequest(){

    }

}
