package com.example.acer.wuli.comments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class _CommentAdapter extends BaseAdapter {
    private Context context;
    private List<CommentItem> list;
    private ListView listView;

    /**
     * @param context
     * @param list    数据集合
     */
    public _CommentAdapter(Context context, List<CommentItem> list, ListView listView) {
        this.list = list;
        this.context = context;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentItem item = (CommentItem) getItem(position);
        User user = item.getUser();
        Comment comment = item.getComment();

        View view = LayoutInflater.from(context).inflate(R.layout.lv_item_comment, parent, false);
        // TODO: 2019/1/14  需要评论的数据                                               --未完成
        CircleImageView civHead = view.findViewById(R.id.civ_item_head);                   //头像
        TextView tvNickName = view.findViewById(R.id.tv_item_nickname);                    //昵称
        RatingBar rb = view.findViewById(R.id.rb_item_star);                               //评论星星
        TextView tvTime = view.findViewById(R.id.tv_item_time);                            //时间
        TextView tvHits = view.findViewById(R.id.tv_item_hits);                            //点赞数
        TextView tvTitle = view.findViewById(R.id.tv_item_comment_title);                  //标题
        TextView tvContent = view.findViewById(R.id.tv_item_comment_article);              //影评内容
        // TODO: 2019/1/14 绑定数据                                                      --已完成
        civHead.setImageResource(R.drawable.head_boy_32);     //头像
        tvNickName.setText(user.getNickName());//昵称
        rb.setRating(comment.getCommentPoint()/2.0f);//评论星星
        tvTime.setText(comment.getCommentTime()); //时间
        tvHits.setText(comment.getPraiseNumber() + ""); //点赞数
        tvTitle.setText(comment.getCommentTitle());//标题
        tvContent.setText("\t"+comment.getCommentContent()); //影评内容
        return view;
    }

    //1.添加数据到第一条，可以立即查看信息
    public void addComment(CommentItem comment) {
        list.add(0, comment);
        this.notifyDataSetChanged();
    }
    //2.获取评论数据
    public CommentItem getComment(int position) {
        return list.get(position);
    }
}
