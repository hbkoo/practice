package com.example.acer.wuli.comments.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.wuli.R;
import com.example.acer.wuli.addcomment.activity.Comment_Add_Activity;
import com.example.acer.wuli.comments.adapter._CommentAdapter;
import com.example.acer.wuli.comments.adapter._RecycleAdapter;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.comments.bean.Movie2;
import com.example.acer.wuli.comments.helper.BackGroundHelper;
import com.example.acer.wuli.comments.helper.CurrentUser;
import com.example.acer.wuli.comments.helper.DataHelper;
import com.example.acer.wuli.comments.helper.FitMovie2DialogHelper;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 评论列表页
 */
public class CommentsActivity extends AppCompatActivity {
    //组件
    @ViewInject(R.id.lv_comments)
    private ListView commentsListView;                                   //1.评论列表
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;                                            //2.更换系统的ActionBar
    @ViewInject(R.id.collapsing_toolbar)
    private CollapsingToolbarLayout collapsingTbarLayoutTitle;          //3.电影名字显示在折叠标题布局
    @ViewInject(R.id.ll_movie_preview)
    private LinearLayout llayout;                                       //4.电影头的布局，用于点击弹出电影简介
    @ViewInject(R.id.iv_movie_collect)
    private ImageView ivCollect;                                        //5.收藏按钮，点击以后收藏，可取消收藏
    @ViewInject(R.id.tv_movie_do_comment)
    private TextView tvDoComment;                                       //6.写影评按钮，点击以后切换到评论Activity
    @ViewInject(R.id.ll_movie_background)
    private LinearLayout llBackGround;                                  //7.用于设置头部背景为模糊背景
    @ViewInject(R.id.iv_poster)
    private ImageView ivPoster;                                         //8.用于计算模糊背景
    @ViewInject(R.id.recycler_photo)
    private RecyclerView recyclerPhoto;                                 //9.剧照图片的横向列表
    @ViewInject(R.id.pb_load_comments)
    private ProgressBar pbLoading;                                      //10.数据加载进度
    @ViewInject(R.id.tv_no_comments)
    private TextView tvNoData;                                          //11.提示有无评论数据
    @ViewInject(R.id.app_bar)
    private AppBarLayout appBar;                                        //12.用于监听折叠事件
    /******/
    @ViewInject(R.id.tv_movie_name)
    private TextView title;                                             //电影名
    @ViewInject(R.id.tv_director)
    private TextView director;                                          //导演
    @ViewInject(R.id.tv_type)
    private TextView type;                                              //类型
    @ViewInject(R.id.tv_actor)
    private TextView actor;                                             //演员
    @ViewInject(R.id.tv_preview)
    private TextView preview;                                           //简介

    /****/
    //适配器
    private _CommentAdapter commentAdapter;                             //评论适配器
    private _RecycleAdapter recycleAdapter;                              //剧照适配器
    //数据
    private List<CommentItem> list;                                     //评论数据
    private List<Integer> recycleList;                                  //剧照数据
    //背景
    Drawable currentBg;                                                 //折叠标题的背景
    private Movie2 curMovie;                                            //查询的数据和海报的ID


    //收藏按钮状态标记
    private boolean collectMovie = true;//图标状态

    //数据库状态
    private boolean current_state = false;//当前收藏状态

    //评论记录标记
    private final int HAVE_DATE = 1;
    private final int NO_DATE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comments);
        x.view().inject(this);

        //2.设置ToolBar和collapsingTbarLayoutTitle
        initBudget();
        //3.初始电影数据
        initMovie();
        //4.绑定评论数据和剧照数据
        bindData();


    }

    //TODO: 2019/1/14 --0.初始ToolBar和collapsingTbarLayoutTitle        --已完成
    //TODO: 2019/1/15 -- .1注册评论列表的点击事件
    private void initBudget() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //listView的点击事件
        commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommentItem comment = (CommentItem) commentAdapter.getItem(position);
                OneCommentActivity.startOneCommentActivity(CommentsActivity.this,
                        comment,curMovie.getMovie().getMovieName());
            }
        });
        //collapsingTbarLayoutTitle的折叠完毕后显示电影名字
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int scrollRange = appBar.getTotalScrollRange();
                if (i == 0) {//展开状态
                    collapsingTbarLayoutTitle.setTitleEnabled(false);
                } else {//折叠
                    collapsingTbarLayoutTitle.setTitleEnabled(true);
                }
            }
        });
        //设置横向效果
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置布局管理器
        recyclerPhoto.setLayoutManager(linearLayoutManager);

    }

    // TODO: 2019/1/14  --1.初始Activity的数据，设置模糊背景            --待完成
    public static void startCommentsActivity(Context context, int movieId) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra("curMovie", movieId);
        context.startActivity(intent);
    }

    //进入本Activity显示的电影数据
    private void initMovie() {
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("curMovie", 0);
        curMovie = DataHelper.queryOneMovie(this, movieId);
        if (curMovie == null) {
            Toast.makeText(this, "数据错误！", Toast.LENGTH_SHORT).show();
        }
        Movie m = curMovie.getMovie();
        title.setText(m.getMovieName());
        director.setText("导演：" + m.getDirectorName());
        type.setText("类型：" + m.getType());
        actor.setText("主演：" + m.getMainActor());
        preview.setText("简介：" + m.getSummary());
        ivPoster.setImageResource(curMovie.getResId());
        //折叠标题显示电影名称
        collapsingTbarLayoutTitle.setTitle(m.getMovieName());
        User cur = CurrentUser.getCurrentUser();
        //判断是否收藏该电影
        current_state = DataHelper.isCollected(cur, m);
        collectMovie = !current_state;
        initCollectImage(current_state);

    }

    @Event(value = {R.id.ll_movie_preview, R.id.iv_movie_collect, R.id.tv_movie_collect, R.id.tv_movie_do_comment})
    private void handleMovieClick(View view) {
        switch (view.getId()) {
            case R.id.ll_movie_preview:
                showMovieDialog();                                      //弹出电影详情对话框
                break;
            case R.id.iv_movie_collect:                                 //点击收藏图标或者
            case R.id.tv_movie_collect:                                 //收藏文字可以切换收藏状态
                collectMovie(collectMovie);                             //收藏切换为实心图，取消收藏切换为空心图
                break;
            case R.id.tv_movie_do_comment:
                startCommentActivity();                                 //打开评论页
                break;

        }
    }

    // TODO: 2019/1/14  --4.处理头部的点击事件，弹出电影的简介对话框    --完成
    // TODO: 2019/1/14  --5.在4的基础上加载弹出对话框的内容             --完成
    private void showMovieDialog() {
        //1.创建movieView
        View movieView = LayoutInflater.from(CommentsActivity.this).inflate(R.layout.movie_dialog, null);
        //2.创建Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(CommentsActivity.this);
        final AlertDialog dialog = builder.create();
        //3.填充view
        dialog.setView(movieView);
        //4.todo 填充数据到对话框
        FitMovie2DialogHelper.bindMovie2Dialog(curMovie.getMovie(), movieView);
        //5.设置Dialog中的确定点击事件，以结束对话框
        TextView tvCloseDialog = movieView.findViewById(R.id.tv_movie_dialog_close);
        //6.设置弹窗的背景
        LinearLayout bg = movieView.findViewById(R.id.ll_movie_dialog_bg);
        bg.setBackground(currentBg);
        tvCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //动画效果
        Animation animation = new AlphaAnimation(0.5f, 1f);
        animation.setDuration(200);
        movieView.setAnimation(animation);

        dialog.show();
    }

    // TODO: 2019/1/17 收藏电影
    private void collectMovie(boolean collected) {
        if (collected) {
            collectMovie = false;
            ivCollect.setImageResource(R.drawable.ic_heart_pressed);
            DataHelper.collectMovie(CurrentUser.getCurrentUser(), curMovie.getMovie(), true);
        } else {
            collectMovie = true;
            ivCollect.setImageResource(R.drawable.ic_heart);
            DataHelper.collectMovie(CurrentUser.getCurrentUser(), curMovie.getMovie(), false);
        }
    }

    private void initCollectImage(boolean state) {
        if (state) {
            ivCollect.setImageResource(R.drawable.ic_heart_pressed);
        } else {
            ivCollect.setImageResource(R.drawable.ic_heart);
        }
    }

    // TODO: 2019/1/14  --7.跳转到写影评的Activity                      --待完成，需要知道评论Activity需要的数据和返回的数据
    private void startCommentActivity() {
        Movie m = curMovie.getMovie();
        Comment_Add_Activity.startComment_Add_Activity(this, m.getMovieName(), curMovie.getResId(), m.getId());

    }


    // TODO: 2019/1/14  --8.处理回传的数据，加入到ListView中?            --完成
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

            User cur = CurrentUser.getCurrentUser();
            Bundle bundle = data.getBundleExtra("RESULT");
            Comment comment = (Comment) bundle.getSerializable("NewComment");
            Log.e("新评论", comment.getCommentTitle());
            CommentItem newItem = new CommentItem(cur, comment);
            commentsListView.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
            commentAdapter.addComment(newItem);
        }
    }

    //线程更行UI
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HAVE_DATE:
                    pbLoading.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.GONE);
                    commentsListView.setVisibility(View.VISIBLE);
                    break;
                case NO_DATE:
                    pbLoading.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                    commentsListView.setVisibility(View.GONE);
                    break;
            }
        }
    };

    // TODO: 2019/1/16  --9.查询评论数据和剧照数据并绑定到UI上           --完成
    private void bindData() {
        //剧照
        //匹配剧照列表图片
        recycleList = DataHelper.matchPhotoList(curMovie.getMovie().getId(), curMovie.getMovie(),
                this);
        recycleAdapter = new _RecycleAdapter(recycleList);
        recyclerPhoto.setAdapter(recycleAdapter);
        if (recycleList == null || recycleList.size() == 0) {
            recyclerPhoto.setVisibility(View.GONE);
        }
        //查询评论
        list = DataHelper.queryComments(curMovie);
        commentAdapter = new _CommentAdapter(this, list, commentsListView);
        commentsListView.setAdapter(commentAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message m = new Message();
                    if (list.size() > 0) {
                        m.what = HAVE_DATE;
                    } else {
                        m.what = NO_DATE;
                    }
                    Thread.sleep(1000);
                    handler.sendMessage(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //模糊背景
        Drawable src = ivPoster.getDrawable();
        BitmapDrawable bd = (BitmapDrawable) src;
        Bitmap bitmap = bd.getBitmap();
        Bitmap bg = BackGroundHelper.fastblur(bitmap, 25, this);
        currentBg = new BitmapDrawable(bg);
        llBackGround.setBackground(currentBg);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
