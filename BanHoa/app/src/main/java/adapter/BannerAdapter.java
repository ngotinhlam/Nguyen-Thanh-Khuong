package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.khuong.thanh.nguyen.banhoa.R;
import com.squareup.picasso.Picasso;

//Lớp xử lý gắn ảnh banner

public class BannerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String[] listUrlBanner;

    public BannerAdapter(Context context, String[] listUrlBanner) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listUrlBanner = listUrlBanner;
    }

    @Override
    public int getCount() {
        return listUrlBanner.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_banner, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.banner);
        Picasso.with(context).load(listUrlBanner[position]).into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
