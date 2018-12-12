package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.story.culture.R;
import com.story.culture.adapter.StudentInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @Description: 重点关注用户查询
 * @Copyright  2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/12/12 0012 上午 9:52
 */
public class SearchImportantAttentionActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener {
    private int type = 1;//1是姓名2是电话3是课程，默认是姓名
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private StudentInfoAdapter adapter;
    public static void action2SearchImportantAttentionActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SearchImportantAttentionActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, 0xff303030, false);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableAutoLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new StudentInfoAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("学员查询");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<StudentInfo> list = DbOperator.getInstance().getSutdentByAttention();
        adapter.refresh(list);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(View view, int position) {
        PersonCenterActivity.action2PersonCenterActivity(this, adapter.getItem(position).id);
    }
}
