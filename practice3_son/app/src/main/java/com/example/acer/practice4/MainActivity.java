package com.example.acer.practice4;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.practice4.Adapter.LVAdapterNews;
import com.example.acer.practice4.Beans.News;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.lv_news)
    private ListView lv_news;

    private List<News> newsList;   // 数据源
    private LVAdapterNews adapterNews; // 适配器

    private int[] image = {R.drawable.aty_read_pic_1, R.drawable.aty_read_pic_2,
            R.drawable.aty_read_pic_3, R.drawable.aty_read_pic_4, R.drawable.aty_read_pic_5};
    private String[] titles = {"我们一起来读书", "中国梦，我的梦", "刑事和解",
            "友书会", "盛装民乐"};
    private String[] address = {"市图书馆一楼大厅", "北京电视台", "武汉理工大学西院大礼堂",
            "中山路文化中心大厅", "国家大剧院"};
    private String[] times = {"2019年1月6日", "2019年1月24日", "2019年2月6日",
            "2019年3月8日", "2019年4月23日"};
    private String[] category = {"读书会", "演讲", "演讲", "读书会", "音乐会"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);

        Init();

    }

    private void Init() {

        InitData();

        adapterNews = new LVAdapterNews(this, newsList);

        lv_news.setAdapter(adapterNews);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeItemDialog(i);
                Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();

            }
        });

        lv_news.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteItemDialog(i);
                return true;  // 不再继续向下传
            }

        });

    }

    // 显示并修改条目的信息
    private void changeItemDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_change,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText et_title = view.findViewById(R.id.et_title);
        final EditText et_address = view.findViewById(R.id.et_address);
        final EditText et_time = view.findViewById(R.id.et_time);
        final EditText et_category = view.findViewById(R.id.et_category);
        TextView tv_change = view.findViewById(R.id.tv_change);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel_in_change);

        final News news = newsList.get(position);
        et_title.setText(news.getTitle());
        et_time.setText(news.getTime());
        et_address.setText(news.getAddress());
        et_category.setText(news.getCategory());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = et_title.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String time = et_time.getText().toString().trim();
                String category = et_category.getText().toString().trim();
                news.setTitle(title);
                news.setAddress(address);
                news.setCategory(category);
                news.setTime(time);
                newsList.remove(position);
                newsList.add(news);
                adapterNews.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
            }
        });


    }

    // 删除记录对话框
    private void deleteItemDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_delete,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsList.remove(position);
                adapterNews.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
            }
        });


    }


    // 初始化数据源
    private void InitData() {
        newsList = new ArrayList<>();
        News news;
        for (int i = 0; i < image.length; i++) {
            news = new News(image[i], titles[i], "地点：" + address[i],
                    "时间：" + times[i], category[i]);
            newsList.add(news);
        }
    }


}
