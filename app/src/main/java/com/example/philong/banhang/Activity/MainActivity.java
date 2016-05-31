package com.example.philong.banhang.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.philong.banhang.Adapter.Adapter_Product_Main;
import com.example.philong.banhang.Adapter.Adapter_Table;
import com.example.philong.banhang.Adapter.Adapter_Product_Bill;
import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.Objects.Table;
import com.example.philong.banhang.Interface.OnAdapterItemClick;
import com.example.philong.banhang.R;
import com.example.philong.banhang.View.TestMVP;
import com.nex3z.notificationbadge.NotificationBadge;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int mCount = 0;

    //khai báo chuỗi kết nối
    static String urlGetDataTable = "http://192.168.56.1:81/GraceCoffee/getdataTable.php";
    static String urlGetDataProduct = "http://192.168.56.1:81/GraceCoffee/getdataProduct.php";
    static String urlGetDataProductCoffee = "http://192.168.56.1:81/GraceCoffee/getdataProductCoffee.php";
    static String urlGetDataProductCannedWater = "http://192.168.56.1:81/GraceCoffee/getdataProductCannedWater.php";
    static String urlGetDataProductBottledWater = "http://192.168.56.1:81/GraceCoffee/getdataProductBottledWater.php";
    static String urlGetDataProductTea = "http://192.168.56.1:81/GraceCoffee/getdataProductTea.php";
    static String urlGetDataProductFruit = "http://192.168.56.1:81/GraceCoffee/getdataProductFruit.php";
    static String urlGetDataProductFastFood = "http://192.168.56.1:81/GraceCoffee/getdataProductFastFood.php";
    static String urlGetDataProductOther = "http://192.168.56.1:81/GraceCoffee/getdataProductOther.php";

    //khai báo thuộc tính con
    TextView mTextViewGetTime, mTextViewNumberTable;

    NotificationBadge mNotify;

    //khai báo recyclerview
    RecyclerView mRecyclerViewBill, mRecyclerViewProduct, mRecyclerViewTable;

    //khai báo arrayList

    ArrayList<Table> tableArrayList = new ArrayList<>();
    ArrayList<Product> ProductArrayList = new ArrayList<>();
    ArrayList<Product> arrayListBill = new ArrayList<>();

    //khai báo adapter
    Adapter_Product_Bill mAdapterBill;
    Adapter_Product_Main mAdapterProduct;
    Adapter_Table mAdapterTable;

    //khai báo 2 thuộc tính để setup grid layout
    RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("intentBill"));

        initView();

        SetupRecycerView();

        getDataTable(urlGetDataTable);

        getDataProduct(urlGetDataProduct);

        handleEvent();

    }

    public void initView() {

        //anh xa badge
        mNotify = findViewById(R.id.badge);


        //ánh xạ recyclerview
        mRecyclerViewBill = findViewById(R.id.recycler_view_bill);
        mRecyclerViewProduct = findViewById(R.id.recycler_view_product);
        mRecyclerViewTable = findViewById(R.id.recycler_view_table);

        mTextViewNumberTable = findViewById(R.id.textview_number_table);
        mTextViewGetTime = findViewById(R.id.text_view_getTime);


        //ánh xạ button in category
        findViewById(R.id.button_category_coffee).setOnClickListener(this);
        findViewById(R.id.button_category_cannedWater).setOnClickListener(this);
        findViewById(R.id.button_category_bottledWater).setOnClickListener(this);
        findViewById(R.id.button_category_tea).setOnClickListener(this);
        findViewById(R.id.button_category_fruit).setOnClickListener(this);
        findViewById(R.id.button_category_fastFood).setOnClickListener(this);
        findViewById(R.id.button_category_other).setOnClickListener(this);

        //ánh xạ button trên tittle
        findViewById(R.id.button_exit).setOnClickListener(this);
        findViewById(R.id.btn_update_menu).setOnClickListener(this);
        findViewById(R.id.button_main_print).setOnClickListener(this);
        findViewById(R.id.button_main_save).setOnClickListener(this);

    }

    void handleEvent() {
        //xử lý event thời gian
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        mTextViewGetTime.setText(String.valueOf(formattedDate));

        //event timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getDataTable(urlGetDataTable);

            }
        }, 1000, 10000);


    }

    void SetupRecycerView() {
        //Setup cấu hình cho recycler bill
        mRecyclerViewBill.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewBill.setLayoutManager(layoutManager1);

        //Setup cấu hình cho recycler product
        mRecyclerViewLayoutManager = new GridLayoutManager(context, 1);
        mRecyclerViewProduct.setHasFixedSize(true);
        mRecyclerViewProduct.setLayoutManager(mRecyclerViewLayoutManager);

        //Setup cấu hình cho recycler table
        mRecyclerViewLayoutManager = new GridLayoutManager(context, 5);
        mRecyclerViewTable.setHasFixedSize(true);
        mRecyclerViewTable.setLayoutManager(mRecyclerViewLayoutManager);

        //Setup gán adapter cho recycler table
        mAdapterTable = new Adapter_Table(tableArrayList, getApplicationContext(), this, new OnAdapterItemClick() {
            @Override
            public void onItemClick(int positon) {
                Toast.makeText(MainActivity.this, tableArrayList.get(positon).getName(), Toast.LENGTH_SHORT).show();
                mTextViewNumberTable.setText(tableArrayList.get(positon).getName());


            }
        });
        mRecyclerViewTable.setAdapter(mAdapterTable);


        //Setup gán adapter cho recycler product
        mAdapterProduct = new Adapter_Product_Main(ProductArrayList, getApplicationContext());
        mRecyclerViewProduct.setAdapter(mAdapterProduct);
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Product product = (Product) intent.getSerializableExtra("databill");

            int id = product.getId();
            String name = product.getName();
            int price = product.getPrice();

            Toast.makeText(MainActivity.this, name + " " + price, Toast.LENGTH_SHORT).show();

            arrayListBill.add(new Product(id, name, price));

            mAdapterBill = new Adapter_Product_Bill(arrayListBill, getApplicationContext());
            mRecyclerViewBill.setAdapter(mAdapterBill);
        }
    };

    public void getDataProduct(String url) {  //
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    //khi doc duoc json
                    public void onResponse(JSONArray response) {
                        ProductArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                ProductArrayList.add(new Product(
                                        object.getInt("ID"),
                                        object.getString("Ten"),
                                        object.getInt("Gia")//trùng với định nghĩa contructor của php $this->ID

                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapterProduct.notifyDataSetChanged();
                    }
                },
                //khi doc json bi loi
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    public void getDataTable(String url) {
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
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_exit:
                finish();
                System.exit(0);
                break;
            case R.id.btn_update_menu:
                startActivity(new Intent(MainActivity.this, Update_All.class));
                break;
            case R.id.button_main_print:
                Toast.makeText(MainActivity.this, "Chưa làm gì hết hehe", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TestMVP.class);
                startActivity(intent);
                break;
            case R.id.button_main_save:
                mNotify.setNumber(++mCount);  //gán tạm thời
                break;
            case R.id.button_category_coffee:
                getDataProduct(urlGetDataProductCoffee);
                break;
            case R.id.button_category_cannedWater:
                getDataProduct(urlGetDataProductCannedWater);
                break;
            case R.id.button_category_bottledWater:
                getDataProduct(urlGetDataProductBottledWater);
                break;
            case R.id.button_category_tea:
                getDataProduct(urlGetDataProductTea);
                break;
            case R.id.button_category_fruit:
                getDataProduct(urlGetDataProductFruit);
                break;
            case R.id.button_category_fastFood:
                getDataProduct(urlGetDataProductFastFood);
                break;
            case R.id.button_category_other:
                getDataProduct(urlGetDataProductOther);
                break;
        }
    }
}
