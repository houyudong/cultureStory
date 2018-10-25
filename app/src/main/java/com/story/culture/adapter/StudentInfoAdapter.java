package com.story.culture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.story.culture.R;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.basecomon.BaseRecyclerViewViewHolder;
import com.story.culture.database.DbOperator;
import com.story.culture.database.StudentInfo;

import java.util.List;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class StudentInfoAdapter extends BaseRecyclerViewAdapter<StudentInfo, BaseRecyclerViewViewHolder> {
    public StudentInfoAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    private LayoutInflater inflater;

    @Override
    public int getType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() <= 0)
            return 0;
        return mDatas.size();
    }

    @Override
    public StudentInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_student_info_adapter;
    }

    public void refresh(List<StudentInfo> list) {
        if (list != null && list.size() > 0) {
            clear();
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseRecyclerViewViewHolder viewHoder, final StudentInfo item, int position) {
        if (item != null) {
            if (item.sex == 1) {
                viewHoder.getImageView(R.id.sex_img).setBackgroundResource(R.drawable.drawable_man);
            } else if (item.sex == 2) {
                viewHoder.getImageView(R.id.sex_img).setBackgroundResource(R.drawable.drawable_girl);
            }
            viewHoder.getTextView(R.id.name).setText(item.studentName);
            viewHoder.getTextView(R.id.phone_num).setText("电话：" + item.studentPhonenumber);
            viewHoder.getTextView(R.id.course).setText("入学时间" + item.startDate);
//            viewHoder.getTextView(R.id.class_hour).setText(item.studentName);
            if (DbOperator.getInstance().querySutdent(item.id)) {
                viewHoder.getImageView(R.id.sex_img).setVisibility(View.VISIBLE);
            } else {
                viewHoder.getImageView(R.id.sex_img).setVisibility(View.INVISIBLE);
            }
        }

    }

}
