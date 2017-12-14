package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khuong.thanh.nguyen.banhoa.R;
import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import object.LoaiSanPham;


//Lớp lấy dữ liệu sản phẩm gắn lên grid view sản phẩm

public class ListTypeProductAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<LoaiSanPham> arrayList;
    Context context;

    public ListTypeProductAdapter(Context context, ArrayList<LoaiSanPham> arrayList) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.arrayList = arrayList;
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
        TextView tvTypeName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LoaiSanPham typeproduct = arrayList.get(position);
        if (convertView==null) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, null);
            holder = new ViewHolder();
            holder.tvTypeName = (TextView) convertView.findViewById(R.id.tvTypeName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       holder.tvTypeName.setText(typeproduct.getTenLoai());

        return convertView;
    }

}
