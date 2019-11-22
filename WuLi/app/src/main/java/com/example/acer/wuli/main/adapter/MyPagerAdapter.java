package com.example.acer.wuli.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> pagerViews;

    public MyPagerAdapter(Context context, List<View> pagerViews) {
        this.context = context;
        this.pagerViews = pagerViews;
    }

    @Override
    public int getCount() {
        if (pagerViews == null || pagerViews.size() == 0) {
            return 0;
        }
        if (pagerViews.size() == 1) {
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pagerViews.get(position % pagerViews.size());
        if (view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.removeView(view);
        }
        container.addView(view);
        return pagerViews.get(position % pagerViews.size());
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
