package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.xutil.DateUtil;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.story.culture.R;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;
import com.story.culture.views.DatePicker;
import com.story.culture.views.ListViewPicker;
import com.story.culture.views.WheelView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteCourseInfoActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.start_time)//
            TextView start_time;
    @Bind(R.id.end_time)//
            TextView end_time;
    @Bind(R.id.type)//
            TextView type;
    @Bind(R.id.course_name)//
            TextView course_name;
    @Bind(R.id.course_time)//
            EditText course_time;
    @Bind(R.id.course_price)//
            EditText course_price;
    @Bind(R.id.course_activity_price)//
            EditText course_activity_price;
    @Bind(R.id.pay_price)//
            EditText pay_price;
    @Bind(R.id.teacher)//
            EditText teacher;
    @Bind(R.id.memo)//
            EditText memo;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private int yearIndex;
    private int monthIndex;
    private int dayIndex;
    private StudentInfo info;
    private InputMethodManager manager;

    public static void action2WriteCourseInfoActivity(Context context, StudentInfo info) {
        Intent intent = new Intent();
        intent.setClass(context, WriteCourseInfoActivity.class);
        intent.putExtra("item", info);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_course_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            info = (StudentInfo) intent.getSerializableExtra("item");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        getSupportActionBar().setTitle("添加课程");
        start_time.setOnClickListener(this);
        end_time.setOnClickListener(this);
        type.setOnClickListener(this);
        course_name.setOnClickListener(this);
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
                this.finish();
                return true;
            case R.id.save:
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String payPrice = pay_price.getText().toString();
        String courseActivityPrice = course_activity_price.getText().toString();
        String coursePrice = course_price.getText().toString();
        String courseTime = course_time.getText().toString();
        String courseName = course_name.getText().toString();
        String type1 = type.getText().toString();
        String endTime = end_time.getText().toString();
        String startTime = start_time.getText().toString();
        String memo1 = memo.getText().toString();
        String teacher1 = teacher.getText().toString();
        if (TextUtils.isEmpty(courseName)) {
            Toast.makeText(this, "请输入课程科目", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(payPrice)) {
            Toast.makeText(this, "请输入支付金额", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(teacher1)) {
            Toast.makeText(this, "请输入授课老师", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(type1)) {
            Toast.makeText(this, "请输入课程类别", Toast.LENGTH_SHORT).show();
        } else {
            if ("按次".equalsIgnoreCase(type1)) {
                if (TextUtils.isEmpty(courseTime)) {
                    Toast.makeText(this, "请输入课程课时", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(startTime)) {
                    Toast.makeText(this, "请输入课程开始时间", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(endTime)) {
                    Toast.makeText(this, "请输入课程结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.course_name = courseName;
            courseInfo.course_state = 1;
            courseInfo.course_class_hour = courseTime;
            courseInfo.available_class_hour = courseTime;
            courseInfo.course_price = payPrice;
            courseInfo.course_sale = courseActivityPrice;
            courseInfo.course_actual_price = coursePrice;
            courseInfo.memo = memo1;
            courseInfo.date = startTime;
            courseInfo.overdue_date = endTime;
            courseInfo.type = type1;
            courseInfo.phone_number = info.studentPhonenumber;
            courseInfo.student_id =  info.id;
            courseInfo.teacher =  teacher1;
            DbOperator.getInstance().insertCourseInfo(courseInfo);
        }
    }

    private void changeDate(final TextView textView) {
        DatePicker picker = new DatePicker(this, DatePicker.getYearArr(), yearIndex, monthIndex - 1, dayIndex - 1,
                new com.story.culture.views.DatePicker.OnDatePickListener() {
                    @Override
                    public void onDone(String year, String month, String day) {
                        Date select = DateUtil.stringtoDate(year + "-" + month + "-" + day, DateUtil.LONG_DATE_FORMAT);
                        Date now = new Date();
                        if (select.getTime() > now.getTime()) {
                            return;
                        }
                        yearIndex = DatePicker.getIndexOfTheYear(year);
                        monthIndex = Integer.valueOf(month);
                        dayIndex = Integer.valueOf(day);
                        textView.setText(year + "-" + month + "-" + day);
                    }

                    @Override
                    public void onCancle() {
                    }
                });
        picker.setTitle(getString(R.string.select_time));
        wvYear = picker.getWvYear();
        wvMonth = picker.getWvMonth();
        wvDay = picker.getWvDay();
        wvYear.setLabel(getString(R.string.label_year));
        wvMonth.setLabel(getString(R.string.label_month));
        wvDay.setLabel(getString(R.string.label_day));
        wvYear.setVisibleItems(5);
        wvMonth.setCyclic(true);
        wvMonth.setVisibleItems(5);
        wvDay.setCyclic(true);
        wvDay.setVisibleItems(5);
        picker.show();
    }

    private void selectType() {
        hideKeyboard();
        final ListViewPicker picker = new ListViewPicker(new String[]{"按次", "半年", "一年"}, this);
        picker.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.dismiss();
            }
        });
        picker.setOnSelectListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View listItemView, int position, long id) {
                type.setText(picker.getValue(position));
                picker.dismiss();
            }
        });
        picker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_time:
                changeDate(start_time);
                break;
            case R.id.end_time:
                changeDate(end_time);
                break;
            case R.id.type:
                selectType();
                break;
            case R.id.course_name:
                selectCourse();
                break;
        }
    }

    private void selectCourse() {
        hideKeyboard();
        final ListViewPicker picker = new ListViewPicker(new String[]{"吉他", "钢琴", "声乐", "古筝", "尤克里里", "非洲鼓", "架子鼓"}, this);
        picker.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.dismiss();
            }
        });
        picker.setOnSelectListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View listItemView, int position, long id) {
                course_name.setText(picker.getValue(position));
                picker.dismiss();
            }
        });
        picker.show();
    }

    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
