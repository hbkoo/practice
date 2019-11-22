package com.example.acer.wuli.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.acer.wuli.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {

    private ViewPager viewPager;
    private ImageView guide1, guide2,guide3,guide4;
    private List<View> list = new ArrayList<>();
    private int[] image = {R.drawable.dark_dot, R.drawable.white_dot};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        InitControl();
    }

    private void InitControl() {
        guide1 = (ImageView) findViewById(R.id.guide1);
        guide2 = (ImageView) findViewById(R.id.guide2);
        guide3 = (ImageView) findViewById(R.id.guide3);
        guide4 = (ImageView) findViewById(R.id.guide4);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        LayoutInflater inflater = getLayoutInflater();


        View view1 = inflater.inflate(R.layout.start_litem, null, false);
        ((ImageView) view1.findViewById(R.id.image_item))
                .setImageResource(R.drawable.bumblebee);

        View view2 = inflater.inflate(R.layout.start_litem, null, false);
        ((ImageView) view2.findViewById(R.id.image_item))
                .setImageResource(R.drawable.mrbig);

        View view3 = inflater.inflate(R.layout.start_litem, null, false);
        ((ImageView) view3.findViewById(R.id.image_item))
                .setImageResource(R.drawable.killmobile);
        // 添加一个引导页

        View view4 = inflater.inflate(R.layout.start_litem, null, false);
        ((ImageView) view4.findViewById(R.id.image_item))
                .setImageResource(R.drawable.guide_02);


        TextView textView = (TextView) view4.findViewById(R.id.text);
        textView.setText("开启全新影评时代!");
        textView.setOnClickListener(new mClick());

        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new PagerChangeListener());
        viewPager.setCurrentItem(0);
    }

    private class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text:
                    SharedPreferences preferences = getSharedPreferences("isFirst",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstIn",false);
                    editor.apply();
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    }

    private class PagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    guide1.setImageResource(image[0]);
                    guide2.setImageResource(image[1]);
                    guide3.setImageResource(image[1]);
                    guide4.setImageResource(image[1]);
                    break;
                case 1:
                    guide1.setImageResource(image[1]);
                    guide2.setImageResource(image[0]);
                    guide3.setImageResource(image[1]);
                    guide4.setImageResource(image[1]);
                    break;
                case 2:
                    guide1.setImageResource(image[1]);
                    guide2.setImageResource(image[1]);
                    guide3.setImageResource(image[0]);
                    guide4.setImageResource(image[1]);
                    break;
                case 3:
                    guide1.setImageResource(image[1]);
                    guide2.setImageResource(image[1]);
                    guide3.setImageResource(image[1]);
                    guide4.setImageResource(image[0]);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

