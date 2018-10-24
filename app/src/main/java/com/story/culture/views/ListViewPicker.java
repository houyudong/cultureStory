package com.story.culture.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.stetho.inspector.console.CLog;
import com.story.culture.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:自定义ListViewPicker控件类
 * @author:
 * @see:
 * @since:
 * @copyright © ciyun.cn
 * @Date:2014年8月12日
 */
public class ListViewPicker {

    private ArrayList<Map<String, Object>> list;
    private JSONArray jsonArr;
    private Context mContext;


    private ListPopUpWindow mPopupWindow;
    private ListView mListView;
    private Button cancelBtn;
    private LinearLayout layout;
public String getValue(int position){
   return (String) list.get(position).get("value");
}
    public ListViewPicker(String[] arr, Context context) {
        int length = arr.length;
        list = new ArrayList<Map<String, Object>>();
        Map<String, Object> tmp;

        for (int i = 0; i < length; i++) {
            tmp = new HashMap<String, Object>();
            tmp.put("value", arr[i]);
            list.add(tmp);
        }

        mContext = context;
        init(false);
    }

    public ListViewPicker(String[] arr, int[] icons, Context context) {
        int length = arr.length;
        jsonArr = new JSONArray();
        JSONObject tmp;
        for (int i = 0; i < length; i++) {
            try {
                tmp = new JSONObject();
                tmp.put("value", arr[i]);
                tmp.put("icon", icons[i]);
                jsonArr.put(i, tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mContext = context;
        init(true);
    }

    private void init(boolean isDisplayIcon) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        layout = (LinearLayout) inflater.inflate(R.layout.listviewpicker, null);
        mListView = (ListView) layout.findViewById(R.id.picker_listview);
        cancelBtn = (Button) layout.findViewById(R.id.picker_cancel_btn);
        /*layout.setAnimation(Animation.);*/
        BaseAdapter mListAdapter;
        if (isDisplayIcon) {
            mListAdapter = new ListViewPickerAdapter(mContext, jsonArr);
        } else {
            mListAdapter = new SimpleAdapter(mContext, list, R.layout.listviewpicker_item,
                    new String[]{"value"},
                    new int[]{R.id.listviewpicker_item_text});
        }
        mListView.setAdapter(mListAdapter);
        mPopupWindow = new ListPopUpWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        //设置点击窗口外边窗口消失
        mPopupWindow.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        mPopupWindow.setAnimationStyle(R.style.picker_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }
        });
    }

    public void setCancelListener(OnClickListener listener) {
        cancelBtn.setOnClickListener(listener);
    }

    public void setOnSelectListener(OnItemClickListener listener) {
        mListView.setOnItemClickListener(listener);
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mPopupWindow.setOnDismissListener(listener);
    }

    public void show() {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = 0.4f; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
        layout.setFocusable(true);//设置该view能监听事件
        layout.setFocusableInTouchMode(true);//设置该view能监听事件
        layout.setBackgroundDrawable(new ColorDrawable(0x00000000));
        layout.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && mPopupWindow != null) {
                    dismiss();
                }
                return true;
            }
        });
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null) {
                    dismiss();
                }
            }
        });
        mPopupWindow.update();
        mPopupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = 1.0f; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
        layout.setVisibility(View.GONE);
        mPopupWindow.dismiss();
        mPopupWindow = null;
    }

    /**
     * @Description:ListViewPicker适配器
     * @author:
     * @see:
     * @since:
     * @copyright © ciyun.cn
     * @Date:2014年8月13日
     */
    public class ListViewPickerAdapter extends BaseAdapter {

        private Context mContext;
        private JSONArray jsonArr;

        public ListViewPickerAdapter(Context mContext, JSONArray jsonArr) {
            this.mContext = mContext;
            this.jsonArr = jsonArr;
        }

        @Override
        public int getCount() {
            return jsonArr.length();
        }

        @Override
        public Object getItem(int position) {
            JSONObject ret = new JSONObject();
            try {
                ret = jsonArr.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ret;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = (LinearLayout) inflater.inflate(R.layout.listviewpicker_withicon_item, null);
            }
            ImageView image = (ImageView) convertView.findViewById(R.id.listviewpicker_item_icon);
            TextView text = (TextView) convertView.findViewById(R.id.listviewpicker_item_text);
            JSONObject object = (JSONObject) getItem(position);
            try {
                image.setImageDrawable(mContext.getResources().getDrawable((Integer) object.get("icon")));
                //image.setImageResource(mContext.getResources().get((Integer) object.get("icon")));
                text.setText(object.getString("value"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }

    }
}
