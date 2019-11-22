package com.example.acer.wuli.collect_feedback.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.collect_feedback.adapter.LikeAdapter;
import com.example.acer.wuli.collect_feedback.helper.DataProcess;
import com.example.acer.wuli.lichenhao.activity.MainActivity;
import com.example.acer.wuli.model.Movie;
import com.example.acer.wuli.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏界面的fragment
 */

public class LikeFragment extends Fragment {

    private List<Movie> movies = new ArrayList<>();
    public static User user = null;

    private RecyclerView recyclerView;
    private TextView tv_no_like;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_like, container, false);
        tv_no_like = view.findViewById(R.id.tv_no_like);
        recyclerView = view.findViewById(R.id.rv_like);

        InitData();

        LikeAdapter adapter = new LikeAdapter(getActivity(), movies);
        adapter.setRecyclerView(recyclerView);
        adapter.setTv_no_like(tv_no_like);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void InitData() {
        movies = DataProcess.getInstance().queryCollectionMovie(MainActivity.user_ID);
        if (movies.size() == 0) {
            tv_no_like.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tv_no_like.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        Log.i("in LikeFragment :","movies' size:" +movies.size());
    }


}
