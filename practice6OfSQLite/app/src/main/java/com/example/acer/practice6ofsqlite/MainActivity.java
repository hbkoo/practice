package com.example.acer.practice6ofsqlite;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.practice6ofsqlite.com.example.acer.adapter.StaffAdapterRV;
import com.example.acer.practice6ofsqlite.com.example.acer.bean.Staff;
import com.example.acer.practice6ofsqlite.com.example.acer.db.DBHelper;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;

    @ViewInject(R.id.rv_staffs)
    private RecyclerView rv_staffs;

    private StaffAdapterRV adapterRV = null;
    private List<Staff> staffList = new ArrayList<>();

    @ViewInject(R.id.item_refresh)
    private MenuItem item_refresh;

    @ViewInject(R.id.iv_add)
    private ImageView iv_add;
    @ViewInject(R.id.iv_refresh)
    private ImageView iv_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        DBHelper helper = new DBHelper(this);
        database = helper.getWritableDatabase();

        InitRecycleView();
        refreshStaff();




    }

    // 初始化recycleVIew控件
    private void InitRecycleView() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        rv_staffs.setLayoutManager(manager);

        adapterRV = new StaffAdapterRV(this, staffList);
        adapterRV.setDatabase(database);
        rv_staffs.setAdapter(adapterRV);

    }

    // 从数据库中获取数据
//    private void InitData() {
//        staffList = new ArrayList<>();
//        refreshStaff();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.staff_menu,menu);
//        menu.add(0, 1, 0, "新增");
//        menu.add(0, 2, 0, "删除");
//        menu.add(0, 3, 0, "查询");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                adapterRV.addDialog(iv_add);
                break;
            case R.id.item_add:
                adapterRV.addDialog(iv_add);
                break;
            case R.id.item_refresh:
                refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Event(value = {R.id.iv_refresh,R.id.iv_add,R.id.btn_second})
    private void onclick(View view) {
        switch (view.getId()){
            case R.id.iv_add:
                addStaff();
                break;
            case R.id.iv_refresh:
                refresh();
                break;
            case R.id.btn_second:
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                break;
        }
    }

    // 新增职工信息
    private void addStaff() {
        // 加载动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_add,"rotation",0,45);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        // 打开新增对话框
        adapterRV.addDialog(iv_add);

    }

    // 刷新数据
    private void refresh() {

        RotateAnimation rotateAnimation = new RotateAnimation(0,720,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(3000);
        iv_refresh.startAnimation(rotateAnimation);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(MainActivity.this,"正在获取...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this,"重新获取成功！",Toast.LENGTH_SHORT).show();
                refreshStaff();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    // 获取信息数据
    private void refreshStaff() {

        staffList.clear();
        Cursor cursor = database.rawQuery("select * from staff",null);
//        Cursor cursor = database.query("staff", null, null,null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String dname = cursor.getString(2);
            String job = cursor.getString(3);
            float salary = cursor.getFloat(4);
            Staff staff = new Staff(id,name,dname,job,salary);
            staffList.add(staff);
            Log.i("staff:",""+staff.toString());
        }
        cursor.close();

        adapterRV.notifyDataSetChanged();
    }

}
