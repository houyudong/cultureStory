package com.story.culture.database;

import java.io.Serializable;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class StudentInfo implements Serializable{
    public int id;
    public String studentPhonenumber;//学生电话
    public String studentName;//学生姓名
    public int sex;//性别
    public boolean attention;//是否重点关注
    public String source;//渠道 （招生渠道 活动，朋友推荐....）
    public String recommendPeople;//推荐人（招收负责人）
    public String qqNumber;//QQ号
    public String wechatNumber;//微信号
    public String birthday;//生日
    public String startDate;//入学时间（最早的日期）
    public String photo;//头像
    public String vedio;//学生视频

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentPhonenumber() {
        return studentPhonenumber;
    }

    public void setStudentPhonenumber(String studentPhonenumber) {
        this.studentPhonenumber = studentPhonenumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean getAttention() {
        return attention;
    }

    public void setAttention(boolean attention) {
        this.attention = attention;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRecommendPeople() {
        return recommendPeople;
    }

    public void setRecommendPeople(String recommendPeople) {
        this.recommendPeople = recommendPeople;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }
}
