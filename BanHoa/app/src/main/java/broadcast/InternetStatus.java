package broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;


//Broadcast bắt sự thay đổi trạng thái kết nối internet

public class InternetStatus extends BroadcastReceiver {

    //Khi trạng thái internet thay đổi sẽ chạy hàm này
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable(context)) {
            MainActivity.data.getAllData();
        } else {
            Toast.makeText(context, context.getString(R.string.lost_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    //Kiểm tra xem có đang được kết nối internet hay không
    public static boolean isNetworkAvailable(Context context) {
        int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int typeNetwork : type) {
                NetworkInfo info = manager.getNetworkInfo(typeNetwork);
                if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
