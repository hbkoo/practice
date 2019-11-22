package com.example.acer.practice2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_add_address)
public class AddAddressActivity extends AppCompatActivity {

    @ViewInject(R.id.et_name)
    private EditText et_name;

    @ViewInject(R.id.et_tel)
    private EditText et_tel;

    @ViewInject(R.id.et_address)
    private EditText et_address;

    @ViewInject(R.id.et_detail_address)
    private EditText et_detail_address;


    @ViewInject(R.id.btn_save)
    private Button btn_save;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    private Boolean editTag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        x.view().inject(this);
        Init();
    }

    private void Init() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String tel = intent.getStringExtra("tel");
        String address = intent.getStringExtra("address");
        String detailAddress = intent.getStringExtra("detailAddress");
        editTag = intent.getBooleanExtra("editTag",false);
        et_name.setText(username);
        et_tel.setText(tel);
        et_address.setText(address);
        et_detail_address.setText(detailAddress);

        if (editTag) {
            tv_title.setText("修改地址");
            btn_save.setText("修改并保存");
        }

    }


    @Event(value = {R.id.iv_back,R.id.btn_save})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_save:
                save();
                break;
        }
    }

    //保存按钮事件处理逻辑
    private void save() {
        String username = et_name.getText().toString().trim();
        String tel = et_tel.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        String detailAddress = et_detail_address.getText().toString().trim();

        if (username.equals("")){
            Toast.makeText(this,"收货人不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        if (tel.equals("")){
            Toast.makeText(this,"手机号码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        } else if (!checkPhoneValid(tel)) {
            Toast.makeText(this,"请输入合法的手机号！",Toast.LENGTH_SHORT).show();
            return;
        }

        if (address.equals("")){
            Toast.makeText(this,"所在地区不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        if (detailAddress.equals("")){
            Toast.makeText(this,"详细地址不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (editTag) {
            Toast.makeText(this,"修改成功！",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"新增成功！",Toast.LENGTH_SHORT).show();
        }

        Log.i("AddAddressActivity", "收货人：" + username);
        Log.i("AddAddressActivity", "手机号码：" + tel);
        Log.i("AddAddressActivity", "所在地区：" + address);
        Log.i("AddAddressActivity", "详细地址：" + detailAddress);
        Intent intent = getIntent();
        intent.putExtra("username",username);
        intent.putExtra("tel",tel);
        intent.putExtra("address",address);
        intent.putExtra("detailAddress",detailAddress);
        setResult(RESULT_OK,intent);
        finish();
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
