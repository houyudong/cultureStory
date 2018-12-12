package com.story.culture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.story.culture.R;
import com.story.culture.basecomon.BaseRecyclerViewAdapter;
import com.story.culture.basecomon.BaseRecyclerViewViewHolder;
import com.story.culture.database.CourseInfo;
import com.story.culture.database.StudentInfo;

import java.util.List;

/**
 * @Description:
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 侯玉东 on 2018/09/27
 */
public class CourseInfoAdapter extends BaseRecyclerViewAdapter<CourseInfo, BaseRecyclerViewViewHolder> {
    public CourseInfoAdapter(Context context) {
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
    public int getLayoutId(int viewType) {
        return R.layout.item_course_info_adapter;
    }

    public void refresh(List<CourseInfo> list) {
        if (list != null && list.size() > 0) {
            clear();
            mDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseRecyclerViewViewHolder viewHoder, final CourseInfo item, int position) {
        if (item != null) {
//            viewHoder.getImageView(R.id.img).setBackgroundResource();
            switch (item.course_name) {
                case "吉他":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_guitar_bg);
                    break;
                case "钢琴":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_drum);
                    break;
                case "声乐":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_guitar_bg);
                    break;
                case "古筝":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_drum);
                    break;
                case "尤克里里":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_guitar_bg);
                    break;
                case "非洲鼓":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_drum);
                    break;
                case "架子鼓":
                    viewHoder.getImageView(R.id.img).setBackgroundResource(R.drawable.drawable_guitar_bg);
                    break;
            }
            viewHoder.getTextView(R.id.name).setText(item.course_name);
            viewHoder.getTextView(R.id.teacher).setText("授课老师：" + item.teacher);
            viewHoder.getTextView(R.id.pay_price).setText("服务类型：" + item.type);
            viewHoder.getTextView(R.id.memo).setText("备注信息： " + item.memo);
//            if ("按次".equalsIgnoreCase(item.type)) {
//                viewHoder.getTextView(R.id.time).setText(item.available_class_hour + "/" + item.course_class_hour);
//            } else {
//            viewHoder.getTextView(R.id.time).setText(item.type);
//            }
            if (item.course_state == 0) {
                viewHoder.getView(R.id.view).setVisibility(View.VISIBLE);
                viewHoder.getImageView(R.id.finish).setVisibility(View.VISIBLE);
            }else{
                viewHoder.getView(R.id.view).setVisibility(View.INVISIBLE);
                viewHoder.getImageView(R.id.finish).setVisibility(View.INVISIBLE);
            }

        }

    }

}
