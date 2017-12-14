package handle;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.khuong.thanh.nguyen.banhoa.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import object.Banner;
import object.LoaiSanPham;
import object.SanPham;

//Đối tượng kết nối tới server để lấy dữ liệu về

public class GetData {

    Context context;
    RequestQueue queue;
    public static ArrayList<Banner> listBanner;
    public static ArrayList<LoaiSanPham> listLoaiSanPham;
    public static ArrayList<SanPham> listSanPham;
    Intent intent; //broadcast sẽ gửi intent này đi khi dữ liệu lấy về từ internet hoàn tất

    public GetData(Context context) {
        this.context = context;
        //Khởi tạo đối tượng RequestQueue (đối tượng này dùng để giao tiếp với dữ liệu trên internet)
        queue = Volley.newRequestQueue(context);
        //Tạo intent để send Broadcast
        intent = new Intent();
        intent.setAction(context.getString(R.string.action_name_load_data));
        //Lấy dữ liệu
        getAllData();
    }

    //Lấy tất cả dữ liệu
    public void getAllData() {
        layTatCaBanner();
        layTatCaLoaiSanPham();
        layTatCaSanPham();
    }

    //lấy tất cả banner
    public void layTatCaBanner() {
        listBanner= new ArrayList<>();
        String url = context.getString(R.string.url_host) + context.getString(R.string.url_lay_tat_ca_banner);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        object = response.getJSONObject(i);
                        listBanner.add(new Banner(object.getString("MaBanner"), object.getString("TenBanner")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Gán một key có tên là action cho intent (khi broadcast được gọi thì sẽ lấy ra giá trị của key này để biết được phần giao diện nào sẽ được cập nhật)
                intent.putExtra("action", 0);
                //Gửi broadcast
                context.sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    //Lấy tất cả loại sản phẩm
    public void layTatCaSanPham() {
        listSanPham = new ArrayList<>();
        String url = context.getString(R.string.url_host) + context.getString(R.string.url_lay_tat_ca_san_pham);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        object = response.getJSONObject(i);
                        listSanPham.add(new SanPham(object.getString("MaSanPham"), object.getString("TenSanPham"), object.getString("MaLoai"), object.getString("MoTa"), object.getInt("DonGia"), object.getInt("SoLuong"), object.getString("HinhAnh")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Gán một key có tên là action cho intent (khi broadcast được gọi thì sẽ lấy ra giá trị của key này để biết được phần giao diện nào sẽ được cập nhật)
                intent.putExtra("action", 1);
                //Gửi broadcast
                context.sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }

    //Lấy tất cả loại sản phẩm
    public void layTatCaLoaiSanPham() {
        listLoaiSanPham = new ArrayList<>();
        String url = context.getString(R.string.url_host) + context.getString(R.string.url_lay_tat_ca_loai_san_pham);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        object = response.getJSONObject(i);
                        listLoaiSanPham.add(new LoaiSanPham(object.getString("MaLoai"), object.getString("TenLoai"), object.getString("GhiChu")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("error", "Không lấy được danh mục loại sản phẩm");
                    }
                }
                //Gán một key có tên là action cho intent (khi broadcast được gọi thì sẽ lấy ra giá trị của key này để biết được phần giao diện nào sẽ được cập nhật)
                intent.putExtra("action", 2);
                //Gửi broadcast
                context.sendBroadcast(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        queue.add(request);
    }
}
