package com.example.acer.practice1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.practice1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("account");
        String password = intent.getStringExtra("password");

        TextView tv_main = findViewById(R.id.tv_main);
        tv_main.setText("用户名：" + username + "\n密码：" + password);
        Toast.makeText(this,"主界面中获取用户名为：" + username + "\n密码为：" + password,
                Toast.LENGTH_SHORT).show();

    }


}
