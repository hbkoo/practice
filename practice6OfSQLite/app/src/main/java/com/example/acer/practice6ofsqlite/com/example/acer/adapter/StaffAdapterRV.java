package com.example.acer.practice6ofsqlite.com.example.acer.adapter;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.practice6ofsqlite.R;
import com.example.acer.practice6ofsqlite.com.example.acer.bean.Staff;

import java.util.List;

/**
 * Created by 19216 on 2019/1/13.
 */

public class StaffAdapterRV extends RecyclerView.Adapter<StaffAdapterRV.ViewHolder> {

    private static int num = 0;

    private Context context;
    private List<Staff> staffList;
    private SQLiteDatabase database = null; // 数据库操作对象

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public StaffAdapterRV(Context context, List<Staff> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    // 创建每一条条目
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 测试
        Log.i("onCreateViewHolder:", "加载创建！");
        num++;
        Log.i("num:", "" + num);

        // 加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.iv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w("iv_change is clicked:", "adapter position is " + viewHolder.getAdapterPosition());
                Log.w("layout position is ", "" + viewHolder.getLayoutPosition());
                Log.w("old position is ", "" + viewHolder.getOldPosition());

                openDialog(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteStaff(viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    // 每当条目滑到每一条时会加载数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Staff staff = staffList.get(position);
        holder.tv_name.setText(staff.getSname());
        holder.tv_part.setText(staff.getDname());
        holder.tv_job.setText(staff.getSjob());
        holder.tv_salary.setText(String.valueOf(staff.getSalary()));


        Log.v("onBindViewHolder:", "加载重新绑定！");
        Log.v("staff:", staff.toString());
        Log.v("num:", "" + num);


    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    // 保存缓存信息
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_part, tv_job, tv_salary;
        private ImageView iv_change, iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_part = itemView.findViewById(R.id.tv_part);
            tv_job = itemView.findViewById(R.id.tv_job);
            tv_salary = itemView.findViewById(R.id.tv_salary);
            iv_change = itemView.findViewById(R.id.iv_change);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }


    // 删除职工信息
    private void deleteStaff(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView tv_ok = view.findViewById(R.id.tv_delete);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFromDB(position);
                dialog.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    // 从数据库中删除
    private void deleteFromDB(int position) {
        int id = staffList.get(position).getId();
        int result = database.delete("staff", "_id = ?",
                new String[]{String.valueOf(id)});
        if (result == 0) {
            Toast.makeText(context, "删除失败，请稍后重试...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
            staffList.remove(position);
            this.notifyDataSetChanged();
        }
    }

    // 为主界面提供添加新职工的接口
    public void addDialog(ImageView iv_add) {
        openDialog("新增", 0,iv_add);
    }

    private void openDialog(int position){
        openDialog("修改",position,null);
    }

    /**
     * @param position : 点击的序号
     * @param tag      : 新增/修改
     * @param iv_add : 新增按钮的图标
     */
    private void openDialog(final String tag, int position, final ImageView iv_add) {

        Staff staff = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_staff, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        final EditText et_name = view.findViewById(R.id.et_name);
        final EditText et_part_name = view.findViewById(R.id.et_part_name);
        final EditText et_job = view.findViewById(R.id.et_job);
        final EditText et_salary = view.findViewById(R.id.et_salary);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_ok = view.findViewById(R.id.tv_ok);
        tv_ok.setText(tag);

        if (tag.equals("修改")) {
            staff = staffList.get(position);
            et_name.setText(staff.getSname());
            et_part_name.setText(staff.getDname());
            et_job.setText(staff.getSjob());
            et_salary.setText(String.valueOf(staff.getSalary()));
        }

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (tag.equals("新增")) {
                    rotateIV(iv_add);
                }
            }
        });

        final Staff finalStaff = staff;
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String job = et_job.getText().toString();
                String part_name = et_part_name.getText().toString();
                String salary = et_salary.getText().toString();
                if (tag.equals("修改")) {
                    changeDB(finalStaff, name, job, part_name, salary);
                    dialog.dismiss();
                } else {
                    if(addToDB(name, job, part_name, salary)){
                        dialog.dismiss();
                        rotateIV(iv_add);
                    }
                }

            }
        });
    }

    // 图标旋转
    private void rotateIV(ImageView iv_add) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_add,
                "rotation",45,0);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }

    // 向数据库修改职工信息
    private void changeDB(Staff finalStaff, String name, String job,
                          String part_name, String salary) {

        if (!checkInput(name, job, part_name, salary)) {
            return;
        }
        float salarys = Float.valueOf(salary);
        if (!checkIsChanged(finalStaff, name, job, part_name, salarys)) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("sname", name);
        values.put("dname", part_name);
        values.put("sjob", job);
        values.put("salary", salarys);
        int result = database.update("staff", values,
                "_id=?", new String[]{String.valueOf(finalStaff.getId())});
        if (result == 0) {
            Toast.makeText(context, "职工修改失败，请稍后重试...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "修改成功!", Toast.LENGTH_SHORT).show();
            finalStaff.setSname(name);
            finalStaff.setDname(part_name);
            finalStaff.setSalary(salarys);
            finalStaff.setSjob(job);
            notifyDataSetChanged();
        }

    }

    // 向数据库中添加数据职工
    private boolean addToDB(String name, String job, String part_name, String salary) {
        if (!checkInput(name, job, part_name, salary)) {
            return false;
        }
        float salarys = Float.valueOf(salary);
        ContentValues values = new ContentValues();
        values.put("sname", name);
        values.put("dname", part_name);
        values.put("sjob", job);
        values.put("salary", salarys);
        long result = database.insert("staff", null, values);

        if (result == 0) {
            Toast.makeText(context, "插入职工失败，请稍后重试...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "插入成功!", Toast.LENGTH_SHORT).show();
            Staff staff = new Staff(name,part_name,job,salarys);
            staffList.add(staff);
            notifyDataSetChanged();
        }
        return true;
    }

    // 输入格式检查
    private boolean checkInput(String name, String job, String part_name, String salary) {
        if (name.equals("")) {
            Toast.makeText(context, "姓名不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (job.equals("")) {
            Toast.makeText(context, "部门不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (part_name.equals("")) {
            Toast.makeText(context, "职务不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (salary.equals("")) {
            Toast.makeText(context, "工资不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                Float.valueOf(salary);
            } catch (NumberFormatException e) {
                // 格式转换错误
                Toast.makeText(context, "只能输入数值！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // 检查用户是否修改了信息
    private boolean checkIsChanged(Staff finalStaff, String name, String job,
                                   String part_name, float salary) {
        if (name.equals(finalStaff.getSname()) && job.equals(finalStaff.getSjob())
                && part_name.equals(finalStaff.getDname()) && salary == finalStaff.getSalary()) {
            Toast.makeText(context, "信息暂未修改！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
