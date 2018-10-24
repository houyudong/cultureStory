package com.story.culture.basecomon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @author:east
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2016年8月4日
 */
public abstract class BaseRecyclerViewAdapter<T, H extends BaseRecyclerViewViewHolder> extends RecyclerView.Adapter<BaseRecyclerViewViewHolder> {


    protected static final String TAG = BaseRecyclerViewAdapter.class.getSimpleName();

    protected final Context context;

//    protected HashMap<Integer,Integer> layoutResId;

    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener = null;



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public List<T> getData(){
        return mDatas;
    }
    public BaseRecyclerViewAdapter(Context context) {
        this(context, null);
    }
    public BaseRecyclerViewAdapter(Context context, List<T> mDatas) {
        this.mDatas = mDatas == null ? new ArrayList<T>() : mDatas;
        this.context = context;
    }
    @Override
    public BaseRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(viewType), viewGroup, false);
        BaseRecyclerViewViewHolder vh = new BaseRecyclerViewViewHolder(view, mOnItemClickListener);
        return vh;
    }
    @Override
    public void onBindViewHolder(BaseRecyclerViewViewHolder viewHoder, int position) {
        T item = getItem(position);
        convert((H) viewHoder, item,position );
    }
    @Override
    public int getItemViewType(int position) {
        return getType(position);
    }
    public abstract int getType(int position);
    public abstract int getLayoutId(int viewType);
    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() <= 0)
            return 0;
        return mDatas.size();
    }
    public T getItem(int position) {
        if (position >= mDatas.size()) return null;
        return mDatas.get(position);
    }
    public void clear() {
        for (Iterator it = mDatas.iterator(); it.hasNext(); ) {
            T t = (T) it.next();
            int position = mDatas.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }
    /**
     * 从列表中删除某项
     *
     * @param t
     */
    public void removeItem(T t) {
        int position = mDatas.indexOf(t);
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void addData(List<T> mDatas) {
        addData(0, mDatas);
    }

    public void addData(int position, List<T> list) {
        if (list != null && list.size() > 0) {
            for (T t : list) {
                mDatas.add(position, t);
                notifyItemInserted(position);
            }
        }
    }


    public void refreshData(List<T> list) {

        if (list != null && list.size() > 0) {
            clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                mDatas.add(i, list.get(i));
                notifyItemInserted(i);
            }

        }
//        notifyDataSetChanged();
    }
    public void refresh(List<T> list) {

        if (list != null && list.size() > 0) {
//            clear();
            mDatas.clear();
            mDatas.addAll(list);
//            int size = list.size();
//            for (int i = 0; i < size; i++) {
//                mDatas.add(i, list.get(i));
//                notifyItemInserted(i);
//            }

        }
        notifyDataSetChanged();
    }
    public void loadMoreData(List<T> list) {
        if (list != null && list.size() > 0) {
            int size = list.size();
            int begin = mDatas.size();
            for (int i = 0; i < size; i++) {
                mDatas.add(list.get(i));
                notifyItemInserted(i + begin);
            }
        }
    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param viewHoder A fully initialized helper.
     * @param item      The item that needs to be displayed.
     */
    protected abstract void convert(H viewHoder, T item,int position);


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;

    }


}
