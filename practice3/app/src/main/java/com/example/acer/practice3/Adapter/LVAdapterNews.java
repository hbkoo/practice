package com.example.acer.practice3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.practice3.Beans.News;
import com.example.acer.practice3.R;

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

        View view;
        ViewHolder viewHolder;

        if (contentView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_news_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.iv_item);
            viewHolder.tv_title = view.findViewById(R.id.tv_item_title);
            viewHolder.tv_address = view.findViewById(R.id.tv_item_address);
            viewHolder.tv_time = view.findViewById(R.id.tv_item_time);
            viewHolder.tv_category = view.findViewById(R.id.tv_item_category);
            view.setTag(viewHolder);
        } else {
            view = contentView;
            viewHolder = (ViewHolder) view.getTag();
        }


        News news = newsList.get(i);


        viewHolder.imageView.setImageResource(news.getImage());
        viewHolder.tv_title.setText(news.getTitle());
        viewHolder.tv_address.setText("地点：" + news.getAddress());
        viewHolder.tv_time.setText("时间：" + news.getTime());
        viewHolder.tv_category.setText(news.getCategory());

        return view;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView tv_title,tv_address,tv_time,tv_category;
    }

}
