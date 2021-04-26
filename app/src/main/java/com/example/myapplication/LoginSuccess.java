package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.student.Student;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class LoginSuccess extends AppCompatActivity implements View.OnClickListener {

    Button saveButton;
    Button displayButton;
    Button serialButton;
    Button antiXmlButton;
    public static String userNumber;
    public static String password;
    public static String profession;
    public static String mes = "";
    public Student student = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        // 获取传递进来的数据，在页面上显示
        Intent intent = getIntent();

        Bundle info = intent.getExtras();

        userNumber = info.getString("userNumber");

        password = info.getString("password");

        profession = info.getString("profession");

        TextView view = findViewById(R.id.text1);

        view.setText("学号:"+userNumber+"\n"+"密码:"+password+"\n"+"职业:"+profession);



        // 为按钮设置单机事件
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(this);

        displayButton = findViewById(R.id.display);

        displayButton.setOnClickListener(this);

        serialButton = findViewById(R.id.serial);

        serialButton.setOnClickListener(this);

        antiXmlButton = findViewById(R.id.antiXml);

        antiXmlButton.setOnClickListener(this);



//        String environment = Environment.getExternalStorageState();
//
//        if(Environment.MEDIA_MOUNTED.equals(environment)){
//
//
//            File sd_path = Environment.getExternalStorageDirectory();
//            File file = new File(sd_path,"test.txt");
//            FileOutputStream fi_out;
//            String s = "网络工程181 邓峰";
//            try{
//                fi_out = new FileOutputStream(file);
//                fi_out.write(s.getBytes());
//                fi_out.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//        }


    }

    @Override
    public void onClick(View view) {

        // 按钮的分类点击
        switch (view.getId()){

            case R.id.save:
                Boolean flag = saveInfo();
                if(flag){
                    Toast.makeText(this,"用户信息已保存",Toast.LENGTH_SHORT).show();
                };
                break;
            case R.id.display:
                displayInfo();
                break;
            case R.id.serial:
                Student student = new Student(userNumber,password,profession);
                Boolean flag1 = xmlSerial(student);
                if(flag1){
                    Toast.makeText(this,"序列化成功！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"序列化失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.antiXml:
                InputStream inputStream = null;
                try{
                    inputStream =  this.getClass().getClassLoader().getResourceAsStream("assets/student.xml");
                    List<Student> lists = getStudent(inputStream);
                    Toast.makeText(this,lists.toString(),Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }
    }

    // 反序列化文件
    public List<Student> getStudent(InputStream inputStream) throws Exception{
        XmlPullParser pullParser = Xml.newPullParser();
        pullParser.setInput(inputStream,"UTF-8");
        List<Student> studentList = null;
        int event = pullParser.getEventType();
        while(XmlPullParser.END_DOCUMENT != event){
            switch (event){

                case XmlPullParser.START_DOCUMENT:
                    studentList = new ArrayList<>();
                    break;

                case XmlPullParser.START_TAG:
                    if("student".equals(pullParser.getName())){
                        int id = new Integer(pullParser.getAttributeValue(0));
                        student = new Student();
                        student.setId(id);
                    }
                    if("username".equals(pullParser.getName())){
                        student.setUsername(pullParser.nextText());
                    }
                    if("password".equals(pullParser.getName())){
                        student.setPassword(pullParser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("student".equals(pullParser.getName())){
                        studentList.add(student);
                        student = null;
                    }
                    break;
            }
            event = pullParser.next();
        }
        return studentList;
    }

    // 序列化文件的方法
    public Boolean xmlSerial(Student student){
        XmlSerializer serializer = null;
        FileOutputStream fi_out = null;
        File file = null;

        try{
             serializer = Xml.newSerializer();
             file = new File(Environment.getExternalStorageDirectory(),"student_mes.xml");
             fi_out = new FileOutputStream(file);
             serializer.setOutput(fi_out,"UTF-8");
             serializer.startDocument("UTF-8",true);
             serializer.startTag(null,"students");
             serializer.startTag(null,"student");
             // 写入对象属性
            serializer.startTag(null,"username");
            serializer.text(userNumber);
            serializer.endTag(null,"username");
            serializer.startTag(null,"password");
            serializer.text(password);
            serializer.endTag(null,"password");
            serializer.startTag(null,"profession");
            serializer.text(profession);
            serializer.endTag(null,"profession");
            serializer.endTag(null,"student");
            serializer.endTag(null,"students");
            serializer.flush();
            fi_out.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 保存信息的方法
    public  Boolean saveInfo(){
        String user_info = "用户名为：" + userNumber + "\n" + "密码为：" + password;
        FileOutputStream fi_out;
        try{
            fi_out = openFileOutput("user_info.txt",MODE_PRIVATE);
            fi_out.write(user_info.getBytes());
            fi_out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 查看信息的方法
    public void displayInfo(){
        FileInputStream fi_in;
        byte[] buffer = null;
        try{
           fi_in = openFileInput("user_info.txt");
           buffer = new byte[fi_in.available()];
           fi_in.read(buffer);
           mes = new String(buffer);
           fi_in.close();

        }catch (Exception e){
            e.printStackTrace();

        }
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }
}

