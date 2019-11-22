package com.example.acer.wuli.comments.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.acer.wuli.R;

import java.util.List;

/**
 * 电影剧照的适配器
 */
public class _RecycleAdapter extends RecyclerView.Adapter<_RecycleAdapter.ViewHolder> {
    private List<Integer> list;

    public _RecycleAdapter(List list) {
        this.list = list;
    }

    @NonNull
    @Override
    //创建ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycle_item_photo, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    //绑定单条样式中的组件
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.imageView.setImageResource(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 自定义的ViewHolder来保存单条演示样式中组件
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_recycle_item_photo);
        }
    }
}
