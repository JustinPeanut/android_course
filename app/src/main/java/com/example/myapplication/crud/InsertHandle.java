package com.example.myapplication.crud;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Student_Database;

public class InsertHandle extends AppCompatActivity implements View.OnClickListener {

    Button insertButton = null;

    EditText user_info = null;
    EditText user_pwd = null;

    Spinner spinner = null;

    String user_pro = "学生";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insert_info);
        init();
    }

    private void init() {
        insertButton = findViewById(R.id.doInsert);
        insertButton.setOnClickListener(this);
        user_info = findViewById(R.id.iuserNumber);
        user_pwd = findViewById(R.id.ipassword);
        spinner = findViewById(R.id.insert_info);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user_pro = (InsertHandle.this.getResources().getStringArray(R.array.pro))[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.doInsert:
                String username = user_info.getText().toString();
                String password = user_pwd.getText().toString();
                Student_Database student_database = new Student_Database(this);
                SQLiteDatabase db = student_database.getWritableDatabase();
                student_database.addStu(db,username,password,user_pro);
                Intent it = new Intent();
                it.setClass(this,QueryAndDelete.class);
                startActivity(it);
                break;
        }
    }
}
