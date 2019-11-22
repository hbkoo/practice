package com.example.acer.wuli.history_hot.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.activity.OneCommentActivity;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.history_hot.adapter.HotAdapter;
import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    private List<Comment> comments = new ArrayList<>();  // 评论历史
    private List<Movie> movies = new ArrayList<>();      // 对应的电影
    private User user;
    private HotAdapter hisadpter;
    private ListView lv_hiscomment;
    private ImageView iv_backtoperson;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lv_hiscomment = findViewById(R.id.lv_hiscomment);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        queryHistory();

        hisadpter = new HotAdapter(HistoryActivity.this, comments, movies);

        lv_hiscomment.setAdapter(hisadpter);

        // TODO 界面跳转
        lv_hiscomment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Comment comment = comments.get(i);
                CommentItem item = new CommentItem(new User(),comment);
                OneCommentActivity.startOneCommentActivity(HistoryActivity.this,item,
                        movies.get(i).getMovieName());
            }
        });


    }

    // 查找历史
    private void queryHistory() {

        comments = LitePal.where("userID = ?", String.valueOf(MainActivity.user_ID))
                .find(Comment.class);
        Movie movie;
        // 获取对应的电影信息
        for (Comment comment : comments) {
            movie = LitePal.where("id = ?", String.valueOf(comment.getMovieID()))
                    .findFirst(Movie.class);
            movies.add(movie);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
