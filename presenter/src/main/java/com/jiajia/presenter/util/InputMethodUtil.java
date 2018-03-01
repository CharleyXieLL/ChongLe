package com.jiajia.presenter.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by XL on 2016/12/23 0023.
 */
public class InputMethodUtil {
  private Activity activity;

  public InputMethodUtil(Activity activity) {
    this.activity = activity;
  }

  /**
   * 显示输入法
   */
  public void showInputMethod(View view) {
    /** 若输入法关闭则打开 */
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    view.setFocusable(true);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
  }

  /**
   * 在Dialog或者PopupWindow中隐藏输入法键盘
   */
  public void hidenInputMethodInDialogOrPopupWindow(View view) {
    /** 若输入法打开则关闭 */
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
    if (isOpen) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  /**
   * 直接用edittext关闭输入法
   */
  public void hidenInputMethodWithEditText(EditText editText) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    boolean isOpen = imm.isActive();
    if (isOpen) {
      // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
      imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
  }

  /**
   * 在Activity中隐藏输入法键盘
   */
  public void hidenInputMethodInActivity() {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
    if (isOpen) {
      try {
        InputMethodManager inputMethodManager =
            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 判断输入法是否已经打开
   */
  public boolean isInputMethodShow() {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
    return isOpen;
  }

  public static void hideSoftInput(View view) {
    if (view == null || view.getContext() == null) return;
    InputMethodManager imm =
        (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    view.clearFocus();
    if (imm != null && imm.isActive()) {
      imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  public static void showSoftInput(EditText editText) {
    if (editText == null) return;
    InputMethodManager imm =
        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    do {
      editText.requestFocus();
    } while (!editText.isFocused());

    if (imm.isActive()) {
      imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
      imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }
}