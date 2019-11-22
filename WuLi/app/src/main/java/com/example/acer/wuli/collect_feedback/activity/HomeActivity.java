package com.example.acer.wuli.collect_feedback.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.acer.wuli.collect_feedback.fragment.LikeFragment;
import com.example.acer.wuli.R;
import com.example.acer.wuli.model.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class HomeActivity extends AppCompatActivity {


    // 碎片管理器
    private android.support.v4.app.FragmentManager fragmentManager = null;

    private LikeFragment homeFragment = null;
    private LikeFragment bookFragment = null;
    private LikeFragment friendFragment = null;
    private LikeFragment personFragment = null;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.iv_home)
    private ImageView iv_home;

    @ViewInject(R.id.iv_book)
    private ImageView iv_book;

    @ViewInject(R.id.iv_friend)
    private ImageView iv_friend;

    @ViewInject(R.id.iv_person)
    private ImageView iv_person;

    private int current_index = -1;   // 当前界面显示的fragment的序号

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        x.view().inject(this);

        changeFragment(1);  // 显示默认的第一的界面

    }

    @Event(value = {R.id.iv_home, R.id.iv_book, R.id.iv_friend, R.id.iv_person})
    private void onclick(View view) {

        switch (view.getId()) {
            case R.id.iv_home:
                changeFragment(1);
                break;
            case R.id.iv_book:
                changeFragment(2);
                break;
            case R.id.iv_friend:
                changeFragment(3);
                break;
            case R.id.iv_person:
                changeFragment(4);
                break;
        }

    }

    // 修改点击界面fragment碎片
    private void changeFragment(int selected_index) {

        // 用户点击的界面与当前显示的界面一致，不进行更新
        if (selected_index == current_index) {
            return;
        }

        // 更改前一个界面的点击图标
        switch (current_index) {
            case 1:
                iv_home.setImageResource(R.drawable.dark_heart);
                break;
            case 2:
                iv_book.setImageResource(R.drawable.dark_heart);
                break;
            case 3:
                iv_friend.setImageResource(R.drawable.dark_heart);
                break;
            case 4:
                iv_person.setImageResource(R.drawable.dark_heart);
                break;
        }

        // 获取碎片管理器
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        // 开启一个事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (selected_index) {
            case 1:
                if (homeFragment == null) {
                    homeFragment = new LikeFragment();
                }
                transaction.replace(R.id.fl_content,homeFragment);
                tv_title.setText("首页");
                iv_home.setImageResource(R.drawable.comment);
                break;
            case 2:
                if (bookFragment == null) {
                    bookFragment = new LikeFragment();
                }
                transaction.replace(R.id.fl_content,bookFragment);
                tv_title.setText("书籍分类");
                iv_book.setImageResource(R.drawable.comment);
                break;
            case 3:
                if (friendFragment == null) {
                    friendFragment = new LikeFragment();
                }
                transaction.replace(R.id.fl_content,friendFragment);
                tv_title.setText("书友圈");
                iv_friend.setImageResource(R.drawable.comment);
                break;
            case 4:
                if (personFragment == null) {
                    personFragment = new LikeFragment();
                }
                transaction.replace(R.id.fl_content,personFragment);
                tv_title.setText("个人中心");
                iv_person.setImageResource(R.drawable.comment);
                break;
        }
        // 提交事务
        transaction.commit();
        // 修改界面中当前的界面为用户选中的界面
        current_index = selected_index;
    }


    public User getUser(){
        return new User(001,"123","15927037762","龙息在天");
    }


}
