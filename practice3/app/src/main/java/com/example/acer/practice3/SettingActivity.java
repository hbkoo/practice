package com.example.acer.practice3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        x.view().inject(this);

    }


    @Event(value = {R.id.iv_back, R.id.ll_change_pwd, R.id.ll_delete_cache, R.id.ll_update,
            R.id.ll_about_us, R.id.ll_contact_us, R.id.ll_opinion_return})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_change_pwd:
                Toast.makeText(this,"修改密码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_delete_cache:
                Toast.makeText(this,"清除缓存",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_update:
                Toast.makeText(this,"版本检查",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_about_us:
                Toast.makeText(this,"关于我们",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_contact_us:
                Toast.makeText(this,"联系我们",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_opinion_return:
                Toast.makeText(this,"意见反馈",Toast.LENGTH_SHORT).show();
                break;


        }
    }

}
