package com.example.acer.wuli.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Movie;

import java.util.List;


public class HomePageAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> data;

    public HomePageAdapter(Context context, List<Movie> data) {
        this.context = context;
        this.data = data;
    }

    @Override

    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepage_list_item,null);
        ImageView iv_post=view.findViewById(R.id.iv_homepage_post);
        TextView tv_moviename=view.findViewById(R.id.tv_homepage_moviename);
        TextView tv_director=view.findViewById(R.id.tv_homepage_director);
        TextView tv_actor=view.findViewById(R.id.tv_homepage_actor);
        TextView tv_point=view.findViewById(R.id.tv_homepage_point);

        //TODO 通过字符串获取ID
        String postName=data.get(position).getPostID();
 //       iv_post.setImageResource(data.get(position).getPostID());
        int postID=getResource(postName,context);
        Log.i("postid",postName+"+"+postID);
        iv_post.setImageResource(postID);
        tv_moviename.setText(data.get(position).getMovieName());
        tv_director.setText("导演："+data.get(position).getDirectorName());
        tv_actor.setText("主演："+data.get(position).getMainActor());
        tv_point.setText(data.get(position).getPoint()+"");

        return view;
    }

    public int getResource(String name,Context context){
            Resources r=context.getResources();
            int id = r.getIdentifier(name,"drawable",context.getPackageName());
            return id;
    }
}
