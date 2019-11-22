package com.example.acer.practice3;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    @ViewInject(R.id.btn_2)
    private Button btn_2;

    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.tv_time)
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);

    }


    @Event(value = {R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5,R.id.btn_6,R.id.btn_7})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                testBtn();
                break;
            case R.id.btn_2:
                userDialog();
                break;
            case R.id.btn_3:
                dateDialog();
                break;
            case R.id.btn_4:
                timeDialog();
                break;
            case R.id.btn_5:
                progressDialog();
                break;
            case R.id.btn_6:
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
                break;
            case R.id.btn_7:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
        }
    }

    private void progressDialog() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "请稍等", "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();


    }

    private void timeDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timeDialog = new TimePickerDialog(this, 1, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + "时" + i1 + "分";
                tv_time.setText(time);
                Log.i("time:", time);
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timeDialog.show();

    }

    private void dateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i + "年" + (i1 + 1) + "月" + i2 + "日";
                tv_date.setText(date);
                Log.i("date:", date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();

    }

    private void userDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle("请输入玩家昵称");
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();


        Button btn_ok = view.findViewById(R.id.btn_dialog_ok);
        Button btn_cancel = view.findViewById(R.id.btn_dialog_cancel);
        final EditText et_username = view.findViewById(R.id.et_username);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(view.getContext(), "请输入昵称！", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    btn_2.setText(username);
                }
            }
        });


    }


    private void testBtn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle("提示");
        builder.setMessage("是否删除？");
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);
        builder.show();
    }


}
