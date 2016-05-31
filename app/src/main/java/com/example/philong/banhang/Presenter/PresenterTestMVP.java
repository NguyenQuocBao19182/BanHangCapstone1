package com.example.philong.banhang.Presenter;

import android.content.Context;

import com.example.philong.banhang.Model.ModelResponsePresenterGetData;
import com.example.philong.banhang.Model.ModelTestMVP;
import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.View.AdapterTestMVP;

import java.util.ArrayList;

public class PresenterTestMVP implements ModelResponsePresenterGetData {
    private ModelTestMVP modelTestMVP;
    private PresentorResponseView presentorResponseView;
    private  ArrayList<Product> mProductArrayList = new ArrayList<>();
    public PresenterTestMVP(PresentorResponseView presentorResponseView) {
        this.presentorResponseView = presentorResponseView;
    }
    public void receivedGetData(String url, Context context, AdapterTestMVP adapterTestMVP,ArrayList<Product> mProductArrayList1 ){

        modelTestMVP=new ModelTestMVP(this);
        modelTestMVP.getDataProduct(url,context,adapterTestMVP,mProductArrayList1); //gởi đến và nhận từ model
    }

    @Override
    public void getDataSuccess() {
        presentorResponseView.getDataSuccess();  //gởi success đến view
    }

    @Override
    public void getDataFail() {
    presentorResponseView.getDataFail(); //gởi fail đến view
    }
}
