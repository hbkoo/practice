package com.example.acer.wuli.login.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.acer.wuli.R;
import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.login.app.User;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    //声明
    private Button btn_login;
    private ImageView iv_eye;
    private ImageView iv_account;
    private ImageView iv_password;
    private TextView tv_forget;
    private TextView tv_register;
    private EditText et_account;
    private EditText et_password;
    private CheckBox cb_check;
    private boolean canSee = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        iv_account = findViewById(R.id.iv_account);
        et_account = findViewById(R.id.et_account);
        et_account.setOnFocusChangeListener(this);
        iv_password = findViewById(R.id.iv_password);
        et_password = findViewById(R.id.et_password);
        et_password.setOnFocusChangeListener(this);

        btn_login = findViewById(R.id.btn_login);
        iv_eye = findViewById(R.id.iv_eye);
        tv_forget = findViewById(R.id.tv_forget);
        tv_register = findViewById(R.id.tv_register);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        cb_check = findViewById(R.id.cb_check);
        //监视器
        iv_eye.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        //复选框默认选中
        cb_check.setChecked(true);

        //记住用户名和密码
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String saved_username = sp.getString("USERNAME", ""); //获取sp里面存储的数据
        String saved_password = sp.getString("PASSWORD", "");

        //Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        et_account.setText(saved_username);//将sp中存储的username写入EditeText
        et_password.setText(saved_password);//将sp中存储的password写入EditeText

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_eye:

                if (canSee == false) {
                    //如果是不能看到密码的情况下
                    et_password.setInputType(0x81);//可见
                    iv_eye.setImageResource(R.drawable.eye_grey);
                    canSee = true;
                } else {
                    //如果是能看到密码的状态下
                    et_password.setInputType(0x90);//不可见
                    iv_eye.setImageResource(R.drawable.eye_blue);
                    canSee = false;
                }
                break;
            case R.id.btn_login:
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();

                //验证
                if (account.equals("") || account == null) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                }
                if (password.equals("") || password == null) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }

                //验证数据库中用户信息
                List<User> users1 = LitePal.where("phoneNumber=?", account).find(User.class);
                List<User> users2 = LitePal.where("phoneNumber=? and password=?", account, password).find(User.class);
                if (users1.size() == 0) {
                    Toast.makeText(this, "账号不存在", Toast.LENGTH_SHORT).show();
                } else if (users2.size() == 0) {
                    Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences.Editor sp = getSharedPreferences("user", Context.MODE_PRIVATE).edit();
                    if (cb_check.isChecked()) {
                        sp.putString("USERNAME", et_account.getText().toString());
                        sp.putString("PASSWORD", et_password.getText().toString());
                        sp.putBoolean("check", true);
                        sp.commit();
                    } else {
                        sp.clear();
                    }
                    sp.apply();
                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Log.i("test", String.valueOf(users2.get(0).getID()));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userID", users2.get(0).getID());
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_forget:
                Intent intent1 = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_register:
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_account:
                et_account.setHint("");
                et_password.setHint("请输入密码");
                iv_account.setImageResource(R.drawable.people_blue);
                iv_password.setImageResource(R.drawable.suo_grey);
                break;
            case R.id.et_password:
                et_account.setHint("请输入用户名/邮箱/手机号");
                et_password.setHint("");
                iv_password.setImageResource(R.drawable.suo_blue);
                iv_account.setImageResource(R.drawable.people_grey);
                break;
        }
    }
}
