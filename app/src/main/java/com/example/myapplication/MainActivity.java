package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


// 实现View.OnClickListener（OnCheckedChangeListener）才有onClick（OnCheckedChangeListener）方法
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{

    // 定义常量
    public static EditText userNumber;
    public static EditText password;
    // 默认为学生
    public static String profession = "学生";
    // 登陆按钮
    Button loginButton;
    // 按钮组
    RadioGroup radioGroup;

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void onClick(View view){
        // 根据单机按钮的id来判断是点击的哪一个按钮
        switch (view.getId()){
            case R.id.login:

                String username = userNumber.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                Intent it = new Intent();

                if(username.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(this,"请输入用户名和密码",Toast.LENGTH_SHORT).show();
                    return;
                    }else{
                    it.putExtra("userNumber", username);
                    it.putExtra("password",pwd);
                    it.putExtra("profession",profession);
                    if("学生".equals(profession)){
                        it.setClass(MainActivity.this,LoginSuccess.class);
                        startActivity(it);
                    }else{
                        it.setClass(this,StudentService.class);
                        startActivity(it);
                    }
                }



                break;
            case R.id.register:
                Intent it2 = new Intent();
                it2.setClass(MainActivity.this,RegisterPage.class);
                startActivity(it2);
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 判断哪个单选组被点击了
        switch(group.getId()){
            case R.id.radioGroup:
                profession = checkedId == R.id.student?"学生":"老师";
                break;
        }

    }

    public void init(){
        // 获取按钮，设置单机事件
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(this);

        // 设置单选按钮切换的事件，判断是学生还是老师
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(this);

        // 初始化用户名 ，密码
        userNumber = findViewById(R.id.userNumber);
        password = findViewById(R.id.password);

    }
}
