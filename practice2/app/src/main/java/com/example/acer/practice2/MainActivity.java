package com.example.acer.practice2;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_ADD_ADDRESS = 1;

    @ViewInject(R.id.tv_user_info)
    private TextView tv_user_info;
    @ViewInject(R.id.tv_address_info)
    private TextView tv_address_info;

    @ViewInject(R.id.lv_address)
    private ListView lv_address;

    private List<Map<String,Object>> data;  // 列表数据
    private SimpleAdapter adapter;

    private int selected_index = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        x.view().inject(this);

        InitListVIew();
    }

    @Event(value = {R.id.btn_add, R.id.iv_back,R.id.iv_edit})
    private void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                selected_index = -1;
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit:
                Intent edit_intent = new Intent(this,AddAddressActivity.class);
                String user_info = tv_user_info.getText().toString();
                String address_info = tv_address_info.getText().toString();
                String[] user_tel_info = user_info.split(" ");
                String[] address_detail_info = address_info.split(" ");
                edit_intent.putExtra("username",user_tel_info[0]);
                edit_intent.putExtra("tel",user_tel_info[user_tel_info.length-1]);
                edit_intent.putExtra("address",address_detail_info[0]);
                edit_intent.putExtra("detailAddress",address_detail_info[address_detail_info.length-1]);
                startActivityForResult(edit_intent,REQUEST_CODE_ADD_ADDRESS);
                break;
        }
    }

    // 结果返回回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_CODE_ADD_ADDRESS:
                if (resultCode == RESULT_OK) {
                    updateFromAddAddress(data); //处理返回来的数据
                }
                break;

        }
    }

    // 处理添加新的地址返回来的数据
    private void updateFromAddAddress(Intent intent) {

        if (selected_index != -1) {
            data.remove(selected_index);
        }

        String username = intent.getStringExtra("username");
        String tel = intent.getStringExtra("tel");
        String address = intent.getStringExtra("address");
        String detailAddress = intent.getStringExtra("detailAddress");

        // 将新增的数据添加到列表ListView中
        Map<String,Object> item = new HashMap<>();
        item.put("user_info",username + "  " + tel);
        item.put("address_info",address + "  " + detailAddress);
        data.add(item);
        adapter.notifyDataSetChanged();  // 更新列表中的数据

    }

    // 初始化ListView控件
    private void InitListVIew() {
        data = new ArrayList<>();
        Map<String,Object> item = new HashMap<>();
        item.put("user_info","张三  15587985635");
        item.put("address_info","湖北省武汉市   武汉理工大学余家头校区");
        data.add(item);
        String[] from = {"user_info","address_info"};
        int[] to = {R.id.tv_user_info,R.id.tv_address_info};
        adapter = new SimpleAdapter(this,data,R.layout.address_item,from,to);
        lv_address.setAdapter(adapter);

        lv_address.setOnItemClickListener(new itemClick());

    }

    private class itemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            selected_index = i;

//            changeAddressInActivity();
            changeAddressInDialog();

        }
    }

    private void changeAddressInActivity() {

        Map<String,Object> item = data.get(selected_index);
        Intent edit_intent = new Intent(MainActivity.this,AddAddressActivity.class);
        String user_info = (String) item.get("user_info");
        String address_info = (String) item.get("address_info");
        String[] user_tel_info = user_info.split(" ");
        String[] address_detail_info = address_info.split(" ");
        edit_intent.putExtra("username",user_tel_info[0]);
        edit_intent.putExtra("tel",user_tel_info[user_tel_info.length-1]);
        edit_intent.putExtra("address",address_detail_info[0]);
        edit_intent.putExtra("detailAddress",address_detail_info[address_detail_info.length-1]);
        edit_intent.putExtra("editTag",true);
        startActivityForResult(edit_intent,REQUEST_CODE_ADD_ADDRESS);
    }


    private void changeAddressInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_detail_address,null);
        builder.setTitle("地址修改");
        builder.setView(view);
        final AlertDialog dialog = builder.create(); //需要在show之前调用
        dialog.show();

        final EditText et_username = view.findViewById(R.id.et_name);
        final EditText et_tel = view.findViewById(R.id.et_tel);
        final EditText et_address = view.findViewById(R.id.et_address);
        final EditText et_detailAddress = view.findViewById(R.id.et_detail_address);
        Button btn_change = view.findViewById(R.id.btn_save);


        Map<String,Object> item = data.get(selected_index);
        String user_info = (String) item.get("user_info");
        String address_info = (String) item.get("address_info");
        String[] user_tel_info = user_info.split(" ");
        String[] address_detail_info = address_info.split(" ");

        et_username.setText(user_tel_info[0]);
        et_tel.setText(user_tel_info[user_tel_info.length-1]);
        et_address.setText(address_detail_info[0]);
        et_detailAddress.setText(address_detail_info[address_detail_info.length-1]);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                String tel = et_tel.getText().toString().trim();
                String address = et_address.getText().toString().trim();
                String detailAddress = et_detailAddress.getText().toString().trim();

                // 将新增的数据添加到列表ListView中
                data.remove(selected_index);
                Map<String,Object> item = new HashMap<>();
                item.put("user_info",username + "  " + tel);
                item.put("address_info",address + "  " + detailAddress);
                data.add(item);
                adapter.notifyDataSetChanged();  // 更新列表中的数据
                dialog.dismiss();
            }
        });




    }


}
