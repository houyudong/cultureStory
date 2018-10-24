package com.story.culture.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.story.culture.R;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.basecomon.BaseRecyclerViewViewHolder;
import com.story.culture.database.ConsumeClassTimeInfo;

import java.util.List;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class UseCourseInfoAdapter extends BaseRecyclerViewAdapter<ConsumeClassTimeInfo,BaseRecyclerViewViewHolder> {
    public UseCourseInfoAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }
    private LayoutInflater inflater;
    @Override
    public int getType(int position){
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() <= 0)
            return 20;
        return mDatas.size()+20;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_consume_course_info_adapter ;
    }
    public void refresh(List<ConsumeClassTimeInfo> list) {
        if (list != null && list.size() > 0) {
            clear();
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseRecyclerViewViewHolder viewHoder, final ConsumeClassTimeInfo item, int position) {
        if(item != null ){
//            viewHoder.getImageView(R.id.img).setBackground(new BitmapDrawable(new Bitmap()));
            ImageLoader.getInstance().displayImage(item.photo, viewHoder.getImageView(R.id.img));
            viewHoder.getTextView(R.id.name).setText(item.course_name);
            viewHoder.getTextView(R.id.teacher).setText("授课老师："+item.teacher);
            viewHoder.getTextView(R.id.class_time).setText("消耗学时："+item.course_class_hour);
            viewHoder.getTextView(R.id.date).setText("签到日期："+item.time);
            if(TextUtils.isEmpty(item.memo)){
                viewHoder.getTextView(R.id.memo).setVisibility(View.GONE);
            }else{
                viewHoder.getTextView(R.id.memo).setVisibility(View.VISIBLE);
                viewHoder.getTextView(R.id.memo).setText("备注："+item.memo);
            }

        }

    }

}
