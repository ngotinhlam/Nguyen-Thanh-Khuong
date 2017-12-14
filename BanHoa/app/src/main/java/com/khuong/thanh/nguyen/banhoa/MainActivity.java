package com.khuong.thanh.nguyen.banhoa;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import adapter.BannerAdapter;
import adapter.ListProductAdapter;
import adapter.ListTypeProductAdapter;
import broadcast.InternetStatus;
import de.hdodenhof.circleimageview.CircleImageView;
import handle.Authentication;
import handle.GetData;
import handle.ProductCart;
import object.LoaiSanPham;
import object.SanPham;
import object.TaiKhoan;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleMenu;
    Toolbar toolbar;
    EditText etSearch;
    Button btnClearSearch;
    public static Context context;
    public static LinearLayout llUser, llLoginRequired;
    public static CircleImageView civAvatar;
    public static TextView tvFullName, tvLoginRequired, tvLogout;
    public static NavigationView navView;
    public static ViewPager slideBanner;
    public static Spinner spnTypeProduct;
    public static GridView gvProduct;
    public static String[] listUrlBanner;
    public static BannerAdapter bannerAdapter;
    public static Authentication auth;
    public static GetData data;
    public static ArrayList<LoaiSanPham> listLoaiSanPham;
    public static ArrayList<SanPham> listSanPham;
    public static ListProductAdapter adapterSanPham;
    public static ListTypeProductAdapter adapterLoaiSanPham;
    public static TaiKhoan tk;
    public static TextView tvNameInfoUser;
    public static TextView tvPhoneInfoUser;
    public static TextView tvAddressInfoUser;
    public static TextView tvEmailInfoUser;
    public static LinearLayout llInfoUser;
    public static ArrayList<ProductCart> listGioHang;
    public Button btnShowCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //Gọi hàm ánh xạ
        initView();
        //Tạo giỏ hàng mới
        listGioHang = new ArrayList<>();
        //Kiểm tra xem có đang kết nối internet hay không
        if (InternetStatus.isNetworkAvailable(this)) { //Có kết nối
            //Khởi tạo đối tượng Authentication
            auth = new Authentication(this);
            //Kiểm tra xem đã có tài khoản đăng nhập chưa
            auth.updateStatusLogged();
            //Khởi tạo đối tượng GetData để thực hiện việc lấy dữ liệu từ trên server
            data = new GetData(this);
        } else { //Không có kết nối
            Toast.makeText(this, R.string.lost_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    //Ánh xạ
    public void initView() {
        spnTypeProduct = (Spinner) findViewById(R.id.spnTypeProduct);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        etSearch = (EditText) findViewById(R.id.etSearch);
        btnClearSearch = (Button) findViewById(R.id.btnClearSearch);
        btnClearSearch.setVisibility(View.INVISIBLE);
        navView = (NavigationView) findViewById(R.id.navView);
        gvProduct = (GridView) findViewById(R.id.gvProduct);
        slideBanner = (ViewPager) findViewById(R.id.slideBanner);
        llUser = (LinearLayout) navView.getHeaderView(0).findViewById(R.id.llUser);
        llLoginRequired = (LinearLayout) navView.getHeaderView(0).findViewById(R.id.llLoginRequired);
        civAvatar = (CircleImageView) navView.getHeaderView(0).findViewById(R.id.civAvatar);
        civAvatar.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tvFullName = (TextView) navView.getHeaderView(0).findViewById(R.id.tvFullName);
        tvLoginRequired = (TextView) navView.getHeaderView(0).findViewById(R.id.tvLoginRequired);
        tvLogout = (TextView) navView.getHeaderView(0).findViewById(R.id.tvLogout);
        setSupportActionBar(toolbar);
        btnShowCart = (Button) findViewById(R.id.btnShowCart);
        tvNameInfoUser = (TextView) findViewById(R.id.tvNameInfoUser);
        tvAddressInfoUser = (TextView) findViewById(R.id.tvAddressInfoUser);
        tvPhoneInfoUser = (TextView) findViewById(R.id.tvPhoneInfoUser);
        tvEmailInfoUser = (TextView) findViewById(R.id.tvEmailInfoUser);
        llInfoUser = (LinearLayout) findViewById(R.id.llInfoUser);
        //Set icon toggle cho menu
        toggleMenu = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);
        drawerLayout.addDrawerListener(toggleMenu);
        toggleMenu.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Bắt sự kiện khung tìm kiếm thay đổi
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    btnClearSearch.setVisibility(View.INVISIBLE);
                } else {
                    btnClearSearch.setVisibility(View.VISIBLE);
                }
                adapterSanPham.filterSearch(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Bắt sự kiện nhấn xóa chuỗi tìm kiếm
        btnClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        //Bắt sự kiện click của spinner
        spnTypeProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapterSanPham.filterType(listLoaiSanPham.get(i).getMaLoai());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tvLoginRequired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.logout();
            }
        });
        //bắt sự kiện click item của GridView
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailProductActivity.class);
                intent.putExtra("product", listSanPham.get(i));
                startActivity(intent);
            }
        });
        //Nhấn nút xem giỏ hàng
        btnShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("data", listGioHang);
                startActivity(intent);
            }
        });
    }

    //Thêm sản phẩm vào giỏ hàng
    public static void addProductToCart(SanPham sp) {
        if (auth.isLogged()) {
            Boolean exist = false; //Kiểm tra một sản phẩm đã tồn tại hay chưa
            for (int i = 0; i < listGioHang.size(); i++) {
                ProductCart productCart = listGioHang.get(i);
                if (productCart.getSanPham().equals(sp)) {
                    productCart.setSoLuong(productCart.getSoLuong() + 1);
                    exist = true;
                    return;
                }
            }
            if (exist == false) {
                listGioHang.add(new ProductCart(sp, 1));
            }

            Toast.makeText(context, "Đã thêm " + sp.getTenSanPham() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            Log.d("bbb", listGioHang.size()+"");
        } else {
            Toast.makeText(context, "Vui lòng đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggleMenu.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
