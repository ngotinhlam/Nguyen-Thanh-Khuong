package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.khuong.thanh.nguyen.banhoa.MainActivity;
import com.khuong.thanh.nguyen.banhoa.R;
import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import object.SanPham;


//Lớp lấy dữ liệu sản phẩm gắn lên grid view sản phẩm

public class ListProductAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<SanPham> arrayList;
    ArrayList<SanPham> arrayListCopy;
    Context context;

    public ListProductAdapter(Context context, ArrayList<SanPham> arrayList) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
        arrayListCopy = new ArrayList<>(arrayList);
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;
        ImageView btnCart;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SanPham product = arrayList.get(position);
        if (convertView==null) {
            convertView = layoutInflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.btnCart = (ImageView) convertView.findViewById(R.id.btnCart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String urlImage = context.getString(R.string.url_host) + context.getString(R.string.url_anh_san_pham) + product.getHinhAnh() + ".jpg";
        holder.ivImage.setImageResource(R.drawable.no_image);
        Picasso.with(context).load(urlImage).into(holder.ivImage);
        holder.tvName.setText(product.getTenSanPham());
        String dongia = String.format("%,d VNĐ",product.getDonGia()).replace(",",".");
        holder.tvPrice.setText(dongia);

        //Sự kiện nhấn nút giỏ hàng
        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addProductToCart(arrayList.get(position));
            }
        });

        return convertView;
    }

    public void filterSearch(String str) {
        arrayList.clear();
        for (int i=0;i<arrayListCopy.size(); i++) {
            SanPham sp = arrayListCopy.get(i);
            if (removeAccent(sp.getTenSanPham()).indexOf(removeAccent(str)) != -1) {
                arrayList.add(sp);
            }
        }
        notifyDataSetChanged();
    }

    public void filterType(String maloai) {
        arrayList.clear();
        if (maloai.equals("0")) { //Chọn tất cả loại sản phẩm
            MainActivity.listSanPham = arrayList = new ArrayList<>(arrayListCopy);
        } else {
            for (int i = 0; i < arrayListCopy.size(); i++) {
                SanPham sp = arrayListCopy.get(i);
                if (sp.getMaLoai().equals(maloai)) {
                    arrayList.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

    //Hàm chuyển chuỗi có dấu thành không dấu và in thường tất cả
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
    }
}
