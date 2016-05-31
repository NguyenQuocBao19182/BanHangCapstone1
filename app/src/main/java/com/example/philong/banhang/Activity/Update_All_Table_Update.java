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
import com.example.philong.banhang.Objects.Table;
import com.example.philong.banhang.R;

import java.util.HashMap;
import java.util.Map;

public class Update_All_Table_Update extends AppCompatActivity implements View.OnClickListener {
    //khai bao cac nut
    EditText mEditTextUpdateTableNumber;

    //khai bao id cho method update
    int id = 0;
    //url
    String urlUpdateTable = "http://192.168.56.1:81/GraceCoffee/updateTable.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_table__update);
        initView();
        handleEvent();
    }

    void initView() {
        mEditTextUpdateTableNumber = findViewById(R.id.edittext_update_number_table);
        findViewById(R.id.button_table_update_confirm).setOnClickListener(this);
        findViewById(R.id.button_table_update_cancel).setOnClickListener(this);
    }

    void handleEvent() {
        //nhận intent từ .. gán vào edit
        Intent intent = getIntent();
        Table table = (Table) intent.getSerializableExtra("soban");

        id = table.getId();
        mEditTextUpdateTableNumber.setText(table.getName());


    }

    void handleEventButtonConfirm() {
        String numberTable = mEditTextUpdateTableNumber.getText().toString().trim();
        if (numberTable.isEmpty()) {
            Toast.makeText(Update_All_Table_Update.this, "Sao rỗng", Toast.LENGTH_SHORT).show();
        } else updateDataBaseTable(urlUpdateTable);
    }

    public void updateDataBaseTable(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {//success là báo thành công trên php lấy xuống để dùng
                    Toast.makeText(Update_All_Table_Update.this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update_All_Table_Update.this, Update_All_Table.class));
                } else {

                    Toast.makeText(Update_All_Table_Update.this, "có lỗi gì rồi", Toast.LENGTH_SHORT).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_All_Table_Update.this, "Loi ", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Loi!\n" + error.toString());//chi tiết lỗi
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //tạo map để đẩy lên
                Map<String, String> params = new HashMap<>();
                params.put("idTable", String.valueOf(id));
                params.put("SoBan", mEditTextUpdateTableNumber.getText().toString().trim());//đẩy lên Json .trim để xóa khoảng trắng đầu,cuối


                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_table_update_confirm:
                handleEventButtonConfirm();
                break;
            case R.id.button_table_update_cancel:
                break;
        }
    }
}
