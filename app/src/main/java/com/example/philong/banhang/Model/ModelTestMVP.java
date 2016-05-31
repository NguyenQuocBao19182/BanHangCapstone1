package com.example.philong.banhang.Model;

import android.content.Context;
import android.util.Log;

import com.example.philong.banhang.Objects.Product;
import com.example.philong.banhang.View.AdapterTestMVP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ModelTestMVP {
    ModelResponsePresenterGetData modelResponsePresenterGetData;


    public ModelTestMVP(ModelResponsePresenterGetData modelResponsePresenterGetData) {
        this.modelResponsePresenterGetData = modelResponsePresenterGetData;
    }




    public void getDataProduct(String url, Context context, final AdapterTestMVP mAapterTestMVP, final ArrayList<Product> mProductArrayList) {

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(url);
            Log.d("aaa", "getDataProduct: "+url);
            for (int i =0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                int ID = jsonObject1.getInt("ID");
                String Ten = jsonObject1.getString("Ten");
                int GIA = jsonObject1.getInt("Gia");

                mProductArrayList.add(new Product(ID, Ten, GIA));
                mAapterTestMVP.notifyDataSetChanged();
                modelResponsePresenterGetData.getDataSuccess();


         }
        } catch (JSONException e) {
            modelResponsePresenterGetData.getDataFail();
        }
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        //GET để lấy xuống
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    //khi doc duoc json
//                    public void onResponse(JSONArray response) {
//
////                       mProductArrayList.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//
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
//                        modelResponsePresenterGetData.getDataSuccess();
//
//                    }
//                },
//                //khi doc json bi loi
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
//                        modelResponsePresenterGetData.getDataFail();
//                    }
//                });
//        requestQueue.add(jsonArrayRequest);
    }
}
