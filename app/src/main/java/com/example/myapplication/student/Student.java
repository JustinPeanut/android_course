package com.example.myapplication.student;

public class Student {
    public int id;
    public String username;
    public String password;
    public String pro;

    public Student(){}

    public Student(String username, String password, String pro) {
        this.username = username;
        this.password = password;
        this.pro = pro;
    }

    public Student(Integer id, String username, String password, String pro) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pro = pro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pro='" + pro + '\'' +
                '}';
    }
}
