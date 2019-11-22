package com.example.acer.wuli.collect_feedback.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.wuli.collect_feedback.fragment.LikeFragment;
import com.example.acer.wuli.R;
import com.example.acer.wuli.collect_feedback.helper.DataProcess;
import com.example.acer.wuli.comments.activity.CommentsActivity;
import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.model.Movie;

import java.util.List;

/**
 * 展示收藏的喜爱电影适配器
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context context;
    private List<Movie> movies;

    private RecyclerView recyclerView;
    private TextView tv_no_like;

    public LikeAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_like, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        // 为整个布局条目设置点击事件
        viewHolder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityToDetailInfo(viewHolder.getAdapterPosition());  // 界面跳转
            }
        });

        // 为收藏图标添加点击事件
        viewHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collection(viewHolder.getAdapterPosition());      // 收藏事件处理
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.iv_movie.setImageResource(getResource(movie.getPostID()));
        holder.tv_movie_name.setText(String.format("影片名：%s", movie.getMovieName()));
        holder.tv_director.setText(String.format("导演：%s", movie.getDirectorName()));
        holder.tv_main_actor.setText(String.format("主演：%s", movie.getMainActor()));
        holder.tv_score.setText(String.format("评分：%s", movie.getPoint()));
        holder.tv_comment_num.setText("" + movie.getCommentNumber());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private View item_view;
        private ImageView iv_movie;
        private TextView tv_movie_name;
        private TextView tv_director;
        private TextView tv_main_actor;
        private TextView tv_score;
        private TextView tv_comment_num;
        private ImageView iv_like;

        public ViewHolder(View itemView) {
            super(itemView);
            // 获取每一条列表的组件的控件
            item_view = itemView;
            iv_movie = itemView.findViewById(R.id.item_iv_movie);
            tv_movie_name = itemView.findViewById(R.id.item_tv_movie_name);
            tv_director = itemView.findViewById(R.id.item_tv_director);
            tv_main_actor = itemView.findViewById(R.id.item_tv_main_actor);
            tv_score = itemView.findViewById(R.id.item_tv_score);
            tv_comment_num = itemView.findViewById(R.id.item_tv_comment_num);
            iv_like = itemView.findViewById(R.id.item_iv_like);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_iv_movie:
                Toast.makeText(context, "点击图片", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    // TODO 界面跳转，跳到电影的详细信息界面
    private void startActivityToDetailInfo(int position) {
        Movie movie = movies.get(position);
        Log.i("详细信息：", movie.toString());
        CommentsActivity.startCommentsActivity(context,movie.getId());
    }

    // TODO 收藏按钮点击事件
    private void collection(final int position) {

        final Movie movie = movies.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancel_like,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView tv_ok = view.findViewById(R.id.tv_ok);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 取消收藏操作

                // 取消收藏的数据库数据
                boolean result = DataProcess.getInstance().deleteCollectionMovie(
                        MainActivity.user_ID,
                        movie);

                if (result) {
                    Toast.makeText(context,"已取消收藏",Toast.LENGTH_SHORT).show();
                    movies.remove(position);
                    notifyDataSetChanged();

                    if (movies.size() == 0) {
                        tv_no_like.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(context,"取消失败\n请稍后重试...",Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    // 图片资源字符串转换为int类型
    private int getResource(String imageName) {

        int resId = context.getResources().getIdentifier(imageName, "mipmap",
                context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setTv_no_like(TextView tv_no_like) {
        this.tv_no_like = tv_no_like;
    }
}
