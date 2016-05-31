package com.example.philong.banhang.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.philong.banhang.Activity.MainActivity;
import com.example.philong.banhang.Objects.Table;
import com.example.philong.banhang.Interface.OnAdapterItemClick;
import com.example.philong.banhang.R;

import java.util.ArrayList;

public class Adapter_Table extends RecyclerView.Adapter<Adapter_Table.ViewHolder>{
    ArrayList<Table> menuUpdatesTableArrayList;
    Context context;
    MainActivity mMainActivity;
    OnAdapterItemClick onAdapterItemClick;

    public Adapter_Table(ArrayList<Table> menuUpdatesTableArrayList, Context context, MainActivity mMainActivity, OnAdapterItemClick onAdapterItemClick) {
        this.menuUpdatesTableArrayList = menuUpdatesTableArrayList;
        this.context = context;
        this.mMainActivity = mMainActivity;
        this.onAdapterItemClick = onAdapterItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_view_row_table, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //su ly mau icon table
        final Drawable notFreeTable =
                ContextCompat.getDrawable(mMainActivity,R.drawable.tablenotfree);
        final Drawable freeTable=
                ContextCompat.getDrawable(mMainActivity,R.drawable.a1);
        holder.mButtonNameTable.setText(menuUpdatesTableArrayList.get(position).getName());


        holder.mButtonNameTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAdapterItemClick.onItemClick(position);

                holder.mButtonNameTable.setBackground(notFreeTable);


            }
        });
    }

    @Override
    public int getItemCount() {
        return menuUpdatesTableArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button  mButtonNameTable;


        public ViewHolder(View itemView) {
            super(itemView);

            mButtonNameTable=itemView.findViewById(R.id.text_view_item_name_table);

        }

    }


}
