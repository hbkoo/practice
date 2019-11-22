package com.example.acer.wuli.lichenhao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.example.acer.wuli.Application.SysApplication;
import com.example.acer.wuli.R;
import com.example.acer.wuli.collect_feedback.activity.AboutActivity;
import com.example.acer.wuli.collect_feedback.activity.ReturnActivity;
import com.example.acer.wuli.collect_feedback.fragment.LikeFragment;
import com.example.acer.wuli.history_hot.activity.HistoryActivity;
import com.example.acer.wuli.history_hot.activity.HotFragment;
import com.example.acer.wuli.main.fragment.HomePageFragment;
import com.example.acer.wuli.main.fragment.SearchPageFragment;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private ImageView iv_bottom_homepage;
    private ImageView iv_bottom_search;
    private ImageView iv_bottom_hot;
    private ImageView iv_bottom_like;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment homepage = new HomePageFragment();
    private Fragment searchPage;
    private LikeFragment likeFragment;
    private HotFragment hotFragment;


    private long mExitTime;//两次返回键的时间

    private NavigationView navigationView;
    private CircleImageView userPhoto; //用户头像
    private TextView userName;  //用户昵称

    private String userBackground;//用户设置的背景地址
    public static int user_ID;//传入的用户ID
    private User user;//用户实例

    private View headView;

    DrawerLayout drawer;

    private Toolbar toolbar_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);

        init();  // 初始化主页界面

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorTheme));
        }

        toolbar_menu = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar_menu);
        toolbar_menu.setBackgroundColor(Color.parseColor("#FF5D7A"));


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar_menu, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        headView = navigationView.getHeaderView(0);
        userName = headView.findViewById(R.id.user_name);
        userPhoto = headView.findViewById(R.id.user_photo);

        //从登陆或注册界面传递给主界面userID
        user_ID = getIntent().getIntExtra("userID",0);
        //从数据库查询得到该ID对应的用户昵称,不为空则直接写入，否则默认
        user = LitePal.find(User.class,user_ID);
        if (!"".equals(user.getNickName())){
            userName.setText(user.getNickName());
        }

        SharedPreferences userImage = getSharedPreferences("userImage" + user_ID, 0);
        String user_photo = userImage.getString("user_photo", "");
        userBackground = userImage.getString("user_background", "");
        if (!user_photo.equals("")) {
            Bitmap bmp = BitmapFactory.decodeFile(user_photo);
            userPhoto.setImageBitmap(bmp);
        }
        if (!userBackground.equals("")) {
            Bitmap bmp1 = BitmapFactory.decodeFile(userBackground);
            Drawable drawable = new BitmapDrawable(bmp1);
            headView.setBackground(drawable);
        }


        //用户点击导航栏头部，跳转到个人信息界面
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019/1/14 跳转个人信息界面
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                intent.putExtra("phone",user.getPhoneNumber());
                intent.putExtra("userNickName",user.getNickName());
                intent.putExtra("url", userBackground);
                startActivityForResult(intent, 1996);
            }
        });

        //用户长按点击导航栏头部，更换背景图片
        headView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(MainActivity.this, 5678); // 打开相册
                return true;
            }
        });

        //用户点击头像的事件监听
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(MainActivity.this, 1234); // 打开相册

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    //Activity进入后台时回调
    @Override
    protected void onStop() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onStop();
    }

    //处理两个返回键，导航栏展开按返回键则关闭导航栏，导航栏没展开两次点击返回键则退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            SysApplication.getInstance().exit();
        }
    }

    //导航栏的事件监听
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.history) {
            // TODO: 2019/1/14  历史
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        } else if (id == R.id.feedback) {
            // TODO: 2019/1/14 反馈
            startActivity(new Intent(MainActivity.this, ReturnActivity.class));
        } else if (id == R.id.about_us) {
            // TODO: 2019/1/14 关于我们
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.contact_us) {
            // TODO: 2019/1/14 联系我们
            contactUs();
        } else if (id == R.id.version_update) {
            upgrade();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && data != null) {
            //获取选择器返回的数据,设置头像
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);

            SharedPreferences user_photo = getSharedPreferences("userImage" + user_ID, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = user_photo.edit();
            editor.putString("user_photo", images.get(0));
            editor.apply();

            Bitmap bmp = BitmapFactory.decodeFile(images.get(0));
            userPhoto.setImageBitmap(bmp);
        } else if (requestCode == 5678 && data != null) {
            //获取选择器返回的数据，设置背景
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);

            SharedPreferences user_background = getSharedPreferences("userImage" + user_ID, 0);
            SharedPreferences.Editor editor = user_background.edit();
            editor.putString("user_background", images.get(0));
            editor.apply();

            userBackground = images.get(0);

            Bitmap bmp = BitmapFactory.decodeFile(images.get(0));
            Drawable drawable = new BitmapDrawable(bmp);
            headView.setBackground(drawable);
        } else if (requestCode == 1996 && resultCode == 2019 && data != null) {
            if (!data.getStringExtra("userNickName").equals("")) {
                User user1 = new User();
                user1.setNickName(data.getStringExtra("userNickName"));
                user1.update(user_ID);
                user.setNickName(data.getStringExtra("userNickName"));
                userName.setText(data.getStringExtra("userNickName"));
            } else {
            }
        }
    }

    // 版本更新检测
    private void upgrade() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_upgrade, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.setCancelable(false);
        ImageView iv_loading = view.findViewById(R.id.iv_load);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 1000,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(4000);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog.dismiss();
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(MainActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        iv_loading.startAnimation(rotateAnimation);
    }

    // 联系我们
    private void contactUs() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        final TextView tv_tel = view.findViewById(R.id.tv_tel);
        tv_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "打电话", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Intent.ACTION_DIAL,
//                        Uri.parse("tel:" + tv_tel.getText().toString()));
//                startActivity(intent);


            }
        });

    }


    private void init() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fl_content, homepage);
        transaction.commit();

        iv_bottom_homepage = findViewById(R.id.iv_bottom_homepage);
        iv_bottom_search = findViewById(R.id.iv_bottom_search);
        iv_bottom_hot = findViewById(R.id.iv_bottom_hot);
        iv_bottom_like = findViewById(R.id.iv_bottom_like);

        iv_bottom_homepage.setOnClickListener(this);
        iv_bottom_search.setOnClickListener(this);
        iv_bottom_hot.setOnClickListener(this);
        iv_bottom_like.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bottom_homepage:
                change(iv_bottom_homepage);
                break;
            case R.id.iv_bottom_search:
                change(iv_bottom_search);
                break;
            case R.id.iv_bottom_hot:
                change(iv_bottom_hot);
                break;
            case R.id.iv_bottom_like:
                change(iv_bottom_like);
                break;
            default:
                break;
        }
    }

    private void change(ImageView iv) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        iv_bottom_homepage.setImageResource(R.mipmap.homepage);
        iv_bottom_search.setImageResource(R.mipmap.movie);
        iv_bottom_hot.setImageResource(R.mipmap.hot);
        iv_bottom_like.setImageResource(R.mipmap.like);
        if (iv == iv_bottom_homepage) {
            iv_bottom_homepage.setImageResource(R.mipmap.homepage_select);
            transaction.replace(R.id.fl_content, homepage);
//            tv_top_title.setText("首页");
            toolbar_menu.setTitle("首页");
        }
        if (iv == iv_bottom_search) {
            iv_bottom_search.setImageResource(R.mipmap.movie_select);
            if (searchPage == null) {
                searchPage = new SearchPageFragment();
            }
            transaction.replace(R.id.fl_content, searchPage);
//            tv_top_title.setText("电影搜索");
            toolbar_menu.setTitle("电影搜索");
        }
        if (iv == iv_bottom_hot) {
            iv_bottom_hot.setImageResource(R.mipmap.hot_select);
            if (hotFragment == null) {
                hotFragment = new HotFragment();
            }
            transaction.replace(R.id.fl_content, hotFragment);
//            tv_top_title.setText("热门影评");
            toolbar_menu.setTitle("热门影评");
        }
        if (iv == iv_bottom_like) {
            iv_bottom_like.setImageResource(R.mipmap.like_select);
            if (likeFragment == null) {
                likeFragment = new LikeFragment();
            }
            transaction.replace(R.id.fl_content, likeFragment);
//            tv_top_title.setText("我的收藏");
            toolbar_menu.setTitle("我的收藏");
        }
        transaction.commit();
    }


}
