package handle;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khuong.thanh.nguyen.banhoa.R;
import com.khuong.thanh.nguyen.banhoa.RegisterAccountActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

//Lớp xử lý đăng ký tài khoản người dùng

public class RegisterAccount {

    Context context;
    RequestQueue queue;

    public RegisterAccount(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context); // add các request đó vào RequestQueue.
    }

    public void register(final String username, final String password, final String fullname, final String email, final String address, final String phone, final Bitmap avatar) {
        String url = context.getString(R.string.url_host) + context.getString(R.string.url_dang_ky_tai_khoan);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stringJson) {
                        try {
                            JSONObject jsonObject = new JSONObject(stringJson);
                            String response = jsonObject.getString("account").trim();
                            switch (response) {
                                case "username exist":
                                    RegisterAccountActivity.dialogNotification.setMessage("Tên tài khoản này đã tồn tại");
                                    RegisterAccountActivity.dialogNotification.show();
                                    break;
                                case "register account fail":
                                    RegisterAccountActivity.dialogNotification.setMessage("Đăng ký thất bại, vui lòng thử lại sau");
                                    RegisterAccountActivity.dialogNotification.show();
                                    break;
                                case "register account success":
                                    RegisterAccountActivity.dialogNotification.setMessage("Đăng ký thành công");
                                    RegisterAccountActivity.dialogNotification.show();
                                    RegisterAccountActivity.clearInfo();
                                    String responseAvatar = jsonObject.getString("avatar").trim();
                                    if (responseAvatar.equals("upload avatar fail")) {
                                        RegisterAccountActivity.dialogNotification.setMessage("Thiết lập ảnh đại diện thất bại");
                                        RegisterAccountActivity.dialogNotification.show();
                                    }
                                    break;
                                default:
                                    RegisterAccountActivity.dialogNotification.setMessage("Đăng ký thất bại, vui lòng thử lại sau");
                                    RegisterAccountActivity.dialogNotification.show();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                String image = getStringImage(avatar);
                String urlhost = context.getString(R.string.url_host);
                String name = "avatar_" + username;
                param.put("username", username);
                param.put("password", password);
                param.put("fullname", fullname);
                param.put("email", email);
                param.put("address", address);
                param.put("phone", phone);
                param.put("name", name);
                param.put("host", urlhost);
                param.put("image", image);
                return param;
            }
        };
        queue.add(request);
    }

    public String getStringImage(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imagebyte = stream.toByteArray();
            String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
            return encode;
        } else {
            return "";
        }
    }
}
