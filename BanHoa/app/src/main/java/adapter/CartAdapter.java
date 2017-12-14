package adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.khuong.thanh.nguyen.banhoa.CartActivity;
import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import handle.ProductCart;


public class CartAdapter extends BaseAdapter{

    LayoutInflater inflater;
    ArrayList<ProductCart> arrayList;
    Activity activity;

    public CartAdapter(Activity activity, ArrayList<ProductCart> arrayList) {
        inflater = activity.getLayoutInflater();
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvPrice;
        TextView tvCount;
        ImageView ivRemove;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        ProductCart product = arrayList.get(i);
        if (view == null) {
            view = inflater.inflate(R.layout.item_cart, null);
            holder = new ViewHolder();
            holder.tvPrice = (TextView) view.findViewById(R.id.tvPriceProductCart);
            holder.tvName = (TextView) view.findViewById(R.id.tvNameProductCart);
            holder.tvCount = (TextView) view.findViewById(R.id.tvCountProductCart);
            holder.ivRemove = (ImageView) view.findViewById(R.id.ivRemove);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvName.setText(arrayList.get(i).getSanPham().getTenSanPham());
        holder.tvPrice.setText(arrayList.get(i).getSanPham().getDonGia() + "");
        holder.tvCount.setText(arrayList.get(i).getSoLuong() + "");

        //Khi bấm vào hình dấu "x" màu đỏ sẽ thực hiện xóa sản phẩm
        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActivity.arrayList.remove(i);
                MainActivity.listGioHang.remove(i);
                CartActivity.adapter.notifyDataSetChanged();
                CartActivity.total();
            }
        });

        //Khi bấm vào TextView hiển thị số lượng thì sẽ hiện lên 1 dialog cho phép nhập số lượng muốn thay đổi
        holder.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_change_info);

                final EditText etInfo = (EditText) dialog.findViewById(R.id.etChangeInformation);
                Button btnAgree = (Button) dialog.findViewById(R.id.btnAgreeChangeInformation);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelChangeInformation);

                String soluong = MainActivity.listGioHang.get(i).getSoLuong() + "";

                etInfo.setText(soluong);
                etInfo.setSelection(soluong.length());

                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soluongmoi = Integer.parseInt(etInfo.getText().toString());
                        arrayList.get(i).setSoLuong(soluongmoi);
                        MainActivity.listGioHang.get(i).setSoLuong(soluongmoi);
                        notifyDataSetChanged();
                        CartActivity.total();
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }
}
