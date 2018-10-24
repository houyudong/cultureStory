package com.story.culture.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.story.culture.R;
import com.story.culture.adapter.OnMultiClickListener;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.database.ConsumeClassTimeInfo;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;
import com.story.utils.Excel1Util;
import com.story.utils.ExcelUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jxl.write.WriteException;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.add)//新增
            Button add;
    @Bind(R.id.outside)//导出
            Button outside;
    @Bind(R.id.query)//查询
            Button query;
    @Bind(R.id.attention)//重点关注
            Button attention;
    @Bind(R.id.pay_price)//续费查询
            Button pay_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        add.setOnClickListener(this);
        outside.setOnClickListener(this);
        query.setOnClickListener(this);
        attention.setOnClickListener(this);
        pay_price.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                WriteInfoActivity.action2WriteInfoActivity(this, null, false);
                break;
            case R.id.outside:
                //学员列表导出
                List<StudentInfo> list = DbOperator.getInstance().getAllSutdent();
                String[] titles = new String[]{"id", "学生姓名", "性别", "联系方式", "QQ", "微信", "生日", "渠道", "推荐人", "入学时间", "重点关注"};// 设置列中文名
                int columnLength[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};// 设置列宽
                String fileds[] = new String[]{"id", "studentName", "sex", "studentPhonenumber", "qqNumber", "wechatNumber", "birthday", "source", "recommendPeople", "startDate", "attention"};// 设置列英文名
                Excel1Util.writeExcel("ceshi.xls", list, titles, columnLength, fileds);



                //学员课程消费记录导出
                List<ConsumeClassTimeInfo> consumeClassTimeInfos =  DbOperator.getInstance().getConsumeClassTimeInfoById(1);
                String[] consumeClassTimeInfoTitles = new String[]{"id", "学生名字", "学生电话", "课程名字", "消费学时", "日期", "时间", "授课老师", "备注"};// 设置列中文名
                int consumeClassTimeInfoColumnLength[] = {10, 10, 10, 10, 10, 10, 10, 10, 10};// 设置列宽
                String consumeClassTimeInfoFileds[] = new String[]{"id", "student_name", "phone_number", "course_name", "course_class_hour", "date", "time", "teacher",  "memo"};// 设置列英文名
                Excel1Util.writeExcel("ceshi.xls", consumeClassTimeInfos, consumeClassTimeInfoTitles, consumeClassTimeInfoColumnLength, consumeClassTimeInfoFileds);
                break;
            case R.id.pay_price:
                break;
            case R.id.attention:

                break;
            case R.id.query:
                SearchActivity.action2SearchActivity(this);
                break;
        }
    }


}
