package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.crud.InsertHandle;
import com.example.myapplication.crud.QueryAndDelete;

public class StudentService extends AppCompatActivity implements View.OnClickListener {


    // 常量
    String userNumber = null;
    String profession = null;
    String password = null;

    // 按钮
    Button queryButton = null;
    Button insertButton = null;
    Button updateButton = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_db);
        init(savedInstanceState);
    }


    @Override
    public void onClick(View view) {
        Intent it = new Intent();
        switch (view.getId()){
            case R.id.query:
                it.setClass(this, QueryAndDelete.class);
                startActivity(it);
                break;
            case R.id.insert:
                it.setClass(this, InsertHandle.class);
                startActivity(it);
                break;
            case R.id.update:
                break;
        }
    }

    // 初始化该Activity
    public void init(Bundle savedInstanceState){

        queryButton = findViewById(R.id.query);
        queryButton.setOnClickListener(this);

        insertButton = findViewById(R.id.insert);
        insertButton.setOnClickListener(this);

        updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(this);

        // 获取传递进来的数据，在页面上显示
        Intent intent = getIntent();

        Bundle info = intent.getExtras();

        userNumber = info.getString("userNumber");

        password = info.getString("password");

        profession = info.getString("profession");

        TextView view = findViewById(R.id.text1);

        view.setText("学号:"+userNumber+"\n"+"密码:"+password+"\n"+"职业:"+profession);

    }

}
