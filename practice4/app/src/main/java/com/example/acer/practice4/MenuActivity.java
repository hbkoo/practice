package com.example.acer.practice4;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.rarepebble.colorpicker.ColorPickerView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Method;

public class MenuActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_main)
    private TextView tv_main;

    private int textSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        x.view().inject(this);

        registerForContextMenu(tv_main);  // 注册上下文菜单
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        /**
         * i ==> groupId  ====> 划分组，可以成组判断
         * i1 ==> itemId  ====> 方便之后的点击事件的查找对应
         * i2 ==> order         0:默认序列
        * */
        MenuItem item1 = menu.add(1, R.id.menu_item_scan, 0, "扫一扫");
        item1.setIcon(R.drawable.scan);

        MenuItem item2 = menu.add(1, R.id.menu_item_add_friend, 0, "添加好友");
        item2.setIcon(android.R.drawable.ic_menu_add);

        MenuItem item3 = menu.add(1, R.id.menu_item_round, 0, "摇一摇");
        item3.setIcon(android.R.drawable.ic_menu_directions);

        MenuItem item4 = menu.add(1, R.id.menu_item_get_pay, 0, "收付款");
        item4.setIcon(android.R.drawable.ic_menu_crop);

        SubMenu subMenu = menu.addSubMenu(1, R.id.menu_item_add_new, 0, "新建事项");
        subMenu.add(1, R.id.submenu_addNew_leave, 0, "请假申请");
        subMenu.add(1, R.id.submenu_addNew_change, 0, "调休申请");


//        getMenuInflater().inflate(R.menu.menu_test,menu);
//        return true;

        setIconEnable(menu, true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_scan:
                Toast.makeText(this, "点击了扫一扫", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_add_friend:
                Toast.makeText(this, "点击了添加好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_round:
                Toast.makeText(this, "点击了摇一摇", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_get_pay:
                Toast.makeText(this, "点击了收付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item_add_new:
                Toast.makeText(this, "点击了新建事项", Toast.LENGTH_SHORT).show();
                break;
            case R.id.submenu_addNew_leave:
                Toast.makeText(this, "点击了请假申请", Toast.LENGTH_SHORT).show();
                break;
            case R.id.submenu_addNew_change:
                Toast.makeText(this, "点击了调休申请", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(1, 0, 0, "加大字体");
        menu.add(1, 1, 0, "恢复默认");
        menu.add(1, 2, 0, "减小字体");
        menu.add(1, 3, 0, "设置背景");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                if (textSize >= 40) {
                    Toast.makeText(this, "字体已经达到最大!", Toast.LENGTH_SHORT).show();
                } else {
                    textSize += 8;
                    tv_main.setTextSize(textSize);
                }
                break;
            case 1:
                textSize = 18;
                tv_main.setTextSize(textSize);
                break;
            case 2:
                if (textSize <= 10) {
                    Toast.makeText(this, "字体已经达到最小!", Toast.LENGTH_SHORT).show();
                } else {
                    textSize -= 8;
                    tv_main.setTextSize(textSize);
                }
                break;
            case 3:
                changeBackground();
                break;

        }

        return super.onContextItemSelected(item);
    }

    private void changeBackground() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择颜色");

        View view = getLayoutInflater().inflate(R.layout.dialog_change_background, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView tv_change = view.findViewById(R.id.tv_change);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel_in_change);

        final ColorPickerView colorPickerView = view.findViewById(R.id.cpv_color);

        Drawable drawable = tv_main.getBackground();
        ColorDrawable dra = (ColorDrawable) drawable;
//        container.setBackgroundColor(dra.getColor());

        colorPickerView.setColor(dra.getColor());


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("color:", "" + colorPickerView.getColor());
                tv_main.setBackgroundColor(colorPickerView.getColor());
                dialog.dismiss();
            }
        });

    }
}
