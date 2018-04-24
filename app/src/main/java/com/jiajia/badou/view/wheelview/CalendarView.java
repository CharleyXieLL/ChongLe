package com.jiajia.badou.view.wheelview;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.jiajia.badou.R;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2017/6/13.
 * 日期选择器
 */

public class CalendarView implements View.OnClickListener {

  public static final String CALENDAR_BIRTHDAY = "calendar_birthday";
  public static final String CALENDAR_NORMAL_TIME = "calendar_normal_time";

  private Activity sActivity;
  private Dialog dialog;
  private LinearLayout timepickLinear;
  private ChooseTime chooseTimeView;

  private CalendarViewCallBack calendarViewCallBack;
  private RelativeLayout relatCalendar;

  private String calendarType;

  public CalendarView(Activity sActivity) {
    this.sActivity = sActivity;
    createDialog();
  }

  public void createDialog() {
    if (dialog == null) {
      dialog = new Dialog(sActivity);
      dialog.setCancelable(false);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCanceledOnTouchOutside(false);
      dialog.setContentView(R.layout.yq_calendar_view);

      Window window = dialog.getWindow();
      window.setGravity(Gravity.BOTTOM);
      window.setWindowAnimations(R.style.yq_mypopwindow_anim_style);
      window.getDecorView().setPadding(0, 0, 0, 0);

      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = WindowManager.LayoutParams.MATCH_PARENT;
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }
    initView();
    initListener();
  }

  private void initListener() {

  }

  public void initView() {
    timepickLinear = dialog.findViewById(R.id.timepick);

    chooseTimeView = new ChooseTime(sActivity);

    timepickLinear.removeAllViews();

    relatCalendar = dialog.findViewById(R.id.relat_calendar_view);

    relatCalendar.setOnClickListener(this);
  }

  /**
   * 设置限制日期，默认为空表明日期为今天(控制选择器不能选择今天之前,今天也不行)
   */
  public void setTargetTime(String time, int days, String title) {
    timepickLinear.removeAllViews();
    timepickLinear.addView(chooseTimeView.getDataPick(this, time, days, title));
  }

  public void showCalendar() {
    if (dialog != null && !dialog.isShowing()) {
      dialog.show();
    }
  }

  public void dismissCalendar() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.done:
        selectDay();
        break;
      case R.id.cancel:
        dismissCalendar();
        break;
      case R.id.relat_calendar_view:
        dismissCalendar();
        break;
    }
  }

  public void selectDay() {
    if (chooseTimeView.compareDate()) {
      dismissCalendar();
      String time = chooseTimeView.getTxt();
      calendarViewCallBack.setCalendarTime(time);
    } else {
      if (calendarType.equals(CALENDAR_BIRTHDAY)) {
        ToastUtil.showToast(sActivity.getApplicationContext(), "不能选择今天之后", false);
      }
      if (calendarType.equals(CALENDAR_NORMAL_TIME)) {
        ToastUtil.showToast(sActivity.getApplicationContext(), "不能选择今天之前", false);
      }
    }
  }

  /**
   * 设定指定日期
   */
  public void setTargetDay(String days) {
    if (chooseTimeView != null) {
      chooseTimeView.setTargetDays(days);
      selectDay();
    }
  }

  public void setCalendarType(String type) {
    this.calendarType = type;
    chooseTimeView.setCalendarType(type);
  }

  public String getTimeText() {
    return chooseTimeView.getTxt();
  }

  public String getTimeStr() {
    chooseTimeView.getTxt();
    return chooseTimeView.getCTime();
  }

  public long getDays() {
    return chooseTimeView.getDays();
  }

  public int getDayInt() {
    return (int) chooseTimeView.getDays();
  }

  public interface CalendarViewCallBack {
    void setCalendarTime(String time);
  }

  public void setCalendarViewCallBack(CalendarViewCallBack calendarViewCallBack) {
    this.calendarViewCallBack = calendarViewCallBack;
  }
}
