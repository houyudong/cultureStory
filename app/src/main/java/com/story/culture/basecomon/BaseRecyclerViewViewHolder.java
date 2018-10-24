package com.story.culture.basecomon;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Description:BaseViewHolder
 * @author:east
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2016年8月4日
 */
public class BaseRecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private SparseArray<View> views;

    private BaseRecyclerViewAdapter.OnItemClickListener mOnItemClickListener ;

    public BaseRecyclerViewViewHolder(View itemView, BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener){
        super(itemView);
        itemView.setOnClickListener(this);

        this.mOnItemClickListener =onItemClickListener;
        this.views = new SparseArray<View>();
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }
    public LinearLayout getLinearLayout(int viewId) {
        return retrieveView(viewId);
    }
    public RelativeLayout getRelativeLayout(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    public View getView(int viewId) {
        return retrieveView(viewId);
    }


    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }


        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
