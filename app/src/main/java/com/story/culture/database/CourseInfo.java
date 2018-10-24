package com.story.culture.database;

import java.io.Serializable;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class CourseInfo  implements Serializable{

    public int id;
    public int student_id;
    public String course_name;//课程名字
    public int course_state;//课程状态  0已结束 1正常
    public String course_class_hour;//课程总学时
    public String available_class_hour;//可用课程学时
    public String course_price;//课程金额原价
    public String course_sale;//课程金额优惠价格
    public String course_actual_price;//课程金额实际支付价格
    public String memo;//备忘录
    public String date;//报名时间
    public String overdue_date;//结束时间（结束前15天提醒，按次剩余3次提醒）
    public String type;//类型 按次、包年、半年
    public String teacher;//授课老师
    public String phone_number;//学生电话

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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_state() {
        return course_state;
    }

    public void setCourse_state(int course_state) {
        this.course_state = course_state;
    }

    public String getCourse_class_hour() {
        return course_class_hour;
    }

    public void setCourse_class_hour(String course_class_hour) {
        this.course_class_hour = course_class_hour;
    }

    public String getAvailable_class_hour() {
        return available_class_hour;
    }

    public void setAvailable_class_hour(String available_class_hour) {
        this.available_class_hour = available_class_hour;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getCourse_sale() {
        return course_sale;
    }

    public void setCourse_sale(String course_sale) {
        this.course_sale = course_sale;
    }

    public String getCourse_actual_price() {
        return course_actual_price;
    }

    public void setCourse_actual_price(String course_actual_price) {
        this.course_actual_price = course_actual_price;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverdue_date() {
        return overdue_date;
    }

    public void setOverdue_date(String overdue_date) {
        this.overdue_date = overdue_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
