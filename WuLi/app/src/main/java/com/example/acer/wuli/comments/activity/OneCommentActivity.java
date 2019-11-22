package com.example.acer.wuli.comments.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.comments.helper.CurrentUser;
import com.example.acer.wuli.comments.helper.DataHelper;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.User;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class OneCommentActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_one_comment_movie_name)
    private TextView tvMovieName;                                                   //1.电影名
    @ViewInject(R.id.rb_one_comment_star)
    private RatingBar rbStars;                                                      //2.平分数
    @ViewInject(R.id.tv_one_comment_title)
    private TextView tvTitle;                                                       //3.影评标题
    @ViewInject(R.id.tv_one_comment_nickname)
    private TextView tvNickName;                                                    //4.昵称
    @ViewInject(R.id.tv_one_comment_time)
    private TextView tvTime;                                                        //5.时间
    @ViewInject(R.id.tv_one_comment_hits)
    private TextView tvHits;                                                        //6.点赞数
    @ViewInject(R.id.tv_one_comment_content)
    private TextView tvArticle;                                                     //7.影评内容
    @ViewInject(R.id.toolbar_one_comment)
    private Toolbar toolbar;                                                        //8.toolbar上显示页面标题
    //数据
    private CommentItem currentComment;                                             //9.当前评论
    //数据库点赞状态
    private boolean current_state = false;                                                //10.点赞
    //切换点赞
    private boolean praiseComment = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_one_comment);
        x.view().inject(this);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //1.启动并设置数据
        init();

    }

    /**
     * 启动一条影评的详细页面
     *
     * @param context
     * @param comment
     */
    public static void startOneCommentActivity(Context context, CommentItem comment, String movieName) {
        Intent post = new Intent(context, OneCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieName", movieName);
        bundle.putSerializable("oneComment", comment);
        post.putExtra("currentComment", bundle);
        context.startActivity(post);
    }

    // TODO: 2019/1/14  加载数据                                            --未完成
    private void init() {
        Intent intent = getIntent();
        //1.获取传递的数据
        Bundle bundle = intent.getBundleExtra("currentComment");
        currentComment = (CommentItem) bundle.getSerializable("oneComment");
        //电影名
        String movieName = bundle.getString("movieName");
        //评论
        Comment comment = currentComment.getComment();
        //评论的作者
        User user = currentComment.getUser();

        //2.设置数据
        tvMovieName.setText(movieName);                                     //0.电影标题
        tvTitle.setText(comment.getCommentTitle());                         //1.影评标题
        rbStars.setRating(comment.getCommentPoint()/2.0f);                  //2.评分星星
        tvTime.setText(comment.getCommentTime());                           //3.时间
        tvNickName.setText(user.getNickName());                             //4.昵称
        tvHits.setText(comment.getPraiseNumber() + "");                     //5.点赞数
        tvArticle.setText("\t"+comment.getCommentContent() + "\n\n\n\n");        //6.内容

        //3.设置点赞状态
        current_state = DataHelper.isPraised(CurrentUser.getCurrentUser(), comment);
        if (current_state) {
            tvHits.setTextColor(Color.RED);
        }
        praiseComment = !current_state;

    }

    @Event(value = R.id.tv_one_comment_hits)
    private void praiseComment(View view) {
        if (view.getId() == R.id.tv_one_comment_hits) {
            praiseComment(praiseComment);
        }
    }

    private void praiseComment(boolean p) {
        Comment c = currentComment.getComment();
        if (p) {
            //点赞
            praiseComment = false;
            int hits = c.getPraiseNumber() + 1;
            c.setPraiseNumber(hits);
            tvHits.setText(hits + "");
            tvHits.setTextColor(Color.RED);
            DataHelper.praiseComment(CurrentUser.getCurrentUser(), currentComment.getComment(), true);
            DataHelper.addOnePraise(currentComment.getComment(), true);
        } else {
            praiseComment = true;
            int hits = c.getPraiseNumber() - 1;
            hits = hits < 0 ? 0 : hits;
            c.setPraiseNumber(hits);
            tvHits.setText(hits + "");
            tvHits.setTextColor(Color.DKGRAY);
            DataHelper.praiseComment(CurrentUser.getCurrentUser(), currentComment.getComment(), false);
            DataHelper.addOnePraise(currentComment.getComment(), false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
