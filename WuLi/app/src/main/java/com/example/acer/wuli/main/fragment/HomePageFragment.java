package com.example.acer.wuli.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.acer.wuli.comments.activity.CommentsActivity;
import com.example.acer.wuli.R;
import com.example.acer.wuli.comments.activity.OneCommentActivity;
import com.example.acer.wuli.comments.bean.CommentItem;
import com.example.acer.wuli.comments.helper.CurrentUser;
import com.example.acer.wuli.main.adapter.HomePageAdapter;
import com.example.acer.wuli.main.adapter.MyPagerAdapter;
import com.example.acer.wuli.main.adapter.MyViewPager;
import com.example.acer.wuli.model.Comment;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    View view;
    private MyViewPager vp_picset;
    List<View> itemViews;
    private MyPagerAdapter adapter;
    private ImageView pointimgs[] = new ImageView[3];
    private int[] imgae_ids = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3};

    private ListView iv_movielist;
    List<Movie> movieList = new ArrayList<Movie>();
    HomePageAdapter homePageAdapter = null;

    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        vp_picset = view.findViewById(R.id.vp_picset);
        if (isFirst) {
            itemViews = installItems();
            adapter = new MyPagerAdapter(getActivity(), itemViews);
            vp_picset.setAdapter(adapter);
            initPoint();
            initMovieList();
            vp_picset.setCurrentItem(itemViews.size() * 500);
            isFirst = false;
        }
        vp_picset.startScroll();

        vp_picset.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 3; i++) {
                    pointimgs[i].setImageResource(R.drawable.point);
                    pointimgs[position % 3].setImageResource(R.drawable.point_select);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initPoint() {
        pointimgs[0] = view.findViewById(R.id.iv_point0);
        pointimgs[1] = view.findViewById(R.id.iv_point1);
        pointimgs[2] = view.findViewById(R.id.iv_point2);

    }

    public List<View> installItems() {
        List<View> views = new ArrayList<>();
        int drawables[] = {R.drawable.fightclub_1, R.drawable.godfather_1, R.drawable.temp};
        ImageView ivItem;
        for (int i = 0; i < 3; i++) {
            RelativeLayout rlHeaderItem = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.pager_item, null);
            ivItem = rlHeaderItem.findViewById(R.id.iv_pager_item);
            ivItem.setImageResource(drawables[i]);
            ivItem.setId(imgae_ids[i]);
            views.add(rlHeaderItem);
            ivItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        //TODO 直接跳转
                        case R.id.pager_image1:
                            //Movie movie=LitePal.find(Movie.class, 8);
                            CommentsActivity.startCommentsActivity(getActivity(), 8);
                            break;
                        case R.id.pager_image2:
                            //Movie movie=LitePal.find(Movie.class, 9);
                            CommentsActivity.startCommentsActivity(getActivity(), 9);
                            break;
                        case R.id.pager_image3:
                            Comment comment=LitePal.find(Comment.class, 17);
                            User cur= CurrentUser.getCurrentUser();
                            CommentItem item=new CommentItem(cur,comment);
                            Movie movie=LitePal.find(Movie.class, 1);
                            OneCommentActivity.startOneCommentActivity(getContext(),item,movie.getMovieName());
                            break;
                    }
                }
            });
        }
        return views;
    }

    private void initMovieList() {
        movieList.clear();
        iv_movielist = view.findViewById(R.id.iv_movielist);
        //TODO 数据库操作
        List<Movie> movies = LitePal.where("isshow =?", "1").find(Movie.class);

        movieList.addAll(movies);
        homePageAdapter = new HomePageAdapter(getActivity(), movieList);
        iv_movielist.setAdapter(homePageAdapter);

        iv_movielist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position;
                Movie movie = movieList.get(index);
                //TODO 跳转
                CommentsActivity.startCommentsActivity(getActivity(), movie.getId());
            }
        });
    }

    @Override
    public void onStop() {
        vp_picset.stopScroll();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        vp_picset.stopScroll();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirst = true;

    }
}