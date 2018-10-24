package com.story.culture.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.story.culture.R;
import com.story.culture.adapter.UseCourseInfoAdapter;
import com.story.culture.views.RoundAngleImageView;
import com.story.culture.views.middleScrollView.ContentRecyclerView;
import com.story.culture.views.middleScrollView.ScrollLayout;
import com.story.utils.DensityUtil;
import com.story.utils.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UseClassTimeActivity extends AppCompatActivity  {

    public static void action2UseClassTimeActivity(Context c, String phoneNum) {
        Intent intent = new Intent(c, UseClassTimeActivity.class);
        intent.putExtra("phoneNum", phoneNum);
        c.startActivity(intent);
    }

}
