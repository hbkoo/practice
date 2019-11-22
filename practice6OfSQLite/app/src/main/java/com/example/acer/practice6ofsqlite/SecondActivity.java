package com.example.acer.practice6ofsqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplication().setTheme(R.style.NoTitleTheme);
        getApplicationContext().setTheme(R.style.NoTitleTheme);
//        setTheme(R.style.NoTitleTheme);

        setContentView(R.layout.activity_second);

        x.view().inject(this);



    }


    @Event(value = {R.id.btn_change})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                change();
                break;
        }
    }

    private void change() {

        Toast.makeText(this,"change",Toast.LENGTH_SHORT).show();

        getApplication().setTheme(R.style.NoTitleTheme);

//        UserInfo




    }


}
