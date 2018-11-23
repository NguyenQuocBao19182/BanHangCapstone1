package com.example.philong.banhang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.philong.banhang.R;

public class Update_All extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }
    void initView(){
        findViewById(R.id.button_update_menu).setOnClickListener(this);
        findViewById(R.id.button_update_table).setOnClickListener(this);
        findViewById(R.id.button_update_employee).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_update_menu :
                startActivity(new Intent(Update_All.this,Update_All_Product.class));
                break;
            case R.id.button_update_table:
                startActivity(new Intent(Update_All.this,Update_All_Table.class));
                break;
            case R.id.button_update_employee:
                startActivity(new Intent(Update_All.this,Update_All_Employee.class));
                break;
        }
    }
}
