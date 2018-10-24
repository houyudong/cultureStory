package com.story.culture.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.story.culture.R;

import java.util.Calendar;


public class DatePicker implements OnClickListener {
	private Context mContext;
	private String[] days;
	private String[] monthes;
	private String[] years;
	private String[] days28;
	private String[] days29;
	private String[] days30;

	private PopupWindow mPopupWindow;
	private Button cancelBtn;
	private LinearLayout layout;
	private Button doneBtn;
	private TextView titleTV;
	private WheelView wvDay;
	private WheelView wvMonth;
	private WheelView wvYear;

	private OnDatePickListener mOnPickListener;

	private int yearIndex, monthIndex, dayIndex;
	private View empty;
	private boolean doublePop;

	public DatePicker(Context mContext, String[] years, int yearIndex, int monthIndex, int dayIndex,
                      OnDatePickListener pickListener) {
		this.mContext = mContext;
		this.years = years;
		this.monthes = getMontyArr();
		this.days = getDayArr(31);
		this.days28 = getDayArr(28);
		this.days29 = getDayArr(29);
		this.days30 = getDayArr(30);

		this.yearIndex = yearIndex;
		this.monthIndex = monthIndex;
		this.dayIndex = dayIndex;

		this.mOnPickListener = pickListener;
		initPopupWindow();
	}
	
	
	public static int getIndexOfTheYear(String year) {

		String[] years = getYearArr();
		int j = years.length;
		int index = j - 1;
		for (int i = 0; i < j; i++) {
			if (year.equals(years[i])) {
				return i;
			}

		}

		return index;
	}

	public static int getIndexOfCurrentYear() {
		return getYearArr().length-1;
	}
	
	public static String[] getYearArr() {

		Calendar a = Calendar.getInstance();
		int year = a.get(Calendar.YEAR);

		String[] hours = new String[year - 1949 + 1];
		for (int i = 1949; i <= year; i++) {
			hours[i - 1949] = String.format("%04d", i);
		}
		return hours;
	}
	
	
	public static String[] getMontyArr() {
		String[] hours = new String[12];
		for (int i = 0; i < 12; i++) {
			hours[i] = String.format("%02d", i + 1);
		}
		return hours;
	}

	public static String[] getDayArr(int length) {
		String[] hours = new String[length];
		for (int i = 0; i < length; i++) {
			hours[i] = String.format("%02d", i + 1);
		}
		return hours;
	}



	private void initPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		layout = (LinearLayout) inflater.inflate(R.layout.datepicker, null);
		empty = layout.findViewById(R.id.view_empty);
		doneBtn = (Button) layout.findViewById(R.id.numberpicker_done);
		cancelBtn = (Button) layout.findViewById(R.id.numberpicker_cancle);
		titleTV = (TextView) layout.findViewById(R.id.numberpicker_title);

		wvYear = (WheelView) layout.findViewById(R.id.wheelview_year);
		wvMonth = (WheelView) layout.findViewById(R.id.wheelview_month);
		wvDay = (WheelView) layout.findViewById(R.id.wheelview_day);
		PickerWheelAdapter yearAdapter = new PickerWheelAdapter(years);
		PickerWheelAdapter monthAdapter = new PickerWheelAdapter(monthes);

		wvYear.addChangingListener(new YearChangeListener());
		wvMonth.addChangingListener(new MonthChangeListener());
		wvDay.addChangingListener(new DayChangeListener());

		wvYear.setAdapter(yearAdapter);
		wvMonth.setAdapter(monthAdapter);

		wvYear.setCurrentItem(yearIndex);
		wvMonth.setCurrentItem(monthIndex);

		updateDate(yearIndex, monthIndex, dayIndex);

		mPopupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// 设置点击窗口外边窗口消失
		mPopupWindow.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		mPopupWindow.setFocusable(true);

		setListener();
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				dismiss();
			}
		});
		mPopupWindow.setAnimationStyle(R.style.picker_anim_style);
	}

	public void setDoublePop(){
		doublePop = true;
		mPopupWindow.setAnimationStyle(0);
//		empty.setBackgroundColor(mContext.getResources().getColor(R.color.half_dark));
	}

	public WheelView getWvYear() {
		return wvYear;
	}

	public WheelView getWvMonth() {
		return wvMonth;
	}

	public WheelView getWvDay() {
		return wvDay;
	}

	public void setTitle(String title) {
		titleTV.setText(title);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.numberpicker_cancle:
			mOnPickListener.onCancle();
			break;
		case R.id.numberpicker_done:
			mOnPickListener.onDone(years[wvYear.getCurrentItem()], monthes[wvMonth.getCurrentItem()],
					days[wvDay.getCurrentItem()]);
			break;
		default:
			break;
		}
		this.dismiss();
	}

	private void setListener() {
		cancelBtn.setOnClickListener(this);
		doneBtn.setOnClickListener(this);
		empty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void show() {
//		if (doublePop){
//			StatusBarCompat.setStatusBarColor((Activity)mContext,mContext.getResources().getColor(R.color.half_dark));
//		}
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
		lp.alpha = 0.5f; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
		// mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.welcome));
		layout.setFocusable(true);// 设置该view能监听事件
		layout.setFocusableInTouchMode(true);// 设置该view能监听事件
		layout.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && mPopupWindow != null) {
					dismiss();
				}
				return true;
			}
		});
		mPopupWindow.update();
		mPopupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

	}

	public void dismiss() {
//		if (doublePop){
//			StatusBarCompat.setStatusBarColor((Activity)mContext, Color.parseColor("#f7f7f7"));
//		}
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
		lp.alpha = 1.0f; // 0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
		mOnPickListener.onCancle();
		mPopupWindow.dismiss();
		mPopupWindow = null;
	}

	/**
	 * 
	 * @Description:WheelView适配器
	 * @author:
	 * @see:
	 * @since:
	 * @copyright © ciyun.cn
	 * @Date:2014年8月12日
	 */
	private class PickerWheelAdapter implements WheelView.WheelAdapter {
		private String texts[];

		public PickerWheelAdapter(String items[]) {
			this.texts = items;
		}

		public String getItem(int index) {
			if (index >= 0 && index < texts.length) {
				return texts[index].toString();
			}
			return null;
		}

		public int getItemsCount() {
			return texts.length;
		}

		public int getMaximumLength() {
			return -1;
		}

	}

	/**
	 * 
	 * 判断月份与年份，从而限制日期超出当月最大天数的情况
	 * 
	 * @param yearIndex
	 * @param monthIndex
	 * @param dayIndex
	 */
	private void updateDate(int yearIndex, int monthIndex, int dayIndex) {
		switch (monthIndex) {

		case 1:// 二月
			if (Integer.parseInt(years[yearIndex]) % 4 == 0) {
				updateDayIndex(29, dayIndex);

			} else {
				updateDayIndex(28, dayIndex);
			}
			break;

		case 3:
			updateDayIndex(30, dayIndex);
			break;

		case 5:
			updateDayIndex(30, dayIndex);
			break;

		case 8:
			updateDayIndex(30, dayIndex);
			break;

		case 10:
			updateDayIndex(30, dayIndex);
			break;

		default:
			updateDayIndex(31, dayIndex);
			break;
		}
	}

	void updateDayIndex(int length, int currentIndex) {
		PickerWheelAdapter dayAdapter;

		if (length == 28) {
			dayAdapter = new PickerWheelAdapter(days28);

		} else if (length == 29) {
			dayAdapter = new PickerWheelAdapter(days29);

		} else if (length == 30) {

			dayAdapter = new PickerWheelAdapter(days30);
		} else {
			dayAdapter = new PickerWheelAdapter(days);
		}

		if (currentIndex >= length) {
			currentIndex = length - 1;
		}

		dayIndex = currentIndex;

		wvDay.setAdapter(dayAdapter);
		wvDay.setCurrentItem(currentIndex);

		/*
		 * dayAdapter = new PickerWheelAdapter(days28);
		 * wvDay.setAdapter(dayAdapter); if (dayIndex >= 28) { dayIndex = 27; }
		 * wvDay.setCurrentItem(dayIndex);
		 */
	}

	/**
	 * 
	 * @Description:Pick监听器
	 * @author:
	 * @see:
	 * @since:
	 * @copyright © ciyun.cn
	 * @Date:2014年8月12日
	 */
	public interface OnDatePickListener {
		public void onDone(String year, String month, String day);

		public void onCancle();
	}

	class YearChangeListener implements WheelView.OnWheelChangedListener {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			yearIndex = newValue;
			updateDate(yearIndex, monthIndex, dayIndex);
		}

	}

	class MonthChangeListener implements WheelView.OnWheelChangedListener {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			monthIndex = newValue;
			updateDate(yearIndex, monthIndex, dayIndex);
		}

	}

	class DayChangeListener implements WheelView.OnWheelChangedListener {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			dayIndex = newValue;
		}

	}
}
