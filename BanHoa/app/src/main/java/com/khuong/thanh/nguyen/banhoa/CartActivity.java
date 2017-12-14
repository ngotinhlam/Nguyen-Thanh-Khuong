package com.khuong.thanh.nguyen.banhoa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.CartAdapter;
import handle.ProductCart;

import static android.R.id.list;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    public static TextView tvTotal;
    Button btnBuy;
    public static ArrayList<ProductCart> arrayList;
    public static CartAdapter adapter;
    public static int total;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();

        queue = Volley.newRequestQueue(this);

        arrayList = (ArrayList<ProductCart>) getIntent().getSerializableExtra("data");
        adapter = new CartAdapter(this, arrayList);
        lvCart.setAdapter(adapter);

        total();
    }

    public static void total() {
        total = 0;
        //Tính tổng cộng tiền
        for (int i = 0; i < arrayList.size(); i++) {
            ProductCart pc = arrayList.get(i);
            total += pc.getSanPham().getDonGia() * pc.getSoLuong();
        }
        tvTotal.setText(total + " VNĐ");
    }

    public void initView() {
        lvCart = (ListView) findViewById(R.id.lvCart);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        btnBuy = (Button) findViewById(R.id.btnBuy);

        //Khi nhấn mua hàng sẽ gửi dữ liệu lên server để thêm vào CSDL
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = getString(R.string.url_host) + getString(R.string.url_mua_hang);
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String result) {
                                Log.d("aaa", result);
                               if (result.equals("1")) {
                                   Toast.makeText(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(CartActivity.this, "Đặt hàng thất bại, thử lại sau", Toast.LENGTH_SHORT).show();
                               }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        Gson gson = new Gson();
                        String dataJson = gson.toJson(arrayList);
                        Log.d("aaa", dataJson);
                        params.put("data", dataJson);
                        params.put("makh", MainActivity.tk.getTenTaiKhoan());

                        return params;
                    }
                };
                queue.add(request);
            }
        });
    }
}
