package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Student_Database extends SQLiteOpenHelper {
    public Student_Database(Context context){
        super(context,"stu_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " +
                "stu(id integer primary key autoincrement," +
                "username varchar(20),"+
                "password varchar(20),profession varchar(20))";
                sqLiteDatabase.execSQL(sql);

    }

    // 数据库版本更新时才调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // 添加学生
    public void addStu(SQLiteDatabase sqLiteDatabase,String username,String password,String profession){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        values.put("profession",profession);
        sqLiteDatabase.insert("stu",null,values);
        sqLiteDatabase.close();
    }

    // 删除学生
    public void deleteStu(SQLiteDatabase sqLiteDatabase,int id){
        sqLiteDatabase.delete("stu","id=?",new String[]{id+""});
        sqLiteDatabase.close();
    }

    // 更新学生
    public void updateStu(SQLiteDatabase sqLiteDatabase,int id,String username,String password){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        sqLiteDatabase.update("stu",values,"id=?",new String[]{id+""});
        sqLiteDatabase.close();
    }

    // 查询学生
    public List<Student> query(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query("stu",null,null,null,null,null,"id ASC");
        List<Student> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String profession = cursor.getString(3);
            list.add(new Student(id,username,password,profession));
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }



}
