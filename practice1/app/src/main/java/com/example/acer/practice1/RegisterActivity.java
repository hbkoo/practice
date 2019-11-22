package com.example.acer.practice1;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean canGetCode = true;  // 用户是否已经点击获取验证码按钮
    private int num = 60;               // 用户下次可以点击获取验证码的剩余时间

    private EditText et_tel,et_code;
    private Button btn_code,btn_register;

    private EventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Init();
    }

    private void Init() {

        et_tel = findViewById(R.id.et_tel);
        et_code = findViewById(R.id.et_code);
        btn_code = findViewById(R.id.btn_code);
        btn_register = findViewById(R.id.btn_register);

        btn_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        InitMob();

    }

    // 初始化获取验证码的SDK
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
                              // TODO
                              // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                              updateCodeBtn();  //已发送验证码，更新界面显示
                              Toast.makeText(RegisterActivity.this,"验证码已发送...",
                                      Toast.LENGTH_SHORT).show();

                          } else {
                              ((Throwable) data).printStackTrace();
                              Log.e("SMSSDK:","验证码未发送，出错" + data);
                              Toast.makeText(RegisterActivity.this,"请检查网络连接...",Toast.LENGTH_SHORT).show();
                          }
                      } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                          if (result == SMSSDK.RESULT_COMPLETE) {
                              checkCodeSuccess(); //用户验证成功
                          } else {
                              ((Throwable) data).printStackTrace();
                              Toast.makeText(RegisterActivity.this,"验证码匹配错误！",
                                      Toast.LENGTH_SHORT).show();
                              Log.e("SMSSDK:","验证码处理错误，出错" + data);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                canGetCode = false;
                String tel = et_tel.getText().toString().trim();
                if (!checkPhoneValid(tel)) {
                    Toast.makeText(this,"请输入有效的手机号！",Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.getVerificationCode("86",tel);
                break;
            case R.id.btn_register:
                String input_code = et_code.getText().toString();

                SMSSDK.submitVerificationCode("86",et_tel.getText().toString(),input_code);

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    // 服务器发送验证码，更新界面btn的显示
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

    // 服务器验证成功，更新内存变量，这是在子线程中，界面的更新需要转到UI线程
    private void checkCodeSuccess() {
        num = 0;
        Toast.makeText(RegisterActivity.this,"注册成功",
                Toast.LENGTH_SHORT).show();
    }


    // 简单判断输入的手机号是否合法
    private boolean checkPhoneValid(String phoneNumber){
        String expression = "((^(13|15|18|17|14)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNumber);
        String phone = "[1][34578]\\d{9}";
        return matcher.matches();
    }


}
