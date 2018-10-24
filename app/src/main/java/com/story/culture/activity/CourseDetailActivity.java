package com.story.culture.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.story.culture.R;
import com.story.culture.adapter.UseCourseInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.StudentInfo;
import com.story.culture.views.RoundAngleImageView;
import com.story.culture.views.middleScrollView.ContentRecyclerView;
import com.story.culture.views.middleScrollView.ScrollLayout;
import com.story.utils.DensityUtil;
import com.story.utils.ScreenUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.recyclerView)
    ContentRecyclerView recyclerView;
    @Bind(R.id.scroll_down_layout)
    ScrollLayout mScrollLayout;
    @Bind(R.id.card)
    FrameLayout card;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bg)
    RoundAngleImageView bg;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.phone_num)
    EditText phone_num;
    @Bind(R.id.class_time)
    EditText class_time;
    @Bind(R.id.memo)
    EditText memo;
    @Bind(R.id.sign)
    RoundTextView sign;

    @Bind(R.id.course_name)
    TextView course_name;
    @Bind(R.id.teacher_name)
    TextView teacher_name;
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.start_time)
    TextView start_time;
    @Bind(R.id.end_time)
    TextView end_time;
    @Bind(R.id.course_memo)
    TextView course_memo;
    @Bind(R.id.course_sale)
    TextView course_sale;
    @Bind(R.id.course_actual_price)
    TextView course_actual_price;
    @Bind(R.id.course_price)
    TextView course_price;
    @Bind(R.id.finish)
    ImageView finish;

    private boolean isScroll;
    private UseCourseInfoAdapter adapter;
    private StudentInfo mStudentInfo;
    private CourseInfo mCourseInfo;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void initData() {
        String strBeginDate = dateTimeformat.format(new Date());
        date.setText(strBeginDate);
        phone_num.setText(mStudentInfo.studentPhonenumber);
        name.setText(mStudentInfo.studentName);
        course_name.setText(mCourseInfo.course_name);
        teacher_name.setText("授课老师：" + mCourseInfo.teacher);

        start_time.setText("报名时间：" + mCourseInfo.date);
        end_time.setText("结束时间：" + mCourseInfo.overdue_date);
        course_memo.setText("备忘：" + mCourseInfo.memo);
        course_sale.setText("课程优惠价格：" + mCourseInfo.course_sale);
        course_actual_price.setText("课程金额实际支付价格：" + mCourseInfo.course_actual_price);
        course_price.setText("课程金额原价：" + mCourseInfo.course_price);
        if (mCourseInfo.type.equalsIgnoreCase("按次")) {
            type.setText(mCourseInfo.available_class_hour + "/" + mCourseInfo.course_class_hour);
        } else {
            type.setText(mCourseInfo.type);
        }
        if (mCourseInfo.course_state == 1) {
            finish.setVisibility(View.INVISIBLE);
        } else {
            finish.setVisibility(View.VISIBLE);
        }
        bg.setBackgroundResource(R.drawable.drawable_guitar_bg);
    }

    public static void action2CourseDetailActivity(PersonCenterActivity personCenterActivity, StudentInfo info, CourseInfo courseInfo) {
        Intent intent = new Intent(personCenterActivity, CourseDetailActivity.class);
        intent.putExtra("info", info);
        intent.putExtra("courseInfo", courseInfo);
        personCenterActivity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mStudentInfo = (StudentInfo) intent.getSerializableExtra("info");
            mCourseInfo = (CourseInfo) intent.getSerializableExtra("courseInfo");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人资料");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
        }

        @Override
        public void onScrollFinished(final ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                isScroll = true;
            } else if (currentStatus.equals(ScrollLayout.Status.OPENED)) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                isScroll = false;
            } else {
                isScroll = false;
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    private void initView() {
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (isScroll) {
//                    return false;
//                }
//                return true;
//            }
//        });
        sign.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new UseCourseInfoAdapter(this);
        recyclerView.setAdapter(adapter);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float mScreenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        float mScreenHeight = dm.heightPixels;// 获取屏幕分辨率高度
        float ratio = mScreenHeight / mScreenWidth;
        if (ratio > 1.9) {//全面屏手机
            mScrollLayout.setMaxOffset((int) (ScreenUtils.getScreenHeight(this) * 0.74));
            if (mScreenHeight > 2240) {
                mScrollLayout.setExitOffset((int) (ScreenUtils.getScreenHeight(this) * 0.12));
            }
            if (mScreenHeight < 2180) {
                mScrollLayout.setExitOffset((int) (ScreenUtils.getScreenHeight(this) * 0.08));
            }
        } else {
            mScrollLayout.setMaxOffset((int) (ScreenUtils.getScreenHeight(this) * 0.7));
            mScrollLayout.setExitOffset(DensityUtil.dip2px(this, 100));
        }

        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
//        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToOpen();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign:
                SignaturActivity.action2SignaturActivity(this);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_writeinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.save:
//                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
