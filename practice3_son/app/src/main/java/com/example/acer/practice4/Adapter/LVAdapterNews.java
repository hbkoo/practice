package com.example.acer.practice4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.practice4.Beans.News;
import com.example.acer.practice4.R;

import java.util.List;

/**
 * Created by 19216 on 2019/1/10.
 */

public class LVAdapterNews extends BaseAdapter {

    private Context context;
    private List<News> newsList;

    public LVAdapterNews(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }



    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_news_layout,null);

        News news = newsList.get(i);

        ImageView imageView = view.findViewById(R.id.iv_item);
        TextView tv_title = view.findViewById(R.id.tv_item_title);
        TextView tv_address = view.findViewById(R.id.tv_item_address);
        TextView tv_time = view.findViewById(R.id.tv_item_time);
        TextView tv_category = view.findViewById(R.id.tv_item_category);

        imageView.setImageResource(news.getImage());
        tv_title.setText(news.getTitle());
        tv_address.setText(news.getAddress());
        tv_time.setText(news.getTime());
        tv_category.setText(news.getCategory());

        return view;
    }
}
