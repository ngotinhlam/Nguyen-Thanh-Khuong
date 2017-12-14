package com.khuong.thanh.nguyen.banhoa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import dialog.DialogNotification;
import handle.RegisterAccount;
import handle.Validate;

public class RegisterAccountActivity extends AppCompatActivity {

    Button btnBack, btnRegister;
    public static EditText etUsername, etPassword, etRetypePassword, etFullName, etEmail, etAdress, etPhone;
    public static CircleImageView civAvatar;
    RegisterAccount register;
    public static DialogNotification dialogNotification;
    public static Bitmap bitmapAvatar = null;
    public static int REQUEST_CODE_CAMERA = 111, REQUEST_CODE_GALLERY = 222, REQUEST_CODE_CROP = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        initView();

        dialogNotification = new DialogNotification(this);
        register = new RegisterAccount(this);
    }

    public void initView() {
        btnBack = (Button) findViewById(R.id.btnBackRegister);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etUsername = (EditText) findViewById(R.id.etUsernameRegister);
        etPassword = (EditText) findViewById(R.id.etPasswordRegister);
        etRetypePassword = (EditText) findViewById(R.id.etRetypePasswordRegister);
        etFullName = (EditText) findViewById(R.id.etFullNameRegister);
        etEmail = (EditText) findViewById(R.id.etEmailRegister);
        etAdress = (EditText) findViewById(R.id.etAddressRegister);
        etPhone = (EditText) findViewById(R.id.etPhoneRegister);
        civAvatar = (CircleImageView) findViewById(R.id.civAvatarRegister);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = etFullName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String retypepassword = etRetypePassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAdress.getText().toString().trim();

                if (fullname.equals("") || username.equals("") || password.equals("") || retypepassword.equals("") || email.equals("") || phone.equals("") || address.equals("")) {
                    dialogNotification.showMessage("Bạn phải nhập đầy đủ thông tin");
                    return;
                }
                if (!Validate.isValidUsername(username)) {
                    dialogNotification.showMessage("Tên tài khoản không hợp lệ");
                    return;
                }
                if (!Validate.isValidPassword(password)) {
                    dialogNotification.showMessage("Password phải từ 6 kí tự trở lên");
                    return;
                }
                if (!password.equals(retypepassword)) {
                    dialogNotification.showMessage("Nhập lại mật khẩu không đúng");
                    return;
                }
                if (!Validate.isValidEmail(email)) {
                    dialogNotification.showMessage("Email không hợp lệ");
                    return;
                }
                if (!Validate.isValidPhoneNumber(phone)) {
                    dialogNotification.showMessage("Số điện thoại không hợp lệ");
                    return;
                }

                register.register(username, password, fullname, email, address, phone, bitmapAvatar);
            }
        });

        civAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gọi tới intent Crop ảnh
                CropImage
                        .activity(null) //Uri của ảnh muốn crop (để null thì sẽ cho phép chọn ảnh trước khi crop)
                        .setAspectRatio(1,1) //Tỷ lệ crop ảnh 1:1
                        .setGuidelines(CropImageView.Guidelines.ON) //Hiện đường lưới
                        .setCropShape(CropImageView.CropShape.OVAL) //Hình dáng crop là hình tròn
                        .start(RegisterAccountActivity.this);
            }
        });
    }

    public static void clearInfo() {
        etFullName.setText("");
        etUsername.setText("");
        etPassword.setText("");
        etRetypePassword.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etAdress.setText("");
        civAvatar.setImageResource(R.drawable.icon_user);
        bitmapAvatar = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmapAvatar = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    civAvatar.setImageBitmap(bitmapAvatar);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("error", e.getMessage());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("error", error.toString());
            }
        }
    }
}
