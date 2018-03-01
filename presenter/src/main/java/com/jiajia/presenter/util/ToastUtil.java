package com.jiajia.presenter.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by johnwatsondev on 05/03/2017.
 */
// FIXME: 05/03/2017 待完善，静态持有 handler 对象很可耻。
public final class ToastUtil {
  private static final String TAG = ToastUtil.class.getSimpleName();

  private static Handler sHandler = new Handler(Looper.getMainLooper());
  private static Toast sToast;

  public static void cleanup() {
    sHandler.removeCallbacksAndMessages(null);
    sToast = null;
    sHandler = null;
  }

  public static void showShortToast(Context c, @StringRes int resId) {
    showNormalToast(c, resId, false);
  }

  public static void showShortToast(Context c, String info) {
    showNormalToast(c, info, false);
  }

  public static void showToast(Context c, @StringRes int resId, boolean isLong) {
    showNormalToast(c, resId, isLong);
  }

  public static void showToast(Context c, String info, boolean isLong) {
    showNormalToast(c, info, isLong);
  }

  /**
   * 显示系统自带的Toast
   *
   * @param isLong true 为   LENGTH_LONG；false  为   LENGTH_SHORT
   */
  private static void showNormalToast(Context c, Object info, boolean isLong) {
    final String showTextStr;
    if (info instanceof Integer) {
      showTextStr = c.getApplicationContext().getString((Integer) info);
    } else if (info instanceof String) {
      showTextStr = (String) info;
    } else {
      Log.e(TAG, "传入的pInfo不是文本，无法显示！");
      return;
    }

    initNormalToast(c, isLong);
    if (isLong) {
      sToast.setDuration(Toast.LENGTH_LONG);
    } else {
      sToast.setDuration(Toast.LENGTH_SHORT);
    }

    // FIXME: 31/10/2016 This method may be called from non-UI thread.
    // And may cause: android.view.ViewRootImpl$CalledFromWrongThreadException
    //Only the original thread that created a view hierarchy can touch its views.

    if (Looper.myLooper() != Looper.getMainLooper()) {
      sHandler.post(new Runnable() {
        @Override public void run() {
          sToast.setText(showTextStr);
          sToast.show();
        }
      });
    } else {
      sToast.setText(showTextStr);
      sToast.show();
    }
  }

  /**
   * 初始化系统自带的Toast
   */
  @SuppressLint("ShowToast") private static void initNormalToast(Context c, boolean isLong) {
    if (sToast == null) {
      if (isLong) {
        sToast = Toast.makeText(c.getApplicationContext(), "", Toast.LENGTH_LONG);
      } else {
        sToast = Toast.makeText(c.getApplicationContext(), "", Toast.LENGTH_SHORT);
      }
    }
  }
}