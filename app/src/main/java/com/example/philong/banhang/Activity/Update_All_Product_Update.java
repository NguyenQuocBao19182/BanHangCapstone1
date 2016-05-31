package com.example.philong.banhang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.R;

import java.util.HashMap;
import java.util.Map;

public class Update_All_Product_Update extends AppCompatActivity {
    //khai bao cac nut
    EditText mEditTextName, mEditTextPrice;
    Button mButtonConFirm, mButtonCancel;

    //khai bao id cho method update
    int id = 0;
    //url
    String urlUpdate = "http://192.168.56.1:81/GraceCoffee/updateProduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_update__update);

        initView();
        handleEvent();

    }

    void initView() {
        mEditTextName = findViewById(R.id.edittext_name);
        mEditTextPrice = findViewById(R.id.edittext_price);
        mButtonConFirm = findViewById(R.id.button_menu_update_update_confirm);
        mButtonCancel = findViewById(R.id.button_menu_update_update_cancel);
    }

    void handleEvent() {
        //nhận intent từ .. gán vào edit
        Intent intent = getIntent();
        Product menu_update = (Product) intent.getSerializableExtra("dataProduct"); //lấy Serializable(tuần tự) từ intent adapter

        id = menu_update.getId();
        mEditTextName.setText(menu_update.getName());
        mEditTextPrice.setText(String.valueOf(menu_update.getPrice()));

        mButtonConFirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString().trim();
                String price = mEditTextPrice.getText().toString().trim();
                if (name.isEmpty() || price.isEmpty()) {
                    Toast.makeText(Update_All_Product_Update.this, "Sao để trống", Toast.LENGTH_SHORT).show();
                } else {
                    upDateDataBase(urlUpdate);
                }
            }
        });
    }

    public void upDateDataBase(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {//success là báo thành công trên php lấy xuống để dùng
                    Toast.makeText(Update_All_Product_Update.this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update_All_Product_Update.this, Update_All_Product.class));
                } else {

                    Toast.makeText(Update_All_Product_Update.this, "có lỗi gì rồi", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_All_Product_Update.this, "Loi ", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Loi!\n" + error.toString());//chi tiết lỗi
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //tạo map để đẩy lên
                Map<String, String> params = new HashMap<>();
                params.put("idMon", String.valueOf(id));
                params.put("NameMon", mEditTextName.getText().toString().trim());//đẩy lên Json hotenSV với edtTen .trim để xóa khoảng trắng đầu,cuối
                params.put("PriceMon", mEditTextPrice.getText().toString().trim());//đẩy lên Json

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }


}
