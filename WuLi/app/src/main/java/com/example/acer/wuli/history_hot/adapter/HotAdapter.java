package com.example.acer.wuli.history_hot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;

import java.util.List;

public class HotAdapter extends BaseAdapter {
    ImageView iv_hotmovie;
    TextView tv_hotcommenttitle;
    TextView tv_hottime;
    TextView tv_hotpraisepoint;
    ImageView iv_hotp;
    private Context mContext;
    private List<Comment> comments;
    private List<Movie> movies;

    public HotAdapter(Context mContext, List<Comment> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    public HotAdapter(Context mContext, List<Comment> comments, List<Movie> movies) {
        this.mContext = mContext;
        this.comments = comments;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getResource(String imageName) {

        int resId = mContext.getResources().getIdentifier(imageName, "mipmap",
                mContext.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hot_comment, parent,
                false);
        iv_hotmovie = view.findViewById(R.id.iv_hotmovie);
        tv_hotcommenttitle = view.findViewById(R.id.tv_hotcommenttitle);
        tv_hottime = view.findViewById(R.id.tv_hottime);
        iv_hotp = view.findViewById(R.id.iv_hotp);
        tv_hotpraisepoint = view.findViewById(R.id.tv_hotpraisepoint);
        //绑定数据 因为不知道图片究竟是怎么样放置于数据库当中的，因此由id来找图
        //String MoviePic="R.drawable.im_"+(data.get(position).getMoiveid()+2);

        iv_hotmovie.setImageResource(getResource(movies.get(position).getPostID()));  // 设置电影海报图片

        Comment comment = comments.get(position);
        Log.i("HotAdapter movies:", comment.toString());

        tv_hotcommenttitle.setText(comments.get(position).getCommentTitle());
        tv_hottime.setText(comments.get(position).getCommentTime());
        iv_hotp.setImageResource(R.drawable.praise);
        tv_hotpraisepoint.setText(String.valueOf(comments.get(position).getPraiseNumber()));
        return view;
    }

}
