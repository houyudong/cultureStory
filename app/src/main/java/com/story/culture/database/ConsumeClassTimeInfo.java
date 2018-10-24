package com.story.culture.database;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class ConsumeClassTimeInfo  {

    public int id;
    public int student_id;
    public String student_name;//学生名字
    public String phone_number;//学生电话
    public String course_name;//课程名字
    public String course_class_hour;//消费学时
    public String photo;//图片
    public String date;//日期
    public String time;//时间
    public String teacher;//授课老师
    public String memo;//备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_class_hour() {
        return course_class_hour;
    }

    public void setCourse_class_hour(String course_class_hour) {
        this.course_class_hour = course_class_hour;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
