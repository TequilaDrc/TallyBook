package com.tequila.tallybook.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.tequila.tallybook.SharedApplication.context;

/**
 * Created by tequila on 2017/4/13.
 */

public class CustomDBHelper extends SQLiteOpenHelper {

    private static final int version = 1;				// 数据库版本号，每次修改数据库是需要提升版本号
    public static final String name = "tequila.db";		// 数据库的名称

    public CustomDBHelper(Context context) {
        super(context, name, null, version);
    }

    public CustomDBHelper() {
        super(context, name, null, version);
    }

    public static final CustomDBHelper instance = new CustomDBHelper();

    private void execSqlWithDatabase(SQLiteDatabase db) {
        ArrayList<String> sqlList = new ArrayList<String>();

        // 从init.sql里取出创建语句
        try {
            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/"+"init.sql");
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer(512);
            String line = bufferedReader.readLine();

            while (line != null) {
                line = line.trim();
                if (line.endsWith(";")) {
                    if (line.length() > 0) {
                        buffer.append(line.substring(0, line.length() - 1));
                    }
                    sqlList.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                } else {
                    buffer.append(line);
                    buffer.append("\n");
                }

                // read next line
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            isr.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // exec sql
        db.beginTransaction();
        for (String sql : sqlList) {
            db.execSQL(sql);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    // 创建数据库的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        execSqlWithDatabase(db);
    }

    // 更新数据库的方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("CustomDBHelper", "Update the database program");
    }
}
