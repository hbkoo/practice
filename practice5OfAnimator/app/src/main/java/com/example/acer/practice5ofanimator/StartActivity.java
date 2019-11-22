package com.example.acer.practice5ofanimator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    @ViewInject(R.id.rl_main_layout)
    private RelativeLayout rl_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_start);
        x.view().inject(this);

        loadTime();
        loadAnimate();


    }

    private void loadAnimate() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f,1.0f);
        alphaAnimation.setDuration(3000);
        rl_main_layout.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));

                // 两个参数：第一个进入效果，第二个退出效果
                overridePendingTransition(R.anim.in_right,
                        R.anim.out_left);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    // 加载时间
    private void loadTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weeks = "星期";
        switch (week) {
            case 1:
                weeks += "一";
                break;
            case 2:
                weeks += "二";
                break;
            case 3:
                weeks += "三";
                break;
            case 4:
                weeks += "四";
                break;
            case 5:
                weeks += "五";
                break;
            case 6:
                weeks += "六";
                break;
            case 7:
                weeks += "日";
                break;
        }

        tv_time.setText("地球时间：" + year + "年" + (month + 1) + "月" + day + "日 " + weeks);
    }


}
