package com.example.acer.practice5ofanimator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.iv_scenery)
    private ImageView iv_scenery;

    @ViewInject(R.id.iv_animation)
    private ImageView iv_animation;


    private AnimationDrawable animationDrawable = null;   // 轮播动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        x.view().inject(this);


    }

    @Event(value = {R.id.btn_alpha, R.id.btn_rotate, R.id.btn_scale,
            R.id.btn_translate, R.id.btn_start_frame, R.id.btn_stop_frame,
            R.id.btn_fragment})
    private void onclick(View view) {

        switch (view.getId()) {
            case R.id.btn_alpha:
//                alphaAnimation();
                valueAnimation();
                break;
            case R.id.btn_rotate:
//                objectAnimation();
                rotateAnimation();
                break;
            case R.id.btn_scale:
                scaleAnimation();
//                objectAnimation();
                break;
            case R.id.btn_translate:
//                translateAnimation();
                objectTranslationAnimation();
                break;
            case R.id.btn_start_frame:
                startFrame();
                break;
            case R.id.btn_stop_frame:
                stopFrame();
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.in_right,R.anim.out_left);
                break;
        }

    }

    private void stopFrame() {

        if (animationDrawable != null) {
//            animationDrawable.setOneShot(true);
            animationDrawable.stop();
        }

    }

    private void startFrame() {


        if (animationDrawable == null) {
            iv_animation.setImageResource(R.drawable.animation);
            animationDrawable = (AnimationDrawable) iv_animation.getDrawable();
        }
        animationDrawable.stop();
        animationDrawable.start();

    }

    // ScaleAnimation方法测试
    private void scaleAnimation() {

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);   //动画结束后保持结束后的状态
        iv_scenery.startAnimation(scaleAnimation);

    }

    // TranslateAnimation方法测试
    private void translateAnimation() {

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 1f);
        translateAnimation.setDuration(3000);
        iv_scenery.startAnimation(translateAnimation);

    }

    // RotateAnimation方法测试
    private void rotateAnimation() {

        RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        iv_scenery.startAnimation(rotateAnimation);


    }

    // AlphaAnimation方法测试
    private void alphaAnimation() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(3000);
        iv_scenery.startAnimation(alphaAnimation);


    }

    private void objectTranslationAnimation() {

        View view = findViewById(R.id.main_layout);

        int width = view.getWidth();
        int height = view.getHeight();

        int iv_width = iv_scenery.getWidth();
        int iv_height = iv_scenery.getHeight();

        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(iv_scenery,
                "translationX", 0, width - iv_width,0)
                .setDuration(4000);
        objectAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorX.start();

        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(iv_scenery,
                "translationY", 0, height - iv_height,0);
        objectAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorY.setDuration(4000).start();

    }

    private void objectAnimation() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_scenery,
                "translationZ",0,200,0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();

    }

    private void valueAnimation() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1, 0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                iv_scenery.setAlpha(value);
                Log.i("动画执行：", "" + value);
            }
        });
        valueAnimator.start();

    }



    /*
    private void Init() {

        final AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 0; i <= 25; i++) {
            int id = getResources().getIdentifier("a" + i, "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(id);
            animationDrawable.addFrame(drawable, 100);
        }

        Button btn_startFrame, btn_stopFrame;

        btn_startFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.setOneShot(true);
                iv.setImageDrawable(animationDrawable);
                // 获取资源对象
                animationDrawable.stop();
                // 特别注意：在动画start()之前要先stop()，不然在第一次动画之后会停在最后一帧，这样动画就只会触发一次
                animationDrawable.start();
                // 启动动画

            }
        });

        btn_stopFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.setOneShot(true);
                iv.setImageDrawable(animationDrawable);
                animationDrawable.stop();
            }
        });


    }
    */

}
