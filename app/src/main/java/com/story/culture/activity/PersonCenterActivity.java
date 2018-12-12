package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.flyco.roundview.RoundTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.story.culture.R;
import com.story.culture.adapter.CourseInfoAdapter;
import com.story.culture.basecomon.BaseActivity;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;
import com.story.culture.views.ListViewPicker;
import com.story.utils.Excel1Util;
import com.story.utils.StatusBarUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonCenterActivity extends BaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener, View.OnClickListener {

    private int mOffset = 0;
    private int mScrollY = 0;
    @Bind(R.id.name)//姓名
            TextView name;
    @Bind(R.id.title)//姓名
            TextView title;
    @Bind(R.id.title_img)//头像
            CircleImageView title_img;
    @Bind(R.id.img)//头像
            CircleImageView img;
    //    @Bind(R.id.parallax)//背景
//            ImageView parallax;
    @Bind(R.id.phone_num)//电话
            TextView phone_num;
    @Bind(R.id.study_course)//学习课程科目
            TextView study_course;
    @Bind(R.id.course_time)//剩余课时
            TextView course_time;
    @Bind(R.id.stop) //快退
            RoundTextView stop;
    @Bind(R.id.pre) //快进
            RoundTextView pre;
    @Bind(R.id.play) //播放
            RoundTextView play;
    @Bind(R.id.recyclerView) //列表
            RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @Bind(R.id.videoView)
    VideoView videoView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.scrollView)
    NestedScrollView scrollView;
    @Bind(R.id.buttonBarLayout)
    ButtonBarLayout buttonBar;
    private CourseInfoAdapter adapter;
    private StudentInfo info;
    private ArrayList<CourseInfo> list;
    private int mId;

    public static void action2PersonCenterActivity(Context context, int id) {
        Intent intent = new Intent();
        intent.setClass(context, PersonCenterActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        info = DbOperator.getInstance().getSutdentById(mId);
        list = DbOperator.getInstance().getCourseByStudentId(mId);
        name.setText(info.studentName);
        title.setText(info.studentName);
        phone_num.setText(info.studentPhonenumber);
        adapter.refresh(list);
        if(!TextUtils.isEmpty(info.vedio)){
            videoView.setVideoPath(info.vedio);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getIntExtra("id", 0);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setFocusable(false);
        adapter = new CourseInfoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(this);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        pre.setOnClickListener(this);
        setSupportActionBar(toolbar);

        img.setImageResource(R.drawable.head1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setEnableAutoLoadMore(false);
        smartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                videoView.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                videoView.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(170);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    videoView.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.upload:
                selectVideo();
                break;
            case R.id.add:
                WriteCourseInfoActivity.action2WriteCourseInfoActivity(this, info);
                break;   case R.id.profile:
                WriteInfoActivity.action2WriteInfoActivity(this, info, true);
                break;
            case R.id.importExcel:
                //学员课程表导出
                List<CourseInfo> courseInfos = DbOperator.getInstance().getCourseByStudentId(mId);
                String[] courseInfoTitles = new String[]{"id", "课程名字", "课程状态", "课程总学时", "可用课程学时", "课程金额原价", "课程金额优惠价格", "课程金额实际支付价格", "类型", "授课老师", "报名时间", "学生电话", "备忘录"};// 设置列中文名
                int courseInfoColumnLength[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};// 设置列宽
                String courseInfoFileds[] = new String[]{"id", "course_name", "course_state", "course_class_hour", "available_class_hour", "course_price", "course_sale", "course_actual_price", "type", "teacher", "date", "phone_number", "memo"};// 设置列英文名
                Excel1Util.writeExcel(info.studentName+"的课程表.xls", courseInfos, courseInfoTitles, courseInfoColumnLength, courseInfoFileds);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_personalcenter, menu);//加载menu文件到布局

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(View view, int position) {
        CourseDetailActivity.action2CourseDetailActivity(this, info, adapter.getItem(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop:
                if (TextUtils.isEmpty(info.vedio)) {
                    Toast.makeText(this,"请先选择播放的视频",Toast.LENGTH_SHORT).show();
                }else {
                    if (videoView.isPlaying()) {
                        if(videoView.getCurrentPosition()-5000 >=0){
                            videoView.seekTo(videoView.getCurrentPosition()-5000);
                        }else{
                            videoView.seekTo(0);
                        }
                    }else{
                        Toast.makeText(this,"请先选择播放的视频",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.pre:
                if (TextUtils.isEmpty(info.vedio)) {
                    Toast.makeText(this,"请先选择播放的视频",Toast.LENGTH_SHORT).show();
                }else {
                    if (videoView.isPlaying()) {
                        videoView.seekTo(videoView.getCurrentPosition()+5000);
                    }else{
                        Toast.makeText(this,"请先选择播放的视频",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.play:
                if (TextUtils.isEmpty(info.vedio)) {
                    Toast.makeText(this,"请先选择播放的视频",Toast.LENGTH_SHORT).show();
                }else {
                    if (videoView.isPlaying()) {
                        play.setText("播放");
                        videoView.pause();
                    }else{
                        play.setText("暂停");
                        videoView.start();
                    }
                }

                break;
        }
    }


    Uri fileUri;
    private void selectVideo() {
        final ListViewPicker picker = new ListViewPicker(new String[]{"录制视频", "从图库选取"}, this);
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
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    try {
                        fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                    startActivityForResult(intent, 222);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 111);
                }
                picker.dismiss();
            }
        });
        picker.show();
    }

    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), "CameraDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" +info.studentName +timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK && null != data) {
            if(requestCode == 222){
                videoView.setVideoPath(fileUri.getPath());
                info.vedio = fileUri.getPath();
                DbOperator.getInstance().updateStudentInfo(info);
            }else if(requestCode == 111){
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedVideo,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String videoPath = cursor.getString(columnIndex);
                videoView.setVideoPath(videoPath);
                info.vedio = videoPath;
                DbOperator.getInstance().updateStudentInfo(info);
                cursor.close();
            }
        }
    }

}
