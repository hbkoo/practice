package com.example.acer.practice4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @ViewInject(R.id.civ_head)
    private CircleImageView iv_head;

    @ViewInject(R.id.rg_male_female)
    private RadioGroup rg_male_female;

    @ViewInject((R.id.rb_male))
    private RadioButton rb_male;
    @ViewInject(R.id.rb_female)
    private RadioButton rb_female;

    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_gress;

    @ViewInject(R.id.seek_bar)
    private SeekBar seekBar;

    @ViewInject(R.id.rb_star)
    private RatingBar rb_star;


    @ViewInject(R.id.tv_seekbar_show)
    private TextView tv_seekbar_show;

    @ViewInject(R.id.tv_stars_show)
    private TextView tv_stars_show;


    int progress = 0;  // 进度条的进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);

        seekBar.setOnSeekBarChangeListener(this);

    }

    @Event(value = {R.id.civ_head, R.id.btn_auto_add, R.id.btn_hand_add,R.id.btn_menu})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.civ_head:
                Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_auto_add:
                progress = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress < 100) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pb_gress.setProgress(++progress);
                                }
                            });
                        }
                        progress = 0;
                    }
                }).start();
                break;
            case R.id.btn_hand_add:
                progress += 10;
                pb_gress.setProgress(progress);
                Log.i("progress:", "" + progress);
                if (progress >= 100) {
                    progress = 0;
                }
                break;
            case R.id.btn_menu:
                startActivity(new Intent(MainActivity.this,MenuActivity.class));
                break;
        }
    }

    // radioGround内部的RadioButton选项改变的监听事件
    @Event(value = {R.id.rg_male_female}, type = RadioGroup.OnCheckedChangeListener.class)
    private void oncheckchange(RadioGroup radioGroup, int checkId) {

        if (radioGroup.getCheckedRadioButtonId() == R.id.rb_male) {
            iv_head.setImageResource(R.drawable.male);
        } else if (checkId == R.id.rb_female) {
            iv_head.setImageResource(R.drawable.femal);
        }
    }

    // seekBar的数据改变时的监听
    @Override
    public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
        Log.i("seekbar:", "" + var2);
        tv_seekbar_show.setText("" + var2);
    }

    // seekBar的开始拖动时的位置监听
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i("seekbar start at :" , String.valueOf(seekBar.getProgress()));
    }

    // seekBar的结束拖动的位置监听
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i("seekbar end at :" , String.valueOf(seekBar.getProgress()));
    }

    // ratingBar 添加数据改变事件监听
    @Event(value = R.id.rb_star, type = RatingBar.OnRatingBarChangeListener.class)
    private void onRatingChanged(RatingBar var1, float var2, boolean var3) {
        tv_stars_show.setText("评分" + var2);
        rb_star.setEnabled(false);
    }


}
