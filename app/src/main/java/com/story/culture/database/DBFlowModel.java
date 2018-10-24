package com.story.culture.database;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/25
 */
public class DBFlowModel {
    public int id;
    public String student_name;//学生姓名
    public String student_phonenumber;//学生电话
    public String sex;//性别
    public String course_name;//课程名字
    public String course_state;//课程状态
    public String course_class_hour;//课程学时
    public String course_price;//课程金额原价
    public String course_sale;//课程金额优惠价格
    public String course_actual_price;//课程金额实际支付价格
    public String memo;//备忘录
    public String date;//报名时间
    public String overdue_date;//结束时间（结束前15天提醒，按次剩余3次提醒）
    public String type;//类型 按次、包年、半年
    public String source;//渠道 （招生渠道 活动，朋友推荐....）
    public String recommend_people;//推荐人（招收负责人）
}
