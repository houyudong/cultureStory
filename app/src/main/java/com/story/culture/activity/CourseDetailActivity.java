package com.story.culture.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.github.gcacace.signaturepad.utils.SvgBuilder;
import com.github.gcacace.signaturepad.utils.SvgPathBuilder;
import com.story.culture.R;
import com.story.culture.adapter.UseCourseInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.database.ConsumeClassTimeInfo;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.DbOperator;
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
    LinearLayout sign;    @Bind(R.id.save)
    RoundTextView save;

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
    ImageView finish;    @Bind(R.id.sign_img)
    ImageView sign_img;
    private StudentInfo mStudentInfo;
    private CourseInfo mCourseInfo;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String path;

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
        getSupportActionBar().setTitle(mCourseInfo.course_name);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        sign.setOnClickListener(this);
        save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign:
                SignaturActivity.action2SignaturActivityForResult(this,mStudentInfo.studentName+mCourseInfo.course_name);
                break;
                case R.id.save:
                    ConsumeClassTimeInfo info = new ConsumeClassTimeInfo();
                    info.course_class_hour = class_time.getText().toString();
                    info.course_name = mCourseInfo.course_name;
                    info.date = date.getText().toString();
                    info.time = date.getText().toString();
                    info.memo =memo.getText().toString() ;
                    info.phone_number = mStudentInfo.studentPhonenumber;
                    info.photo = path;
                    info.student_name = mStudentInfo.studentName;
                    info.teacher = mCourseInfo.teacher;
                    info.student_id = Integer.valueOf(mStudentInfo.id);
                    info.course_id =  Integer.valueOf(mCourseInfo.id);
                    DbOperator.getInstance().insertConsumeClassTimeInfo(info);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.save:
                CourseUsedDetailActivity.action2CourseUsedDetailActivity(this,mCourseInfo.id,mStudentInfo.studentName+mCourseInfo.course_name);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        path = data.getStringExtra("path");
        sign_img.setBackground(new BitmapDrawable(path));
    }
}
