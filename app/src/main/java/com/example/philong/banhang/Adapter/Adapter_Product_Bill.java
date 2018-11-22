package com.example.philong.banhang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.R;

import java.util.ArrayList;

public class Adapter_Product_Bill extends RecyclerView.Adapter<Adapter_Product_Bill.ViewHolder>{
    ArrayList<Product> menuUpdatesBillArrayList;
    Context context;

    public Adapter_Product_Bill(ArrayList<Product> menuUpdatesArrayList, Context context) {
        this.menuUpdatesBillArrayList = menuUpdatesArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_view_row_main_bill, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txtName.setText(menuUpdatesBillArrayList.get(position).getName());
        holder.txtPrice.setText(String.valueOf(menuUpdatesBillArrayList.get(position).getPrice()));


        holder.textViewBillCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k= Integer.parseInt(holder.textViewBillSize.getText().toString())+1;
                holder.textViewBillSize.setText(String.valueOf(k));
            }
        });
        holder.textViewBillTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=Integer.parseInt(holder.textViewBillSize.getText().toString())-1;
                holder.textViewBillSize.setText(String.valueOf(k));
            }
        });
    }





    @Override
    public int getItemCount() {
        return menuUpdatesBillArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice, textViewBillTru,textViewBillCong,textViewBillSize;
        ImageView imageViewBillDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.text_view_item_name_bill);
            txtPrice=itemView.findViewById(R.id.text_view_item_price_bill);
            textViewBillTru=itemView.findViewById(R.id.textview_item_view_bill_tru);
            textViewBillSize=itemView.findViewById(R.id.textview_item_view_bill_size);
            textViewBillCong=itemView.findViewById(R.id.textview_item_view_bill_cong);
            imageViewBillDelete=itemView.findViewById(R.id.imageview_bill_delete);
        }

    }


}
