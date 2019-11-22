package com.example.acer.wuli.comments.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;


import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.comments.bean.Movie2;
import com.example.acer.wuli.model.Collect;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.Praise;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询数据库的帮助类
 * 查询内容包括：
 * 1.电影
 * 2.影评列表
 * 3.点赞和取消
 * 4.查询用户--使用到头像和昵称
 * 5.海报图片
 * 6.收藏状态
 * 7.点赞状态
 */
public class DataHelper {
    /**
     * 查询电影和本地图片资源
     *
     * @param context
     * @param movieID
     * @return
     */
    public static Movie2 queryOneMovie(Context context, int movieID) {
        try {
            Movie movie = LitePal.find(Movie.class, movieID);
            int posterId = getResource(context, movie.getPostID());
            Movie2 m2 = new Movie2();
            m2.setMovie(movie);
            m2.setResId(posterId);
            return m2;
        } catch (Exception e) {
            Log.e("queryOneMovie--", e.getMessage());
        }
        return null;
    }

    /**
     * 查询电影的相关评论
     *
     * @param movie 电影
     * @return 评论列表
     */
    public static List<CommentItem> queryComments(Movie2 movie) {
        try {
            //根据Movie的ID查评论，再根据评论中的用户ID查用户
            Movie m = movie.getMovie();
            int movieId = m.getId();
            List<CommentItem> result = new ArrayList<>();
            //1.查询评论
            //List<Song> songs = LitePal.where("name like ? and duration < ?", "song%", "200").order("duration").find(Song.class);
            List<Comment> allComments = LitePal.where("movieid=?", movieId + "").find(Comment.class);

            //2.查询用户
            for (int i = 0; i < allComments.size(); i++) {
                Comment c = allComments.get(i);
                User u = LitePal.find(User.class, c.getUserID());
                result.add(new CommentItem(u, c));
            }
            return result;
        } catch (Exception e) {
            Log.e("queryComments--", e.getMessage());
        }
        return null;
    }

    /**
     * 添加点赞
     *
     * @param cur 当前评论
     * @return
     */
    public static boolean addOnePraise(Comment cur, boolean add) {
        try {
            Comment c = LitePal.find(Comment.class, cur.getID());
            if (add) {
                c.setPraiseNumber(cur.getPraiseNumber());
            } else {
                c.setPraiseNumber(cur.getPraiseNumber());
            }
            c.save();
            return true;
        } catch (Exception e) {
            Log.e("addOnePraise--", e.getMessage());
        }
        return false;
    }

    /**
     * 查询用户
     *
     * @param uid 用户ID
     * @return
     */
    public static User queryUser(int uid) {
        try {
            User user = LitePal.find(User.class, uid);
            return user;
        } catch (Exception e) {
            Log.e("queryUser--", e.getMessage());
        }
        return null;
    }

    /**
     * 查询电影的海报ID
     *
     * @param context
     * @param imageName
     * @return
     */
    private static int getResource(Context context, String imageName) {
//        int resId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
//        //如果没有在"mipmap"下找到imageName,将会返回0
//        return resId;
        Resources r = context.getResources();
        int id = r.getIdentifier(imageName, "drawable", context.getPackageName());
        return id;

    }

    /**
     * 匹配电影的剧照信息
     *
     * @param movieID
     * @return
     */
    public static List<Integer> matchPhotoList(int movieID, Movie movie, Context context) {
        List<Integer> photos = null;
        if (movieID == 1) {
            photos = new ArrayList<Integer>();
            photos.add(R.drawable.photo_1_1);
            photos.add(R.drawable.photo_1_2);
            photos.add(R.drawable.photo_1_3);
            photos.add(R.drawable.photo_1_4);
            return photos;
        }
        photos = new ArrayList<>();
        int p1 = getResource(context, movie.getPhoto1());
        int p2 = getResource(context, movie.getPhoto2());
        int p3 = getResource(context, movie.getPhoto3());
        photos.add(p1);
        photos.add(p2);
        photos.add(p3);
        return photos;
    }

    /**
     * 收藏
     *
     * @param user
     * @param movie
     * @param state
     */
    public static void collectMovie(User user, Movie movie, boolean state) {
        try {
            if (state) {
                Collect cc = new Collect();
                cc.setUserID(user.getID());
                cc.setMovieID(movie.getId());
                cc.save();
                movie.setCollectNumber(movie.getCollectNumber() + 1);
                movie.update(movie.getId());
            } else {
                Collect collect = LitePal.where("userid=? and movieid=?",
                        user.getID() + "", movie.getId() + "").find(Collect.class).get(0);
                if (collect != null) {
                    LitePal.delete(Collect.class, collect.getID());
                    movie.setCollectNumber(movie.getCollectNumber() - 1);
                    movie.update(movie.getId());
                }
            }
        } catch (Exception e) {
            Log.e("isPraised--", e.getMessage());
        }
    }

    /**
     * 判断当前用户是否收藏该电影
     *
     * @param user
     * @param movie
     * @return
     */
    public static boolean isCollected(User user, Movie movie) {
        try {
            Collect collect = LitePal.where("userid=? and movieid=?", user.getID() + "", movie.getId() + "").find(Collect.class).get(0);
            if (collect != null)
                return true;
        } catch (Exception e) {
            Log.e("isCollected--", e.getMessage());
        }
        return false;
    }

    /**
     * 查询是否点赞
     *
     * @param user
     * @param comment
     * @return
     */
    public static boolean isPraised(User user, Comment comment) {
        try {
            Praise praise = LitePal.where("userid=? and commentid=?", user.getID() + "", comment.getID() + "").find(Praise.class).get(0);
            if (praise != null)
                return true;
        } catch (Exception e) {
            Log.e("isPraised--", e.getMessage());
        }
        return false;
    }

    /**
     * 点赞
     *
     * @param user
     * @param comment
     * @return
     */
    public static void praiseComment(User user, Comment comment, boolean state) {
        try {
            if (state) {
                Praise p = new Praise();
                p.setCommentID(comment.getID());
                p.setUserID(user.getID());
                p.save();
            } else {
                Praise praise = LitePal.where("userid=? and commentid=?", user.getID() + "", comment.getID() + "").find(Praise.class).get(0);
                if (praise != null) {
                    LitePal.delete(Praise.class, praise.getId());
                }
            }
        } catch (Exception e) {
            Log.e("isPraised--", e.getMessage());
        }
    }

}
