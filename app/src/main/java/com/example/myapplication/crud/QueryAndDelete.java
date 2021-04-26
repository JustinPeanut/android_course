package com.example.myapplication.crud;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Student_Database;
import com.example.myapplication.student.Student;

import java.util.List;

public class QueryAndDelete extends AppCompatActivity {
    SQLiteDatabase db = null;

    ListView stu_list;
    List<Student> list;

    String []student;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_delete);
        init();
    }

    public void init(){
        // 创建数据库
        Student_Database student_database = new Student_Database(this);
        db = student_database.getReadableDatabase();
        // 从数据库中加入数据
        stu_list = findViewById(R.id.studentMes);
        list = student_database.query(db);
//         将获取到的数据，加入到学生数组中
        student = new String[list.size()];
        for(int i = 0 ; i < list.size() ; i++){
            student[i] = list.get(i).getUsername() + " " + list.get(i).getPassword() + " " + list.get(i).getPro();
        }
        if(list == null){
        }else{
            // 将信息显示在listview中
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(QueryAndDelete.this,android.R.layout.simple_list_item_1,student);
            stu_list.setAdapter(adapter);
//             为ListView每个元素添加单击事件
            stu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final int id = list.get(i).getId();
                    // 弹出对话框
                    new AlertDialog.Builder(QueryAndDelete.this).setTitle("系统提示")
                            // 设置提示内容
                            .setMessage("确定删除该条数据吗？")
                            // 添加确定按钮
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Student_Database sd = new Student_Database(QueryAndDelete.this);
                                    SQLiteDatabase sqLiteDatabase = sd.getWritableDatabase();
                                    sd.deleteStu(sqLiteDatabase,id);
                                    refresh();
                                    Toast.makeText(QueryAndDelete.this,"删除成功！",Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            });
        }

    }

    // 刷新页面方法
    private void refresh() {
        finish();
        Intent intent = new Intent(this,QueryAndDelete.class);
        startActivity(intent);
    }
}
