package broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;
import com.squareup.picasso.Picasso;

//Broadcast bắt sự thay đổi của trạng thái đăng nhập (đăng nhập, đăng xuất)

public class LoginStatus extends BroadcastReceiver {

    SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = context.getSharedPreferences(context.getString(R.string.sharedpreferences_key_authentication), Context.MODE_PRIVATE);

        String action = intent.getStringExtra("action");
        SharedPreferences.Editor editor = preferences.edit();

        if (action.equals("logged")) { //Có user đăng nhập
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
            MainActivity.llUser.setVisibility(View.VISIBLE);
            MainActivity.llLoginRequired.setVisibility(View.INVISIBLE);
            MainActivity.llInfoUser.setVisibility(View.VISIBLE);
            MainActivity.tvFullName.setText(MainActivity.tk.getHoTen());
            String urlAvatar = MainActivity.tk.getAnhCaNhan();
            Log.d("url", urlAvatar);
            if (urlAvatar.equals("")) {
                MainActivity.civAvatar.setImageResource(R.drawable.icon_user);
            } else {
                Picasso.with(context).load(urlAvatar).into(MainActivity.civAvatar);///lay hinh tu mot url truyen vao 1 imageview
                //Lấy ảnh từ một URL gắn vào imageView
                Picasso.with(context).load(urlAvatar).into(MainActivity.civAvatar);
            }
            MainActivity.tvNameInfoUser.setText("Họ Tên: "+MainActivity.tk.getHoTen());
            MainActivity.tvPhoneInfoUser.setText("SĐT: " +MainActivity.tk.getSoDienThoai());
            MainActivity.tvAddressInfoUser.setText("Địa Chỉ: "+MainActivity.tk.getDiaChi());
            MainActivity.tvEmailInfoUser.setText("Email: "+MainActivity.tk.getEmail());

        } else { //Không có user đăng nhập
            editor.clear();
            editor.commit();
            MainActivity.llUser.setVisibility(View.INVISIBLE);
            MainActivity.llLoginRequired.setVisibility(View.VISIBLE);
            MainActivity.llInfoUser.setVisibility(View.INVISIBLE);
        }
    }
}
