package com.example.acer.wuli.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.wuli.R;
import com.example.acer.wuli.model.Movie;

import java.text.DecimalFormat;
import java.util.List;


public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> data;

    public SearchResultAdapter(Context context, List<Movie> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_list_item,null);
        ImageView iv_post=view.findViewById(R.id.iv_search_result_post);
        TextView tv_moviename=view.findViewById(R.id.tv_search_result_moviename);
        TextView tv_director=view.findViewById(R.id.tv_search_result_director);
        TextView tv_actor=view.findViewById(R.id.tv_search_result_actor);
        TextView tv_point=view.findViewById(R.id.tv_search_result_point);
        TextView tv_conment=view.findViewById(R.id.tv_search_result_comment);

        //TODO 通过字符串获取ID
        String postName=data.get(position).getPostID();
 //       iv_post.setImageResource(data.get(position).getPostID());
        int postID=getResource(postName,context);
        iv_post.setImageResource(postID);
        tv_moviename.setText(data.get(position).getMovieName());
        tv_director.setText("导演："+data.get(position).getDirectorName());
        tv_actor.setText("主演："+data.get(position).getMainActor());
        tv_point.setText("评分："+data.get(position).getPoint());
        int commentnum=data.get(position).getCommentNumber();
        DecimalFormat df = new DecimalFormat("#.0");
        if(commentnum>1000&&commentnum<10000){
            double commentnumber=Double.parseDouble(df.format((double)commentnum/1000));
            tv_conment.setText(commentnumber+"k+");
        }else if(commentnum>=10000){
            double commentnumber=Double.parseDouble(df.format((double)commentnum/10000));
            tv_conment.setText(commentnumber+"w+");
        }else{
            tv_conment.setText(""+commentnum);
        }

        return view;
    }
    public int getResource(String name,Context context){
        Resources r=context.getResources();
        int id = r.getIdentifier(name,"drawable",context.getPackageName());
        return id;
    }
}
