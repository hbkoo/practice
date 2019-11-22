package com.example.acer.wuli.main.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.acer.wuli.R;
import com.example.acer.wuli.main.adapter.SearchResultAdapter;
import com.example.acer.wuli.comments.activity.CommentsActivity;
import com.example.acer.wuli.model.Movie;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class MovieListActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_result;
    private ListView lv_search_result;
    private LinearLayout ll_no_result;
    List<Movie> movieList=new ArrayList<Movie>();
    SearchResultAdapter searchResultAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        init();
    }
    public void init() {
        iv_back=findViewById(R.id.iv_back);
        tv_result=findViewById(R.id.tv_result);
        lv_search_result=findViewById(R.id.lv_search_result);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        String searchType=intent.getStringExtra("searchType");
        String searchContent=intent.getStringExtra("searchContent");
        Log.i("text",searchContent+"0");
        List<Movie> movies=null;
        if(searchType.equals("name")){
            //TODO 通过名字搜索
            movies= LitePal.where("moviename like ?","%"+searchContent+"%").order("point desc").find(Movie.class);

            Log.i("text",movies.size()+"");
        }else if(searchType.equals("type")){
            tv_result.setText(searchContent);
            //TODO 全部，按评分排序
            if(searchContent.equals("全部")){
                movies = LitePal.where().order("point desc").find(Movie.class);
            }
            //TODO 热门，按收藏数排序
            else if(searchContent.equals("热门")){
                movies = LitePal.where().order("collectnumber desc").find(Movie.class);
            }
            //TODO 具体类型搜索
            else{
                movies = LitePal.where("type like ?","%"+searchContent+"%").order("point desc").find(Movie.class);
            }
        }
        movieList.addAll(movies);
        if(movieList.size()==0){
            lv_search_result.setVisibility(View.GONE);
            ll_no_result=findViewById(R.id.ll_no_result);
            ll_no_result.setVisibility(View.VISIBLE);
        }else{
            searchResultAdapter=new SearchResultAdapter(MovieListActivity.this,movieList);
            lv_search_result.setAdapter(searchResultAdapter);
        }

        lv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index=position;
                Movie movie=movieList.get(index);
                //TODO 跳转
                CommentsActivity.startCommentsActivity(MovieListActivity.this,movie.getId());
            }
        });
    }
}
