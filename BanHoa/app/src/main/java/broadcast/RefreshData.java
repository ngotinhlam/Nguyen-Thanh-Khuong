package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import adapter.BannerAdapter;
import adapter.ListProductAdapter;
import adapter.ListTypeProductAdapter;
import handle.GetData;
import object.Banner;


//Broadcast này dùng để cập nhật giao diện khi đã lấy được dữ liệu từ internet

public class RefreshData extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra("action", -1);
        switch (action) {
            case 0: //Cập nhật banner
                final ArrayList<Banner> listBanner = GetData.listBanner;
                String[] listUrlBanner = MainActivity.listUrlBanner;
                listUrlBanner = new String[listBanner.size()];
                for (int i = 0; i < listBanner.size(); i++) {
                    listUrlBanner[i] = context.getString(R.string.url_host) + context.getString(R.string.url_anh_banner) + listBanner.get(i).getTenBanner() + ".jpg";
                    Log.d("urlbanner", listUrlBanner[i]);
                }
                MainActivity.bannerAdapter = new BannerAdapter(context, listUrlBanner);
                MainActivity.slideBanner.setAdapter(MainActivity.bannerAdapter);
                break;
            case 1: //Cập nhật danh mục loại sản phẩm
                //Cập nhật menu
                MainActivity.listLoaiSanPham = GetData.listLoaiSanPham;
                MainActivity.adapterLoaiSanPham = new ListTypeProductAdapter(context, MainActivity.listLoaiSanPham);
                MainActivity.spnTypeProduct.setAdapter(MainActivity.adapterLoaiSanPham);
                break;
            case 2: //Cập nhật sản phẩm
                //Cập nhật Grid View sản phẩm
                MainActivity.listSanPham = new ArrayList<>(GetData.listSanPham);
                MainActivity.adapterSanPham = new ListProductAdapter(context, MainActivity.listSanPham);
                MainActivity.gvProduct.setAdapter(MainActivity.adapterSanPham);
                break;
            default:
                break;
        }
    }
}
