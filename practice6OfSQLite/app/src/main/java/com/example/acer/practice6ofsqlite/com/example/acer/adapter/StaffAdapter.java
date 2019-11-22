package com.example.acer.practice6ofsqlite.com.example.acer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.acer.practice6ofsqlite.com.example.acer.bean.Staff;

import java.util.List;

/**
 * Created by 19216 on 2019/1/13.
 */

public class StaffAdapter extends BaseAdapter {

    private Context context;
    private List<Staff> staffList;

    public StaffAdapter(Context context, List<Staff> staffList) {
        this.context = context;
        this.staffList = staffList;
    }


    @Override
    public int getCount() {
        return staffList.size();
    }

    @Override
    public Object getItem(int i) {
        return staffList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
