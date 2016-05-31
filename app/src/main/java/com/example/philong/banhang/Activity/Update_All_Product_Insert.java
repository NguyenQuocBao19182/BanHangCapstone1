package com.example.philong.banhang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.philong.banhang.Adapter.Adapter_Spinner_Category;
import com.example.philong.banhang.Objects.Category;
import com.example.philong.banhang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Update_All_Product_Insert extends AppCompatActivity implements View.OnClickListener {

    //khai bao url
    String urlCategory = "http://192.168.56.1:81/GraceCoffee/getdataCategory.php";
    String urlInsertProduct = "http://192.168.56.1:81/GraceCoffee/insertProduct.php";

    //Khai bao Spinner
    Spinner mSpinerCategory;

    //Khai bao ArrayList
    final ArrayList<Category> arrayList = new ArrayList<>();

    //khai bao Adapter
    Adapter_Spinner_Category mAdapterSpinnerCategory;

    String mCategory;

    EditText mEditTextInsertProductName, mEditTextInsertProductPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_update__add);
        initView();
        setUpSpinner();
        handleEventSpinner();
        getDataCategorySpinner(urlCategory);
    }

    void initView() {
        mEditTextInsertProductName = findViewById(R.id.edittext_insert_product_name);
        mEditTextInsertProductPrice = findViewById(R.id.edittext_insert_product_price);


        mSpinerCategory = findViewById(R.id.spinner_category);

        findViewById(R.id.button_product_insert_confirm).setOnClickListener(this);
        findViewById(R.id.button_product_insert_cancel).setOnClickListener(this);
    }

    void handleEventSpinner() {
        mSpinerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mCategory = String.valueOf(arrayList.get(position).id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(Update_All_Product_Insert.this, "đéo có j", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void handleButtonConfirm() {
        String name = mEditTextInsertProductName.getText().toString().trim();
        String price = mEditTextInsertProductPrice.getText().toString().trim();
        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(Update_All_Product_Insert.this, "Điên r", Toast.LENGTH_SHORT).show();
        } else {

            InsertProduct(urlInsertProduct);
        }
    }

    void getDataCategorySpinner(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //GET để lấy xuống
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    //khi doc duoc json
                    public void onResponse(JSONArray response) {
                        arrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayList.add(new Category(
                                        object.getInt("ID"),
                                        object.getString("Ten")
                                        //trùng với định nghĩa contructor của php $this->ID
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapterSpinnerCategory.notifyDataSetChanged();
                    }
                },
                //khi doc json bi loi
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_All_Product_Insert.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //setup for Spinner
    void setUpSpinner() {

        mAdapterSpinnerCategory = new Adapter_Spinner_Category(this, R.layout.item_view_row_spinner, arrayList);
        mSpinerCategory.setAdapter(mAdapterSpinnerCategory);

    }

    private void InsertProduct(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //POST để đấy lên
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//khi insert thành công
                if (response.trim().equals("success")) {//success là báo thành công trên php lấy xuống để dùng
                    Toast.makeText(Update_All_Product_Insert.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update_All_Product_Insert.this, Update_All_Product.class));
                } else {

                    Toast.makeText(Update_All_Product_Insert.this, "có lỗi gì rồi", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {//khi insert thất bại
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_All_Product_Insert.this, "Loi ", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Loi!\n" + error.toString());//chi tiết lỗi
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //tạo map để đẩy lên
                Map<String, String> params = new HashMap<>();
                params.put("tenMon", mEditTextInsertProductName.getText().toString().trim());//đẩy lên Json
                params.put("gia", mEditTextInsertProductPrice.getText().toString().trim());//đẩy lên Json
                params.put("maMuc", mCategory);
                return params;
            }
        };

        requestQueue.add(stringRequest);//add vao

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_product_insert_confirm:
                handleButtonConfirm();
                break;
            case R.id.edittext_insert_product_price:
                break;
        }
    }
}
