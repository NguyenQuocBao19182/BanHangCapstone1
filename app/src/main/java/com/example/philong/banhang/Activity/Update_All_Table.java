package com.example.philong.banhang.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.philong.banhang.Adapter.Adapter_Update_Table;
import com.example.philong.banhang.Objects.Table;
import com.example.philong.banhang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_All_Table extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Table> tableArrayList = new ArrayList<>();

    Adapter_Update_Table mAdapterTable;

    RecyclerView mRecyclerViewAddTable;



    String urlDeleteDataTable = "http://192.168.56.1:81/GraceCoffee/deleteTable.php";

    //khai báo 2 thuộc tính để setup grid layout
    RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_table);
        initView();
        getDataBaseTable(MainActivity.urlGetDataTable);
        setupRecyclerView();
    }

    void initView() {
        findViewById(R.id.button_update_table_add).setOnClickListener(this);
        mRecyclerViewAddTable = findViewById(R.id.recycler_view_add_table);
    }

    public void getDataBaseTable(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    //khi doc duoc json
                    public void onResponse(JSONArray response) {
                        tableArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                tableArrayList.add(new Table(
                                        object.getInt("ID"),//trùng với định nghĩa contructor của php $this->ID
                                        object.getString("Ten")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapterTable.notifyDataSetChanged();
                    }
                },
                //khi doc json bi loi
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_All_Table.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }

    public void deleteDataBaseTable(final int idban) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteDataTable, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//thực thi thành công quay về getdata
                if (response.trim().equals("success")) {
                    Toast.makeText(Update_All_Table.this, "XoaThanhCong", Toast.LENGTH_SHORT).show();
                    getDataBaseTable(MainActivity.urlGetDataTable);
                } else Toast.makeText(Update_All_Table.this, "loi xoa", Toast.LENGTH_SHORT).show();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_All_Table.this, "Loi xoa", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idTable", String.valueOf(idban));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void setupRecyclerView() {

        //Setup cấu hình cho recycler table
        mRecyclerViewLayoutManager = new GridLayoutManager(context, 5);
        mRecyclerViewAddTable.setHasFixedSize(true);
        mRecyclerViewAddTable.setLayoutManager(mRecyclerViewLayoutManager);

        //Setup gán adapter cho recycler table
        mAdapterTable = new Adapter_Update_Table(tableArrayList, getApplicationContext(), this);
        mRecyclerViewAddTable.setAdapter(mAdapterTable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_update_table_add:
                startActivity(new Intent(Update_All_Table.this, Update_All_Table_Insert.class));
                break;
        }
    }
}
