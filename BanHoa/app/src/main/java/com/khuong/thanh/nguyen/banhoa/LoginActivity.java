package com.khuong.thanh.nguyen.banhoa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dialog.DialogNotification;
import handle.Authentication;
import handle.Validate;


public class LoginActivity extends AppCompatActivity {

    Button btnBack, btnLogin;
    TextView tvRegister;
    EditText etUsername, etPassword;
    Authentication auth;
    DialogNotification dialogNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        dialogNotification = new DialogNotification(this);
        auth = new Authentication(this);
    }

    public void initView() {
        btnBack = (Button) findViewById(R.id.btnBackLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegisterLogin);
        etUsername = (EditText) findViewById(R.id.etUsernameLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (!Validate.isValidUsername(username)) {
                    dialogNotification.showMessage("Tên tài khoản không hợp lệ");
                    return;
                }
                if (password.trim().equals("")) {
                    dialogNotification.showMessage("Hãy nhập mật khẩu");
                    return;
                }
                auth.login(username, password);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterAccountActivity.class));
            }
        });
    }
}
