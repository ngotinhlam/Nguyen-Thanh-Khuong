package com.khuong.thanh.nguyen.banhoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import object.SanPham;


public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack, ivProduct;
    TextView tvName, tvDescription, tvPrice;
    Button btnCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();

        Intent intent = getIntent();
        SanPham sp = (SanPham) intent.getExtras().getSerializable("product");

        tvName.setText(sp.getTenSanPham());
        tvDescription.setText(sp.getMoTa());
        tvPrice.setText(String.format("%,d VNĐ", sp.getDonGia()).replace(",", "."));
        String urlAnhSanPham = getString(R.string.url_host) + getString(R.string.url_anh_san_pham) + sp.getHinhAnh() + ".jpg";
        Picasso.with(this).load(urlAnhSanPham).into(ivProduct);///lay anh tu mot url
    }

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackDetail);
        ivProduct = (ImageView) findViewById(R.id.ivProductDetail);
        tvName = (TextView) findViewById(R.id.tvNameProductDetail);
        tvDescription = (TextView) findViewById(R.id.tvDescriptionProductDetail);
        tvPrice = (TextView) findViewById(R.id.tvPriceProductDetail);
        btnCart = (Button) findViewById(R.id.btnCartDetail);
        ivBack.setOnClickListener(this);
        btnCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackDetail:
                finish();
                break;
            case R.id.btnCartDetail:
                break;
        }
    }
}
