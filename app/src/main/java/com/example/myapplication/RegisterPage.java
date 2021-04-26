package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener , RadioGroup.OnCheckedChangeListener {
    public EditText username = null;
    public EditText password = null;
    public String profession = "学生";
    RadioGroup radioGroup;
    Button doRegisterButton = null;
    Button showInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        init();
    }

    private void init(){
        username = findViewById(R.id.ruserNumber);
        password = findViewById(R.id.rpassword);

        doRegisterButton = findViewById(R.id.doRegister);
        doRegisterButton.setOnClickListener(this);

        showInfo = findViewById(R.id.showInfo);
        showInfo.setOnClickListener(this);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.doRegister:
                Boolean flag = save_register(this, username.getText().toString(), password.getText().toString());
                if(flag){
                    Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.showInfo:
                Map<String, String> result = show_info(this);
                if(null != result || result.isEmpty()){
                    String name = result.get("username");
                    String pwd = result.get("password");
                    Toast.makeText(this,"用户名为：" + name + "\n" + "密码为：" + pwd+ "\n" + "职业为：" + profession,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // 注册用户
    public Boolean save_register(Context context,String username ,String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("profession",profession);
        editor.commit();
        return true;
    }

    // 查看注册信息
    public Map<String,String> show_info(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info",MODE_PRIVATE);
        String username = sharedPreferences.getString("username",null);
        String password = sharedPreferences.getString("password",null);
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("profession",profession);
        return map;
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
}
