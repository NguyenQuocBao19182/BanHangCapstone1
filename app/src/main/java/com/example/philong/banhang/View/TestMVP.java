package com.example.philong.banhang.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.Presenter.PresenterTestMVP;
import com.example.philong.banhang.Presenter.PresentorResponseView;
import com.example.philong.banhang.R;

import java.util.ArrayList;

public class TestMVP extends AppCompatActivity implements PresentorResponseView {//để nhận thông báo và xử lí
    String urlGetDataProduct = "http://192.168.56.1:81/GraceCoffee/getdataProduct.php";
    AdapterTestMVP mAapterTestMVP;
    RecyclerView mRecyclerViewTestMVP;
    ArrayList<Product> mProductArrayList = new ArrayList<>();
    //khai báo 2 thuộc tính để setup grid layout
    RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    Context context;
    PresenterTestMVP presenterTestMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvp);
        initView();
        setupForRecyclerView();
        presenterTestMVP=new PresenterTestMVP(this); //khởi tạo presenter

        //để gởi yêu cầu đi cũng như nhận về giá trị
        presenterTestMVP.receivedGetData(urlGetDataProduct,this,mAapterTestMVP,mProductArrayList);


    }

    private void setupForRecyclerView() {
        mRecyclerViewLayoutManager = new GridLayoutManager(context, 1);
        mRecyclerViewTestMVP.setHasFixedSize(true);
        mRecyclerViewTestMVP.setLayoutManager(mRecyclerViewLayoutManager);

        mAapterTestMVP = new AdapterTestMVP(mProductArrayList, getApplicationContext());
        mRecyclerViewTestMVP.setAdapter(mAapterTestMVP);
    }

    void initView() {
        mRecyclerViewTestMVP = findViewById(R.id.recycler_test_mvp);
    }

    @Override
    public void getDataSuccess() {
        Toast.makeText(TestMVP.this, "Đã lấy được dữ liệu", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getDataFail() {
        Toast.makeText(TestMVP.this, "Chưa lấy được dữ liệu", Toast.LENGTH_SHORT).show();
    }
//    public void getDataProduct(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        //GET để lấy xuống
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    //khi doc duoc json
//                    public void onResponse(JSONArray response) {
//                        mProductArrayList.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//                                mProductArrayList.add(new Product(
//                                        object.getInt("ID"),
//                                        object.getString("Ten"),
//                                        object.getInt("Gia")//trùng với định nghĩa contructor của php $this->ID
//
//                                ));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        mAapterTestMVP.notifyDataSetChanged();
//                    }
//                },
//                //khi doc json bi loi
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(TestMVP.this, error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        requestQueue.add(jsonArrayRequest);
//    }
}
