package com.example.acer.wuli.collect_feedback.activity;

import android.app.AlertDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.wuli.R;
import com.example.acer.wuli.collect_feedback.helper.DataProcess;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class ReturnActivity extends AppCompatActivity {

    @ViewInject(R.id.et_return)
    private EditText et_return;

    @ViewInject(R.id.toolbar_return)
    private Toolbar toolbar_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        x.view().inject(this);

        setSupportActionBar(toolbar_return);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Event(value = {R.id.btn_return})
    private void onclick(View view) {
        switch (view.getId()){
            case R.id.btn_return:
                returnInfo();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnInfo() {

        String return_text = et_return.getText().toString().trim();
        if (return_text.equals("")){
            Toast.makeText(this,"请输入反馈的信息，我们会及时处理",Toast.LENGTH_SHORT).show();
        } else {

            // TODO 提交反馈的数据
            boolean result = DataProcess.getInstance().addFeedback(
                    MainStartActivity0.user.getID(),return_text);
            if (result) {
                showDialog();
                Toast.makeText(this,"我们会及时处理反馈问题",Toast.LENGTH_SHORT).show();
                et_return.setText("");
            } else {
                Toast.makeText(this,"出错啦...",Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_feedback_success,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView tv_ok = view.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
