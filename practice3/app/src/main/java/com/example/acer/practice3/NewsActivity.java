package com.example.acer.practice3;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.practice3.Adapter.LVAdapterNews;
import com.example.acer.practice3.Beans.News;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_news);

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
        View view = getLayoutInflater().inflate(R.layout.dialog_change, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        final EditText et_title = view.findViewById(R.id.et_title);
        final EditText et_address = view.findViewById(R.id.et_address);
        final TextView et_time = view.findViewById(R.id.et_time);
        final EditText et_category = view.findViewById(R.id.et_category);
        TextView tv_change = view.findViewById(R.id.tv_change);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel_in_change);

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(et_time);
            }
        });

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

                if (checkChange(news, title, address, time, category)) {
                    news.setTitle(title);
                    news.setAddress(address);
                    news.setCategory(category);
                    news.setTime(time);
                    adapterNews.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(NewsActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setTime(final TextView et_time) {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i + "年" + (i1 + 1) + "月" + i2 + "日";
                et_time.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }

    // 删除记录对话框
    private void deleteItemDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
                Toast.makeText(NewsActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
            }
        });


    }


    // 初始化数据源
    private void InitData() {
        newsList = new ArrayList<>();
        News news;
        for (int num = 1; num < 11; num++) {
            for (int i = 0; i < image.length; i++) {
                news = new News(image[i], num + "---" + titles[i], address[i],
                        times[i], category[i]);
                newsList.add(news);
            }
        }

    }

    // 检查信息是否改动
    private boolean checkChange(News news, String title, String address, String time, String category) {

        if (title.equals("")) {
            Toast.makeText(this, "题目不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address.equals("")) {
            Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (time.equals("")) {
            Toast.makeText(this, "时间不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (category.equals("")) {
            Toast.makeText(this, "类别不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (title.equals(news.getTitle()) && address.equals(news.getAddress())
                && time.equals(news.getTime()) && category.equals(news.getCategory())) {
            Toast.makeText(this, "信息暂未修改！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Event(R.id.iv_back)
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
