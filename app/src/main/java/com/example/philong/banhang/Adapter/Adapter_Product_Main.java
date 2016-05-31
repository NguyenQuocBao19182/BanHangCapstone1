package com.example.philong.banhang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.R;

import java.util.ArrayList;

public class Adapter_Product_Main extends RecyclerView.Adapter<Adapter_Product_Main.ViewHolder>{
    ArrayList<Product> ProductArrayList;

    Context context;
    String mName;
    int mPrice;

    public Adapter_Product_Main(ArrayList<Product> productArrayList, Context context) {
        ProductArrayList = productArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_view_row_main_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTextViewName.setText(ProductArrayList.get(position).getName());
        holder.mTextViewPrice.setText(String.valueOf(ProductArrayList.get(position).getPrice()));

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mName =ProductArrayList.get(position).getName();
                mPrice =ProductArrayList.get(position).getPrice();


                    Product product=ProductArrayList.get(position);
                    Toast.makeText(context, mName +","+ mPrice, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent("intentBill");
                    intent.putExtra("databill",product); //bug

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ProductArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewName, mTextViewPrice;
        LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewName =itemView.findViewById(R.id.text_view_item_name_product);
            mTextViewPrice =itemView.findViewById(R.id.text_view_item_price_product);
            mLinearLayout=itemView.findViewById(R.id.linear_product);
        }

    }


}
