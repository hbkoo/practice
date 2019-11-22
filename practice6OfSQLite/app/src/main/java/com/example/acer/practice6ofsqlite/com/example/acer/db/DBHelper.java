package com.example.acer.practice6ofsqlite.com.example.acer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 19216 on 2019/1/13.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // 自动增加
        String create_table_sql = "create table staff(_id integer primary key autoincrement," +
                "sname text," +
                "dname text," +
                "sjob text," +
                "salary float)";

        sqLiteDatabase.execSQL(create_table_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
