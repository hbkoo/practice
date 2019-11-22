package com.example.acer.wuli.history_hot.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.activity.OneCommentActivity;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.history_hot.adapter.HotAdapter;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class HotFragment extends Fragment {
    HotAdapter hot_adapter;
    ListView lv_hot_comment;
    List<Comment> comments = new ArrayList<>();  // 热门评论
    List<Movie> movies = new ArrayList<>();      // 对应的热门电影

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        lv_hot_comment = view.findViewById(R.id.lv_hotcomment);

        queryHot();
        hot_adapter = new HotAdapter(view.getContext(), comments, movies);
        lv_hot_comment.setAdapter(hot_adapter);

        lv_hot_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Comment comment = comments.get(i);
                User user = LitePal.where("ID = ?", String.valueOf(comment.getUserID()))
                        .findFirst(User.class);
                Movie movie = LitePal.where("id = ?", String.valueOf(comment.getMovieID()))
                        .findFirst(Movie.class);
                // TODO 启动活动跳转
                CommentItem commentItem = new CommentItem(user, comment);
                OneCommentActivity.startOneCommentActivity(getContext(), commentItem,
                        movie.getMovieName());

            }
        });

        return view;
    }

    // 查询火热的评论信息
    private void queryHot() {
        comments = LitePal.where("praiseNumber >= ?", "100").find(Comment.class);

        Movie movie;
        // 获取对应的电影信息
        for (Comment comment : comments) {
            movie = LitePal.where("id = ?", String.valueOf(comment.getMovieID()))
                    .findFirst(Movie.class);
            movies.add(movie);
        }
    }


}
