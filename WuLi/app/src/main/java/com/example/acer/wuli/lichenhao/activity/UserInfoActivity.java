package com.example.acer.wuli.lichenhao.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.wuli.R;
import com.example.acer.wuli.model.User;

//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;

public class UserInfoActivity extends AppCompatActivity {

    private TextView userName;
    private EditText userNickname;
    private TextView phone;
    private ImageView toolbarImageView;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private FloatingActionButton edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        toolbar = findViewById(R.id.toolbar);
        collapsingToolbar =  findViewById(R.id.collapsing_toolbar);
        toolbarImageView = findViewById(R.id.iv_title);
        userNickname = findViewById(R.id.user_nickname);
        userName = findViewById(R.id.user_name);
        phone = findViewById(R.id.phone);
        edit = findViewById(R.id.edit_user_info);

        String user_background = getIntent().getStringExtra("url");
        final int user_ID = MainActivity.user_ID;
        String userNickName = getIntent().getStringExtra("userNickName");
        String user_phone = getIntent().getStringExtra("phone");

        //对个人信息界面的初始化
        userName.setText(String.valueOf(user_ID));
        phone.setText(user_phone);
        userNickname.setText(userNickName);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("WULI电影");

        if (user_background.equals("")){
            Resources resources  = getResources();
            Drawable drawable = resources.getDrawable(R.drawable.yokino);
            toolbarImageView.setBackground(drawable);
        }else {
            Bitmap bmp= BitmapFactory.decodeFile(user_background);
            Drawable drawable =new BitmapDrawable(bmp);
            toolbarImageView.setBackground(drawable);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNickname.setEnabled(true);
                Snackbar.make(view, "确定保存修改吗?", Snackbar.LENGTH_LONG)
                        .setAction("保存", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String user_nickname = userNickname.getText().toString();
                                // TODO: 2019/1/15 将修改的信息存入数据库
                                User user = new User();
                                user.setNickName(user_nickname);
                                user.update(user_ID);

                                Intent intent = new Intent();
                                intent.putExtra("userNickName",user_nickname);
                                UserInfoActivity.this.setResult(2019,intent);

                                Toast.makeText(UserInfoActivity.this,"修改信息成功",Toast.LENGTH_SHORT).show();
                                userNickname.setEnabled(false);
                            }
                        }).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
