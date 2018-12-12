package com.story.culture.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.xutil.DateUtil;
import android.xutil.task.ForeTask;

import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.story.culture.R;
import com.story.culture.StoryConstants;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;
import com.story.culture.views.DatePicker;
import com.story.culture.views.ListViewPicker;
import com.story.culture.views.WheelView;
import com.story.utils.UIUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Description:学员信息录入
 * @Copyright  2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/12/12 0012 上午 10:09
 */
public class WriteInfoActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private int yearIndex;
    private int monthIndex;
    private int dayIndex;
    private File tempFile;
    private File tempFileCrop;
    private final int SELECT_PIC_BY_TACK_PHOTO = 1111;
    private final int SELECT_PIC_BY_PICK_PHOTO = 2222;
    private final int PHOTO_REQUEST_CUT = 3333;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.brith_day)//生日
            TextView brith_day;
    @Bind(R.id.time)//报名时间
            TextView time;
    @Bind(R.id.name)//学生姓名
            EditText name;
    @Bind(R.id.phone_num)//电话
            EditText phone_num;
    @Bind(R.id.recommend)//推荐人
            EditText recommend;
    @Bind(R.id.qq_number)//QQ号码
            EditText qq_number;
    @Bind(R.id.wechat_number)//微信号
            EditText wechat_number;
    @Bind(R.id.sourse)//来源途径
            EditText sourse;
    @Bind(R.id.user_header)//用户头像
            CircleImageView user_header;
    @Bind(R.id.rl_info_head)//用户头像
            RelativeLayout rl_info_head;
    @Bind(R.id.sex_select)
    RadioGroup mRadioGroup1;
    @Bind(R.id.girl)
    RadioButton mRadioGril;
    @Bind(R.id.man)
    RadioButton mRadioBoy;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private int mSex = 0;// 男 2 女 1
    private StudentInfo info;
    private boolean isUpdate;
    private boolean isSave = false;

    public static void action2WriteInfoActivity(Activity context, StudentInfo info, boolean isUpdate) {
        Intent intent = new Intent();
        intent.setClass(context, WriteInfoActivity.class);
        intent.putExtra("item", info);
        intent.putExtra("isUpdate", isUpdate);
            context.startActivity(intent);
    }

    private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == mRadioGril.getId()) {
                mSex = 1;
            } else if (checkedId == mRadioBoy.getId()) {
                mSex = 2;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_info);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, 0xff303030, false);
        Intent intent = getIntent();
        if (intent != null) {
            info = (StudentInfo) intent.getSerializableExtra("item");
            isUpdate = intent.getBooleanExtra("isUpdate", false);
        }
        rl_info_head.setOnClickListener(this);
        user_header.setOnClickListener(this);
        time.setOnClickListener(this);
        brith_day.setOnClickListener(this);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableAutoLoadMore(false);
        mRadioGroup1.setOnCheckedChangeListener(mChangeRadio);
        String photoName = getPhotoFileName();
        tempFile = new File(StoryConstants.FILE_PATH, photoName);
        tempFileCrop = new File(StoryConstants.FILE_PATH, photoName + "2");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("个人资料");
        if (isUpdate) {
            setData();
        }
    }

    private void setData() {
        name.setText(info.studentName);
        phone_num.setText(info.studentPhonenumber);
        wechat_number.setText(info.wechatNumber);
        qq_number.setText(info.qqNumber);
        sourse.setText(info.source);
        recommend.setText(info.recommendPeople);
        brith_day.setText(info.birthday);
        time.setText(info.startDate);
        if (info.attention) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
        if (info.sex == 1) {
            mRadioGril.setChecked(true);
            mRadioBoy.setChecked(false);
        } else {
            mRadioGril.setChecked(false);
            mRadioBoy.setChecked(true);
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
                save();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        String name1 = name.getText().toString();
        String phone_num1 = phone_num.getText().toString();
        String qq_number1 = qq_number.getText().toString();
        String wechat_number1 = wechat_number.getText().toString();
        String sourse1 = sourse.getText().toString();
        if (TextUtils.isEmpty(name1)) {
            Toast.makeText(this, "请输入学生姓名", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone_num1)) {
            Toast.makeText(this, "请输入学生电话", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(sourse1)) {
            Toast.makeText(this, "请输入招生来源", Toast.LENGTH_SHORT).show();
        } else if (mSex != 1 && mSex != 2) {
            Toast.makeText(this, "请确认性别", Toast.LENGTH_SHORT).show();
        } else {
            if (isUpdate) {
                StudentInfo userData = DbOperator.getInstance().getSutdentById(info.id);
                if (userData != null) {
                    userData.studentName = name1;
                    userData.sex = mSex;
                    userData.birthday = brith_day.getText().toString();
                    userData.studentPhonenumber = phone_num1;
                    userData.attention = checkbox.isChecked();
                    userData.source = sourse1;
                    userData.wechatNumber = wechat_number1;
                    userData.qqNumber = qq_number1;
                    userData.startDate = time.getText().toString();
                    userData.recommendPeople = recommend.getText().toString();
                    DbOperator.getInstance().updateStudentInfo(userData);
                } else {
                    Toast.makeText(this, "用户信息异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                StudentInfo userData = new StudentInfo();
                userData.studentName = name1;
                userData.sex = mSex;
                userData.studentPhonenumber = phone_num1;
                userData.attention = checkbox.isChecked();
                userData.source = sourse1;
                userData.birthday = brith_day.getText().toString();
                userData.wechatNumber = wechat_number1;
                userData.qqNumber = qq_number1;
                userData.startDate = time.getText().toString();
                userData.recommendPeople = recommend.getText().toString();
                DbOperator.getInstance().insertStudentInfo(userData);
                StudentInfo info =  DbOperator.getInstance().getSutdentByPhone(userData.studentPhonenumber).get(0);
                PersonCenterActivity.action2PersonCenterActivity(this, info.id);
                finish();
//                isSave = true;
            }
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
//                            Toast.makeText(WriteInfoActivity.this,
//                                    R.string.not_record_future, Toast.LENGTH_SHORT).show();
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

    private void selectUserHead() {
        final ListViewPicker picker = new ListViewPicker(new String[]{"拍一张", "从图库选取"}, this);
        picker.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.dismiss();
            }
        });
        picker.setOnSelectListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View listItemView, int position, long id) {
                if (position == 0) {
                    UIUtils.takePictures(WriteInfoActivity.this, tempFile, SELECT_PIC_BY_TACK_PHOTO);
                } else {
                    pickPhoto();
                }
                picker.dismiss();
            }
        });
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PIC_BY_TACK_PHOTO:// 当选择拍照时调用
                startPhotoZoom(Uri.fromFile(tempFile));
                break;

            case SELECT_PIC_BY_PICK_PHOTO:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;

            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("output", Uri.fromFile(tempFileCrop));
        intent.putExtra("return-data", false);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {

        new ForeTask(true) {
            Bitmap photo = BitmapFactory.decodeFile(tempFileCrop.getPath());

            @Override
            public void onFore() {
                user_header.setImageBitmap(photo);
            }
        };


    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_info_head:
            case R.id.user_header:
                selectUserHead();

                break;
            case R.id.time:
                changeDate(time);
                break;
            case R.id.brith_day:
                changeDate(brith_day);
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
//        if (isSave) {
//            finish();
//            WriteCourseInfoActivity.action2WriteCourseInfoActivity(this, phone_num.getText().toString());
//        } else {
//            Toast.makeText(this, "请先保存学生信息", Toast.LENGTH_SHORT).show();
//        }

    }
}
