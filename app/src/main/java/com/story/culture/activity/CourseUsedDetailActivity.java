package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.story.culture.R;
import com.story.culture.adapter.StudentInfoAdapter;
import com.story.culture.adapter.UseCourseInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.database.ConsumeClassTimeInfo;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;
import com.story.utils.Excel1Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Description: 重点关注用户查询
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/12/12 0012 上午 9:52
 */
public class CourseUsedDetailActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private UseCourseInfoAdapter adapter;
    private int mId;
    private String mName;
    private ArrayList<ConsumeClassTimeInfo> consumeClassTimeInfos;

    public static void action2CourseUsedDetailActivity(Context context, int id, String name) {
        Intent intent = new Intent();
        intent.setClass(context, CourseUsedDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_used_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, 0xff303030, false);
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getIntExtra("id", 0);
            mName = intent.getStringExtra("name");
        }
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableAutoLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new UseCourseInfoAdapter(this);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("课程记录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        consumeClassTimeInfos = DbOperator.getInstance().getConsumeClassTimeInfoById(mId);
        adapter.refresh(consumeClassTimeInfos);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                break;
                case R.id.export:
                    String[] courseInfoTitles = new String[]{"id", "学生名字", "电话号码", "课程名称", "消费学时", "时间","签名文件名称", "日期", "授课老师"};// 设置列中文名
                    int courseInfoColumnLength[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};// 设置列宽
                    String courseInfoFileds[] = new String[]{"id", "student_name", "phone_number", "course_name", "course_class_hour", "time", "photo", "date", "teacher"};// 设置列英文名
                    Excel1Util.writeExcel(mName+"的课程表.xls", consumeClassTimeInfos, courseInfoTitles, courseInfoColumnLength, courseInfoFileds);

                    break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }
    @Override
    public void onItemClick(View view, int position) {
    }
}
