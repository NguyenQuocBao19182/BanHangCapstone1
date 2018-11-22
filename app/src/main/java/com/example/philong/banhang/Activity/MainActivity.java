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
import android.widget.Button;
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
import com.example.philong.banhang.OnAdapterItemClick;
import com.example.philong.banhang.R;
import com.nex3z.notificationbadge.NotificationBadge;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {




        int count=0;

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
        TextView txt_get_time,textViewNumberTable;
        Button btn_update_menu, btn_exit;
        Button btn_getProduct_coffee, btn_getProduct_cannedWater, btn_getProduct_bottledWater, btn_getProduct_Tea;
        Button btn_getProduct_Fruit, btn_getProduct_fastFood, btn_getProduct_Other;
        Button buttonMainPrint,mButtonMainSave;

        NotificationBadge mNotify;

        //khai báo recyclerview
        RecyclerView recyclerView_bill, recyclerView_product, recyclerView_table;

        //khai báo arrayList

        ArrayList<Table> tableArrayList = new ArrayList<>();
        ArrayList<Product> ProductArrayList = new ArrayList<>();
        ArrayList<Product> arrayListBill=new ArrayList<>();

        //khai báo adapter
        Adapter_Product_Bill menu_adapter_update_bill;
        Adapter_Product_Main adapter_product;
        Adapter_Table adapter_table;

        //khai báo 2 thuộc tính để setup grid layout
        RecyclerView.LayoutManager recyclerViewLayoutManager;
        Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("intentBill"));

        anhXa();

        SetupRecycerView();

        GetDataTable(urlGetDataTable);

        GetDataProduct(urlGetDataProduct);

        XuLyEvent();

    }

    public void anhXa(){

        //anh xa badge
        mNotify=findViewById(R.id.badge);


        //ánh xạ recyclerview
        recyclerView_bill = findViewById(R.id.recycler_view_bill);
        recyclerView_product = findViewById(R.id.recycler_view_product);
        recyclerView_table = findViewById(R.id.recycler_view_table);

        textViewNumberTable=findViewById(R.id.textview_number_table);
        txt_get_time=findViewById(R.id.text_view_getTime);


        //ánh xạ button in category
        btn_getProduct_coffee=findViewById(R.id.button_category_coffee);
        btn_getProduct_cannedWater=findViewById(R.id.button_category_cannedWater);
        btn_getProduct_bottledWater=findViewById(R.id.button_category_bottledWater);
        btn_getProduct_Tea=findViewById(R.id.button_category_tea);
        btn_getProduct_Fruit=findViewById(R.id.button_category_fruit);
        btn_getProduct_fastFood=findViewById(R.id.button_category_fastFood);
        btn_getProduct_Other=findViewById(R.id.button_category_other);

        //ánh xạ button trên tittle
        btn_exit=findViewById(R.id.button_exit);
        btn_update_menu=findViewById(R.id.btn_update_menu);
        buttonMainPrint=findViewById(R.id.button_main_print);
        mButtonMainSave=findViewById(R.id.button_main_save);

    }

        void XuLyEvent(){
        //xử lý event thời gian
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        txt_get_time.setText(String.valueOf(formattedDate));

        //xu ly notify
            mButtonMainSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNotify.setNumber(++count);
                }
            });


        //event timer
            Timer timer=new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    GetDataTable(urlGetDataTable);

                }
            },1000,10000);

        //xử lý button exit
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //xử lý button update
        btn_update_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Update_All.class));
            }
        });

        //xử lý các button trong category
        btn_getProduct_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductCoffee);
            }
        });

        btn_getProduct_cannedWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductCannedWater);
            }
        });

        btn_getProduct_bottledWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductBottledWater);
            }
        });
        btn_getProduct_Tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductTea);
            }
        });

        btn_getProduct_Fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductFruit);
            }
        });

        btn_getProduct_fastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductFastFood);
            }
        });

        btn_getProduct_Other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataProduct(urlGetDataProductOther);
            }
        });






    }

    void SetupRecycerView(){
        //Setup cấu hình cho recycler bill
        recyclerView_bill.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false);
        recyclerView_bill.setLayoutManager(layoutManager1);

        //Setup cấu hình cho recycler product
        recyclerViewLayoutManager = new GridLayoutManager(context, 1);
        recyclerView_product.setHasFixedSize(true);
        recyclerView_product.setLayoutManager(recyclerViewLayoutManager);

        //Setup cấu hình cho recycler table
        recyclerViewLayoutManager = new GridLayoutManager(context, 5);
        recyclerView_table.setHasFixedSize(true);
        recyclerView_table.setLayoutManager(recyclerViewLayoutManager);

        //Setup gán adapter cho recycler table
        adapter_table=new Adapter_Table(tableArrayList, getApplicationContext(),this, new OnAdapterItemClick() {
            @Override
            public void onItemClick(int positon) {
                Toast.makeText(MainActivity.this, tableArrayList.get(positon).getName(), Toast.LENGTH_SHORT).show();
                    textViewNumberTable.setText(tableArrayList.get(positon).getName());


        }
        });
        recyclerView_table.setAdapter(adapter_table);

        //Setup gán adapter cho recycler bill

        //Intent intent=getIntent();
        //Product product=(Product) intent.getSerializableExtra("databill");
        //bug
       // arrayListBill.add(new Product(2,"CaPheDen",20000));
        //arrayListBill.add(new Product(2,product.getName(),product.getPrice()));


       // menu_adapter_update_bill=new Adapter_Product_Bill(arrayListBill,getApplicationContext());
       // recyclerView_bill.setAdapter(menu_adapter_update_bill);



        //Setup gán adapter cho recycler product
        adapter_product=new Adapter_Product_Main(ProductArrayList,getApplicationContext());
        recyclerView_product.setAdapter(adapter_product);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Product product=(Product) intent.getSerializableExtra("databill");

            int id=product.getId();
            String name=product.getName();
            int price=product.getPrice();

            Toast.makeText(MainActivity.this,name +" "+price ,Toast.LENGTH_SHORT).show();

            arrayListBill.add(new Product(id, name, price));

            menu_adapter_update_bill=new Adapter_Product_Bill(arrayListBill,getApplicationContext());
            recyclerView_bill.setAdapter(menu_adapter_update_bill);
        }
    };

    public void GetDataProduct (String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    //khi doc duoc json
                    public void onResponse(JSONArray response) {
                        ProductArrayList.clear();
                        for (int i=0;i<response.length();i++){
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
                        adapter_product.notifyDataSetChanged();
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

    public void GetDataTable (String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    //khi doc duoc json
                    public void onResponse(JSONArray response) {
                        tableArrayList.clear();
                        for (int i=0;i<response.length();i++){
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
                        adapter_table.notifyDataSetChanged();
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




}
