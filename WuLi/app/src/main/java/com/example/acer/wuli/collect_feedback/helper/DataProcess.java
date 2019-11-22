package com.example.acer.wuli.collect_feedback.helper;

import android.util.Log;

import com.example.acer.wuli.model.Collect;
import com.example.acer.wuli.model.Feedback;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * 从数据库中获取数据
 */

public class DataProcess {

    private static DataProcess dataProcess = null;

    private DataProcess() {}

    public static DataProcess getInstance(){
        if (dataProcess == null){
            dataProcess = new DataProcess();
            LitePal.getDatabase(); // 创建数据库
        }
        return dataProcess;
    }

    // 根据用户的ID获取用户收藏的电影
    public List<Movie> queryCollectionMovie(int userID) {

        List<Movie> movies = new ArrayList<>();

        List<Collect> collects = LitePal.where("userID=?", valueOf(userID))
                .find(Collect.class);

        Movie movie;
        for (Collect collect : collects) {
            movie = LitePal.where("id=?", valueOf(collect.getMovieID()))
                    .findFirst(Movie.class);

            if (movie == null) {
                continue;
            }

            movies.add(movie);
            Log.i("in DataProcess:",movie.toString());
        }
        Log.i("movies' size :", valueOf(movies.size()));

        return movies;

    }

    // 删除用户收藏的电影
    public boolean deleteCollectionMovie(int userID,Movie movie){
        boolean result = false;

        int num = LitePal.deleteAll(Collect.class,"userID = ? and movieID = ?",
                String.valueOf(userID), String.valueOf(movie.getId()));

        movie.setCollectNumber(movie.getCollectNumber() - 1);
        movie.update(movie.getId());

        result = (num != 0);

        return result;
    }

    // 添加反馈信息
    public boolean addFeedback(int userID,String content) {

        Feedback feedback = new Feedback(userID,content);
        return feedback.save();

    }

    // 查找用户
    public User searchUserByID(int ID) {

        User user = LitePal.where("ID = ?", String.valueOf(ID)).findFirst(User.class);
        return user;
    }


}
