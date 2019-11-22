package com.example.acer.wuli.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.wuli.R;
import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.login.app.User;
import com.mob.MobSDK;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    private ImageView iv_back;
    private ImageView iv_account;
    private ImageView iv_password1;
    private ImageView iv_password2;
    private ImageView iv_code;
    private EditText et_account;
    private EditText et_password1;
    private EditText et_password2;
    private EditText et_code;
    private ImageView iv_eye1;
    private ImageView iv_eye2;
    private Button btn_code;
    private Button btn_register;
    private boolean canSee1 = true;
    private boolean canSee2 = true;
    private EventHandler eventHandler;
    private int num = 60;
    private boolean canGetCode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        iv_account = findViewById(R.id.iv_account);
        iv_password1 = findViewById(R.id.iv_password1);
        iv_password2 = findViewById(R.id.iv_password2);
        et_account = findViewById(R.id.et_account);
        et_password1 = findViewById(R.id.et_password1);
        et_password2 = findViewById(R.id.et_password2);
        et_code = findViewById(R.id.et_code);
        et_account.setOnFocusChangeListener(this);
        et_password1.setOnFocusChangeListener(this);
        et_password2.setOnFocusChangeListener(this);
        et_code.setOnFocusChangeListener(this);
        iv_code = findViewById(R.id.iv_code);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_eye1 = findViewById(R.id.iv_eye1);
        iv_eye1.setOnClickListener(this);
        iv_eye2 = findViewById(R.id.iv_eye2);
        iv_eye2.setOnClickListener(this);

        btn_code = findViewById(R.id.btn_code);
        btn_register = findViewById(R.id.btn_register);
        btn_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        //编辑框
        et_account = findViewById(R.id.et_account);
        et_password1 = findViewById(R.id.et_password1);
        et_password2 = findViewById(R.id.et_password2);
        et_code = findViewById(R.id.et_code);
        InitMob();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_eye1:
                if (canSee1 == false) {
                    //如果是不能看到密码的情况下
                    et_password1.setInputType(0x81);//可见
                    iv_eye1.setImageResource(R.drawable.eye_grey);
                    canSee1 = true;
                } else {
                    //如果是能看到密码的状态下
                    et_password1.setInputType(0x90);//不可见
                    iv_eye1.setImageResource(R.drawable.eye_blue);
                    canSee1 = false;
                }
                break;
            case R.id.iv_eye2:
                if (canSee2 == false) {
                    //如果是不能看到密码的情况下
                    et_password2.setInputType(0x81);//可见
                    iv_eye2.setImageResource(R.drawable.eye_grey);
                    canSee2 = true;
                } else {
                    //如果是能看到密码的状态下
                    et_password2.setInputType(0x90);//不可见
                    iv_eye2.setImageResource(R.drawable.eye_blue);
                    canSee2 = false;
                }
                break;
            case R.id.btn_code:
                //获取验证码
                canGetCode = false;  // 设置获取验证码按钮不可点击
                String tel = et_account.getText().toString();
                // TODO 判断手机号合法
                if (!checkPhoneValid(tel)) {
                    Toast.makeText(RegisterActivity.this,"请输入合法的手机号",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 获取验证码
                SMSSDK.getVerificationCode("86", tel);
                // 更新按钮倒计时60秒
                updateCodeBtn();  //已发送验证码，更新界面显示
                break;
            case R.id.btn_register:
                //注册按钮
                String account = et_account.getText().toString();
                String password1 = et_password1.getText().toString();
                String password2 = et_password2.getText().toString();
                String code = et_code.getText().toString();

                //验证
                if (account.equals("") || account == null) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
                }
                if (password1.equals("") || password1 == null) {
                    Toast.makeText(this, "输入密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if (password2.equals("") || password2 == null) {
                    Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if (!password1.equals(password2)) {
                    Toast.makeText(this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                }
                if (code.equals("") || code == null) {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                // 确认用户输入的验证码
                SMSSDK.submitVerificationCode("86", et_account.getText().toString(),
                        et_code.getText().toString());
                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_account:
                et_account.setHint("");
                et_password1.setHint("请输入登录密码");
                et_password2.setHint("请确认登录密码");
                et_code.setHint("请输入验证码");
                iv_account.setImageResource(R.drawable.people_blue);
                iv_password1.setImageResource(R.drawable.suo_grey);
                iv_password2.setImageResource(R.drawable.suo_grey);
                iv_code.setImageResource(R.drawable.yaoshi_grey);
                break;
            case R.id.et_password1:
                et_account.setHint("请输入手机号");
                et_password1.setHint("");
                et_password2.setHint("请确认登录密码");
                et_code.setHint("请输入验证码");
                iv_password1.setImageResource(R.drawable.suo_blue);
                iv_account.setImageResource(R.drawable.people_grey);
                iv_password2.setImageResource(R.drawable.suo_grey);
                iv_code.setImageResource(R.drawable.yaoshi_grey);
                break;
            case R.id.et_password2:
                et_account.setHint("请输入手机号");
                et_password1.setHint("请输入登录密码");
                et_password2.setHint("");
                et_code.setHint("请输入验证码");
                iv_password2.setImageResource(R.drawable.suo_blue);
                iv_account.setImageResource(R.drawable.people_grey);
                iv_password1.setImageResource(R.drawable.suo_grey);
                iv_code.setImageResource(R.drawable.yaoshi_grey);
                break;
            case R.id.et_code:
                et_account.setHint("请输入手机号");
                et_password1.setHint("请输入登录密码");
                et_password2.setHint("请确认登录密码");
                et_code.setHint("");
                iv_code.setImageResource(R.drawable.yaoshi_blue);
                iv_account.setImageResource(R.drawable.people_grey);
                iv_password1.setImageResource(R.drawable.suo_grey);
                iv_password2.setImageResource(R.drawable.suo_grey);
                break;
        }
    }

    private void InitMob() {
        MobSDK.init(this);  // 初始化MobSDK

        SMSSDK.setAskPermisionOnReadContact(true);

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                // TODO 验证码未发送

                                ((Throwable) data).printStackTrace();
                                Log.e("SMSSDK:", "验证码未发送，出错" + data);
                                Toast.makeText(RegisterActivity.this, "验证码未发送...", Toast.LENGTH_SHORT).show();
                            }

                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                            if (result == SMSSDK.RESULT_COMPLETE) {

                                // TODO 用户验证成功
                                judge();

                            } else {

                                // TODO 用户验证失败

                                ((Throwable) data).printStackTrace();
                                Toast.makeText(RegisterActivity.this, "验证码匹配错误！",
                                        Toast.LENGTH_SHORT).show();
                                Log.e("SMSSDK:", "验证码处理错误，出错" + data);

                            }

                        }
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

    }

    private void updateCodeBtn() {
        num = 60;
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (!canGetCode) {
                    while (num > 0) {
                        num--;
                        RegisterActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_code.setText("获取验证码（" + num + "）");
                                btn_code.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                canGetCode = true;
                num = 60;
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_code.setText("获取验证码");
                        btn_code.setClickable(true);
                    }
                });

            }
        };
        thread.start();

    }

    // 简单判断输入的手机号是否合法
    private boolean checkPhoneValid(String phoneNumber) {
        String expression = "((^(13|15|18|17|14)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNumber);
        String phone = "[1][34578]\\d{9}";
        return matcher.matches();
    }

    private void judge() {
        String account = et_account.getText().toString();
        String password1 = et_password1.getText().toString();

        List<User> users1 = LitePal.where("phoneNumber=?", account).find(User.class);
        if (users1.size() == 0) {

            //将数据写入数据库
            User user = new User();
            user.setPhoneNumber(account);
            user.setPassword(password1);
            user.save();
            List<User> users2 = LitePal.where("phoneNumber=?", account).find(User.class);
            Log.i("test", String.valueOf(users2.get(0).getID()));
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

            finish();

        } else {
            Toast.makeText(RegisterActivity.this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
        }
    }
}
