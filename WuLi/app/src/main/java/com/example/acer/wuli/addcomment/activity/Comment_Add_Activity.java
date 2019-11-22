package com.example.acer.wuli.addcomment.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.helper.CurrentUser;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.Calendar;
import java.util.List;


public class Comment_Add_Activity extends AppCompatActivity {

    public static final String FILM_NAME = "film_name";
    public static final String FILM_IMAGE_ID = "film_image_id";
    // TODO: 2019/1/16 wp添加
    /*******************************/
    private static final String FILM_ID = "film_id";
    private int filmId;
    /*******************************/

    private RatingBar ratingBar;

    //静态方法 获取重要数据
    public static void startComment_Add_Activity(Context context, String filmName, int filmImageId, int filmId) {
        Intent intent = new Intent(context, Comment_Add_Activity.class);
        intent.putExtra(FILM_NAME, filmName);
        intent.putExtra(FILM_IMAGE_ID, filmImageId);
        intent.putExtra(FILM_ID, filmId);
        //context.startActivity(intent);
        // TODO: 2019/1/16 wp添加的代码
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment__add_);
        //intent
        // 电影名字 图片ID  电影ID
        Intent intent = getIntent();
        final String filmName = intent.getStringExtra(FILM_NAME);
        int filmImageId = intent.getIntExtra(FILM_IMAGE_ID, 0);
        filmId = intent.getIntExtra(FILM_ID, 0);
        //初始化
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView filmImageView = findViewById(R.id.iv_title);
        final TextView tv_score = findViewById(R.id.tv_score);
        final TextView tv_number = findViewById(R.id.tv_number);
        ratingBar = findViewById(R.id.rb);
        final EditText et_title = findViewById(R.id.et_title);
        final EditText et_comment = findViewById(R.id.et_comment);
        Button btn_complete = findViewById(R.id.btn_complete);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置
        collapsingToolbar.setTitle(filmName);
        filmImageView.setImageResource(filmImageId);
        //button 监听器
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_complete:
                        //弹窗提示：
                        AlertDialog.Builder builder = new AlertDialog.Builder(Comment_Add_Activity.this);
                        View view = LayoutInflater.from(Comment_Add_Activity.this).inflate(R.layout.dialog_cancel_like, null);
                        builder.setView(view);
                        final AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();
                        TextView tv_content = view.findViewById(R.id.tv_content);
                        TextView tv_cancel = view.findViewById(R.id.tv_ok);
                        TextView tv_ok = view.findViewById(R.id.tv_cancel);
                        tv_content.setText("确定要提交影评吗？");
                        tv_ok.setText("确认");
                        tv_cancel.setText("取消");
                        //确认提交
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String comment = et_comment.getText().toString();
                                String title = et_title.getText().toString();
                                float rating = (int) (ratingBar.getRating() * 2.0);
                                if (rating == 0) {
                                    Toast.makeText(Comment_Add_Activity.this, "评分不能为空", Toast.LENGTH_LONG).show();
                                } else if (title.equals("")) {
                                    Toast.makeText(Comment_Add_Activity.this, "标题不能为空", Toast.LENGTH_LONG).show();
                                } else if (comment.equals("")) {
                                    Toast.makeText(Comment_Add_Activity.this, "内容不能为空", Toast.LENGTH_LONG).show();
                                } else {
                                    //TODO
                                    String comment_time = loadTime();
                                    //插入数据库 评分 标题 内容 时间
                                    Comment lei_comment = new Comment();
                                    lei_comment.setCommentPoint((int) rating);
                                    lei_comment.setCommentTitle(title);
                                    lei_comment.setCommentContent(comment);
                                    lei_comment.setCommentTime(comment_time);

                                    User cur = CurrentUser.getCurrentUser();
                                    lei_comment.setUserID(cur.getID());
                                    lei_comment.setMovieID(filmId);
                                    Log.i("test", "aaaa");
                                    lei_comment.save();

                                    List<Movie> update_movie = LitePal.where("movieName = ?",filmName)
                                            .find(Movie.class);
                                    //Movie update_movie = LitePal.where("movieName = ?",filmName).find(Movie.class);
                                    //update_movie.get(0).getCommentNumber();
                                    int comment_number =update_movie.get(0).getCommentNumber();
                                    double comment_point = update_movie.get(0).getPoint();
                                    double l_point=(comment_point*comment_number+rating)/(comment_number+1);
                                    update_movie.get(0).setPoint(l_point);
                                    update_movie.get(0).setCommentNumber(comment_number+1);
                                    update_movie.get(0).save();


                                    // TODO: 2019/1/16  wp添加的代码
                                    /*************************************/
                                    Intent back = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("NewComment", lei_comment);
                                    back.putExtra("RESULT", bundle);
                                    setResult(RESULT_OK, back);
                                    /***********************************/

                                    finish();
                                }
                                dialog.dismiss();
                            }
                        });
                        //取消提交
                        tv_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                }
            }
        });
        //ratingBar 监听器
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                switch ((int) (rating * 2.0)) {
                    case 1:
                        tv_score.setText("超烂啊(╬￣皿￣)");
                        tv_number.setText("1分");
                        break;
                    case 2:
                        tv_score.setText("超烂啊");
                        tv_number.setText("2分");
                        break;
                    case 3:
                        tv_score.setText("比较差");
                        tv_number.setText("3分");
                        break;
                    case 4:
                        tv_score.setText("比较差");
                        tv_number.setText("4分");
                        break;
                    case 5:
                        tv_score.setText("一般般");
                        tv_number.setText("5分");
                        break;
                    case 6:
                        tv_score.setText("一般般");
                        tv_number.setText("6分");
                        break;
                    case 7:
                        tv_score.setText("比较好");
                        tv_number.setText("7分");
                        break;
                    case 8:
                        tv_score.setText("比较好");
                        tv_number.setText("8分");
                        break;
                    case 9:
                        tv_score.setText("真棒");
                        tv_number.setText("9分");
                        break;
                    case 10:
                        tv_score.setText("完美(*^▽^*)");
                        tv_number.setText("10分");
                        break;
                    default:
                        //TODO
                        tv_score.setText("期待您的评分");
                        tv_number.setText(" ");
                }
            }
        });
    }


    //获取时间
    private String loadTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String comment_time = year + " 年 " + (month + 1) + " 月 " + day + " 日 ";
        return comment_time;
    }

    ;

    //点击左上角返回按钮 关闭页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //弹窗提示：
                AlertDialog.Builder builder = new AlertDialog.Builder(Comment_Add_Activity.this);
                View view = LayoutInflater.from(Comment_Add_Activity.this).inflate(R.layout.dialog_cancel_like, null);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                TextView tv_content = view.findViewById(R.id.tv_content);
                TextView tv_ok = view.findViewById(R.id.tv_ok);
                TextView tv_cancel = view.findViewById(R.id.tv_cancel);
                tv_content.setText("确定退出吗？（您输入的内容将无法保存）");
                tv_ok.setText("退出");
                tv_cancel.setText("取消");
                //确认退出
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finish();
                    }
                });
                //取消退出
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //返回
               /* Intent intent =new Intent(Comment_Add_Activity.this,newActivity.class);
                startActivityForResult(intent,100);*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
