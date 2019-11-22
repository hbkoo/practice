package com.example.acer.wuli.main.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;


public class MyViewPager extends ViewPager {
    private static final int START_SCROLL_ANIM = 0;//开启滚动处理
    private static final int STOP_SCROLL_ANIM = 1;//关闭滚动处理
    private static final int LOOP_MESSAGE = 2;//循环消息
    MyHeaderPagerHandler handler;
    private int changePagerSpeed = 2000;//切换页面速度
    private int loopSpeed = 3000;//每个item切换间隔
    private boolean ifStopAnim = false;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler = new MyHeaderPagerHandler(new WeakReference<>(this));
    }

    //滑动控制--------------------------------------------------------------------------------------------------------------------------
    public void startScroll() {
        Log.i("test","firstStart");
        ifStopAnim = false;
        Message message = new Message();
        message.what = START_SCROLL_ANIM;
        handler.sendMessage(message);
    }

    public void startScroll(long delay) {
        Log.i("test","StartDelay");
        ifStopAnim = false;
        Message message = new Message();
        message.what = START_SCROLL_ANIM;
        handler.sendMessageDelayed(message, delay);
    }

    public void stopScroll() {
        ifStopAnim = true;
        handler.removeCallbacksAndMessages(null);
    }

    private void loopMessage() {
        Log.i("test","loop");
        Message message = new Message();
        message.what = LOOP_MESSAGE;
        handler.sendMessageDelayed(message, loopSpeed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("test","stop");
                stopScroll();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test","startagain");
                stopScroll();
                startScroll(6000);
                break;
        }
        return super.onTouchEvent(ev);
    }


    private static class MyHeaderPagerHandler extends Handler {
        private WeakReference<MyViewPager> myPager;

        MyHeaderPagerHandler(WeakReference<MyViewPager> myPager) {
            this.myPager = myPager;
        }

        @Override
        public void handleMessage(Message msg) {
            if (myPager.get() == null) return;
            if (myPager.get().ifStopAnim) return;
            super.handleMessage(msg);
            switch (msg.what) {
                case START_SCROLL_ANIM:
                    myPager.get().loopMessage();
                    break;
                case STOP_SCROLL_ANIM:
                    break;
                case LOOP_MESSAGE:
                    myPager.get().setCurrentItem(myPager.get().getCurrentItem() + 1, true);
                    myPager.get().loopMessage();
                    break;
            }
        }
    }
}