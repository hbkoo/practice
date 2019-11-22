package com.example.acer.wuli.comments.helper;

import android.view.View;
import android.widget.TextView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Movie;


public class FitMovie2DialogHelper {
    /**
     *  将电影内容加载到查看电影简介的对话框上
     * @param movie 电影
     * @param view  绑定到的对话视图上
     */
    public static void bindMovie2Dialog(Movie movie, View view){
        TextView tvMovieName=view.findViewById(R.id.tv_movie_dialog_title);                 //1.电影名
        TextView tvtype=view.findViewById(R.id.tv_movie_dialog_scenarist);                  //2.编剧
        TextView tvPlayers=view.findViewById(R.id.tv_movie_dialog_players);                 //3.演员
        TextView tvPreview=view.findViewById(R.id.tv_movie_dialog_preview);                 //4.简介
        tvMovieName.setText(movie.getMovieName());
        tvtype.setText(movie.getType());
        tvPlayers.setText(movie.getMainActor());
        tvPreview.setText("\t"+movie.getSummary());
    }
}
