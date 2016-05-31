package com.example.philong.banhang.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.philong.banhang.Adapter.Adapter_Product_Main;
import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.R;

import java.util.ArrayList;

public class AdapterTestMVP extends RecyclerView.Adapter<AdapterTestMVP.ViewHolder> {
    ArrayList<Product> mProductArrayListTestMVP;
    Context context;

    public AdapterTestMVP(ArrayList<Product> mProductArrayListTestMVP, Context context) {
        this.mProductArrayListTestMVP = mProductArrayListTestMVP;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_test_mvp, parent, false);
        AdapterTestMVP.ViewHolder viewHolder = new AdapterTestMVP.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mProductArrayListTestMVP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewNameTest, mTextViewPriceTest;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewNameTest = itemView.findViewById(R.id.item_name_product_test_mvp);
            mTextViewPriceTest = itemView.findViewById(R.id.item_price_product_test_mvp);
        }
    }
}
