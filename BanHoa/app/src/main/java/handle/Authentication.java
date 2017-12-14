package handle;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import object.TaiKhoan;


//Lớp chứng thực đăng nhập

public class Authentication {

    Activity activity;
    SharedPreferences preferences;
    RequestQueue queue;
    Intent intent;

    public Authentication(Activity activity) {
        this.activity = activity;
        queue = Volley.newRequestQueue(activity);
        intent = new Intent();
        preferences = activity.getSharedPreferences(activity.getString(R.string.sharedpreferences_key_authentication), Context.MODE_PRIVATE);
    }

    public void login(final String username, final String password) {
        final SharedPreferences.Editor editor = preferences.edit();
        String url = activity.getString(R.string.url_host) + activity.getString(R.string.url_kiem_tra_dang_nhap);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String strJson) {
                        try {
                            JSONObject object = new JSONObject(strJson);
                            String result = object.getString("result").trim();
                            switch (result) {
                                case "login success":
                                    JSONObject data = object.getJSONObject("data");
                                    MainActivity.tk = new TaiKhoan(data.getString("TenTaiKhoan"), data.getString("MatKhau"), data.getString("HoTen"), data.getString("Email"), data.getString("SoDienThoai"), data.getString("DiaChi"), data.getString("AnhCaNhan"));
                                    intent.setAction("com.nguyenthanhkhuong.banhoa.LOGIN_STATUS");
                                    intent.putExtra("action", "logged");
                                    intent.putExtra("username", username);
                                    intent.putExtra("password", password);
                                    activity.sendBroadcast(intent);
                                    Toast.makeText(activity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    break;
                                case "login fail":
                                    Toast.makeText(activity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        queue.add(request);
    }

    public void logout() {
        intent.setAction("com.nguyenthanhkhuong.banhoa.LOGIN_STATUS");
        intent.putExtra("action", "loggout");
        activity.sendBroadcast(intent);
    }

    public void updateStatusLogged() {
        if (isLogged()) {
            final String user = preferences.getString("username", "");
            final String pass = preferences.getString("password", "");
            final SharedPreferences.Editor editor = preferences.edit();
            String url = activity.getString(R.string.url_host) + activity.getString(R.string.url_kiem_tra_dang_nhap);
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String strJson) {
                            try {
                                JSONObject object = new JSONObject(strJson);
                                String result = object.getString("result").trim();
                                switch (result) {
                                    case "login success":
                                        JSONObject data = object.getJSONObject("data");
                                        MainActivity.tk = new TaiKhoan(data.getString("TenTaiKhoan"), data.getString("MatKhau"), data.getString("HoTen"), data.getString("Email"), data.getString("SoDienThoai"), data.getString("DiaChi"), data.getString("AnhCaNhan"));
                                        intent.setAction("com.nguyenthanhkhuong.banhoa.LOGIN_STATUS");
                                        intent.putExtra("action", "logged");
                                        intent.putExtra("username", user);
                                        intent.putExtra("username", pass);
                                        activity.sendBroadcast(intent);
                                        break;
                                    case "login fail":
                                        logout();
                                        break;
                                    default:
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                    params.put("username", user);
                    params.put("password", pass);

                    return params;
                }
            };
            queue.add(request);
        } else {
            logout();
        }
    }

    public boolean isLogged() {
        return !preferences.getString(activity.getString(R.string.key_username), "").equals("") && !preferences.getString(activity.getString(R.string.key_password), "").equals("");
    }

}
