package com.example.acer.practiceofbarmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_evaluate)
    private TextView tv_evaluate;

    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_progress;

    @ViewInject(R.id.civ_head)
    private CircleImageView civ_head;

    @ViewInject(R.id.rb_star)
    private RatingBar rb_star;

    private int[] image = {R.drawable.very_bad, R.drawable.bad, R.drawable.ok,
            R.drawable.good, R.drawable.very_good};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

    }

    @Event(value = R.id.rb_star, type = RatingBar.OnRatingBarChangeListener.class)
    private void onRatingChanged(RatingBar var1, float var2, boolean var3) {

        if (var2 < 1) {
            var2 = 1;  // 至少给一星的评价
            rb_star.setRating(1);
            return;
        }

        int num_star = (int) var2;
        pb_progress.setProgress(num_star);
        civ_head.setImageResource(image[num_star - 1]);
        switch (num_star) {
            case 1:
                tv_evaluate.setText("人才食堂的评价为：极差 1星");
                break;
            case 2:
                tv_evaluate.setText("人才食堂的评价为：较差 2星");
                break;
            case 3:
                tv_evaluate.setText("人才食堂的评价为：一般 3星");
                break;
            case 4:
                tv_evaluate.setText("人才食堂的评价为：不错 4星");
                break;
            case 5:
                tv_evaluate.setText("人才食堂的评价为：美味 5星");
                break;
        }

    }


}
