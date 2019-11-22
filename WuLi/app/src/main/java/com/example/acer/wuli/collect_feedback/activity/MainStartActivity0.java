package com.example.acer.wuli.collect_feedback.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.wuli.R;
import com.example.acer.wuli.addcomment.activity.Comment_Add_Activity;
import com.example.acer.wuli.model.Collect;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;
import com.example.acer.wuli.model.Feedback;

import org.litepal.LitePal;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.List;

public class MainStartActivity0 extends AppCompatActivity {


    private String ramdonNumAll;
    private String phoneNum = "15927037762";

    public static User user = new User(001, "123", "15927037762", "龙息在天");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start0);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn_like, R.id.btn_return, R.id.btn_about,
            R.id.btn_contact, R.id.btn_upgrade, R.id.btn_get_code,
            R.id.btn_add_test_data,R.id.btn_add_comment})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_test_data:
                addData();
                break;
            case R.id.btn_like:
                startActivity(new Intent(MainStartActivity0.this, HomeActivity.class));
                break;
            case R.id.btn_return:
                startActivity(new Intent(MainStartActivity0.this, ReturnActivity.class));
                break;
            case R.id.btn_about:
                startActivity(new Intent(MainStartActivity0.this, AboutActivity.class));
                break;
            case R.id.btn_contact:
                contact();
                break;
            case R.id.btn_upgrade:
                upgrade();
                break;
            case R.id.btn_get_code:
                break;
            case R.id.btn_add_comment:
                startActivity(new Intent(MainStartActivity0.this,Comment_Add_Activity.class));
                break;
        }
    }

    private void addData() {

        LitePal.getDatabase();


//        Movie movie0 = new Movie(0, "大黄蜂", 9.3, "特拉维斯·奈特",
//                "克里斯蒂娜·霍德森", R.drawable.im_3, 362);
//        movie0.save();
//
//        Movie movie1 = new Movie(1, "碟中谍2", 9.1, "特拉维斯·奈特",
//                "克里斯蒂娜·霍德森", R.drawable.im_2, 465);
//        movie1.save();
//
//        Movie movie2 = new Movie(2, "碟中谍3", 8.9, "特拉维斯·奈特",
//                "克里斯蒂娜·霍德森", R.drawable.im_6, 555);
//        movie2.save();
//
//        Collect collect = new Collect();
//        collect.setID(0);
//        collect.setMovieID(3);
//        collect.setUserID(001);
//        collect.save();
//
//        Collect collect1 = new Collect();
//        collect1.setID(1);
//        collect1.setMovieID(1);
//        collect1.setUserID(001);
//        collect1.save();

    }

    private void showData() {
        List<Movie> movies = LitePal.findAll(Movie.class);
        Log.i("movie size:", String.valueOf(movies.size()));
        for (Movie movie : movies) {
            Log.i("movie:", movie.toString());
        }

        List<Collect> collects = LitePal.findAll(Collect.class);
        Log.i("collects size:", String.valueOf(collects.size()));
        for (Collect collect2 : collects) {
            Log.i("collection:", collect2.toString());
        }

        List<Feedback> feedbacks = LitePal.findAll(Feedback.class);
        Log.i("feedbacks size:", String.valueOf(feedbacks.size()));
        for (Feedback feedback : feedbacks) {
            Log.i("feedback:", feedback.toString());
        }

    }

    // 版本更新检测
    private void upgrade() {

        showData();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_upgrade, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
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
                Toast.makeText(MainStartActivity0.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        iv_loading.startAnimation(rotateAnimation);
    }

    // 联系我们
    private void contact() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_contact, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        TextView tv_tel = view.findViewById(R.id.tv_tel);
        tv_tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainStartActivity0.this, "打电话", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
