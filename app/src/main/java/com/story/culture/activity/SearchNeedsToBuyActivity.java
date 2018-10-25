package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.story.culture.R;
import com.story.culture.adapter.StudentInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchNeedsToBuyActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener {
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private StudentInfoAdapter adapter;
    public static void action2SearchNeedsToBuyActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SearchNeedsToBuyActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_needs_to_buy);
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
        ArrayList<StudentInfo> list = DbOperator.getInstance().getAllSutdent();
        adapter.refresh(list);
    }

    @Override
    public void onItemClick(View view, int position) {
        PersonCenterActivity.action2PersonCenterActivity(this, adapter.getItem(position).id);
    }

}
