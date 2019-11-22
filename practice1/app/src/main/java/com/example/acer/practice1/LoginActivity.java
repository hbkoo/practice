package com.example.acer.practice1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn;
    private EditText username_et;
    private EditText password_et;
    private TextView forget_pwd_tv,register_tv;
    private CheckBox remember_pwd_cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();

    }

    private void Init() {
        login_btn = findViewById(R.id.login_btn);
        username_et = findViewById(R.id.user_et);
        password_et = findViewById(R.id.pwd_et);
        forget_pwd_tv = findViewById(R.id.forget_pwd_tv);
        register_tv = findViewById(R.id.register_tv);
        remember_pwd_cb = findViewById(R.id.remember_pwd_cb);

        login_btn.setOnClickListener(this);
        forget_pwd_tv.setOnClickListener(this);
        register_tv.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
        String username = preferences.getString("account","");
        String password = preferences.getString("password","");
        username_et.setText(username);
        password_et.setText(password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                Login();
                break;
            case R.id.forget_pwd_tv:
                Toast.makeText(this,"找回密码...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_tv:
                Toast.makeText(this,"用户注册...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void Login() {

        String username = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        if (username.equals("") || password == null) {
            Toast.makeText(this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("") || username == null) {
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals("admin") && password.equals("123")) {

            if (remember_pwd_cb.isChecked()) {
                SharedPreferences.Editor editor = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
                editor.putString("account", username);
                editor.putString("password", password);
                editor.apply();
            }


            Toast.makeText(this,"登录成功!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("account",username);
            intent.putExtra("password",password);
            startActivity(intent);
        } else {
            Toast.makeText(this,"用户名和密码不匹配！",Toast.LENGTH_SHORT).show();
        }

    }



}
