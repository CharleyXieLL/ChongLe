package com.jiajia.badou.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by XIELEI on 2017/3/16 0016.
 */
public class ViewUtil {

  /**
   * dp转px
   */
  public static int dp2px(Context context, float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
        context.getResources().getDisplayMetrics());
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 保留小数点后两位
   */
  public static double decimalFormat(int cnt, double value) {
    BigDecimal b = new BigDecimal(value);
    double f1 = b.setScale(cnt, BigDecimal.ROUND_HALF_UP).doubleValue();
    return f1;
  }

  /**
   * 保留小数点后两位
   */
  public static String decimalFormat(double value) {
    DecimalFormat df = new DecimalFormat(".##");
    String st = df.format(value);
    return st;
  }

  /**
   * 手动清除ImageView缓存
   */
  public static void releaseImageViewResouce(ImageView imageView) {
    if (imageView == null) return;
    Drawable drawable = imageView.getDrawable();
    if (drawable != null && drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      Bitmap bitmap = bitmapDrawable.getBitmap();
      if (bitmap != null && !bitmap.isRecycled()) {
        bitmap.recycle();
      }
    }
  }

  /**
   * 设置EditText中Hint的字体大小
   */
  public static void setEditHint(int textSize, String hint, EditText editText) {
    SpannableString ss = new SpannableString(hint);
    // 新建一个属性对象,设置文字的大小
    AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
    // 附加属性到文本
    ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    // 设置hint
    editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
  }

  /**
   * 上拉刷新更新时间
   */
  static String dateTime = "第一次刷新";

  public static String getDateTime() {

    String return_datetime_str = dateTime;
    // 取当前时间
    long currentTime = System.currentTimeMillis();
    // 格式化日期
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd H:mm");

    TimeZone timeZone = TimeZone.getTimeZone("GMT+8");

    simpleDateFormat.setTimeZone(timeZone);

    dateTime = simpleDateFormat.format(new Date(currentTime));

    return return_datetime_str;
  }

  /**
   * 动态设置ListView的高度
   */
  public static void setListViewHeightBasedOnChildren(ListView listView) {
    // 获取ListView对应的Adapter
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
      return;
    }
    int totalHeight = 0;
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
      // listAdapter.getCount()返回数据项的数目
      View listItem = listAdapter.getView(i, null, listView);
      // 计算子项View 的宽高
      listItem.measure(0, 0);
      // 统计所有子项的总高度
      totalHeight += listItem.getMeasuredHeight();
    }
    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    // listView.getDividerHeight()获取子项间分隔符占用的高度
    // params.height最后得到整个ListView完整显示需要的高度
    listView.setLayoutParams(params);
  }

  /**
   * 提供精确的小数位四舍五入处理。
   *
   * @param v 需要四舍五入的数字
   * @param scale 小数点后保留几位
   * @return 四舍五入后的结果
   */
  public static String round(double v, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal ne = new BigDecimal("1");
    return String.valueOf(b.divide(ne, scale, BigDecimal.ROUND_HALF_UP));
  }

  /**
   * 判断当前数是否有小数
   */
  public static boolean isInteger(String num) {
    boolean bool = false;
    int dotIndex = 715;
    char[] c = num.toCharArray();
    for (int i = 0; i < num.length(); i++) {
      if (c[i] == '.') {
        dotIndex = i;
      }
    }

    if (Double.parseDouble(num.substring(dotIndex + 1, num.length())) == 0) {
      bool = true;
    }

    return bool;
  }

  public static String amount(double amount) {
    if (isInteger(String.valueOf(amount))) {
      return String.valueOf((int) amount);
    } else {
      return round(amount, 2);
    }
  }

  /**
   * 检测输入年利率输入是否合法
   */
  public static boolean isLegal(String source) {
    if (source.equals("0")
        || source.equals("1")
        || source.equals("2")
        || source.equals("3")
        || source.equals("4")
        || source.equals("5")
        || source.equals("6")
        || source.equals("7")
        || source.equals("8")
        || source.equals("9")
        || source.equals(".")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 检测是否为手机号
   *
   * @throws PatternSyntaxException
   */
  public static boolean isChinaPhoneLegal(String str) {
    String regExp = "^1(3|4|5|7|8)[0-9]\\d{8}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(str);
    return m.matches();
  }

  /**
   * 判断权限是否全部允许
   */
  public static boolean isPass(int[] grantResults) {
    boolean pass = true;
    if (grantResults.length > 0) {
      for (int i = 0; i < grantResults.length; i++) {
        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          pass = false;
        }
      }
    } else {
      pass = false;
    }
    return pass;
  }

  /**
   * @param value 判断是否为空的字符串
   * @param legalString 有效字符串
   */
  public static void getLegalString(TextView view, String value, String legalString) {
    if (Strings.isNullOrEmpty(value)) {
      view.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.VISIBLE);
      view.setText(legalString);
    }
  }

  /**
   * @param legalString 判断是否为空的字符串
   * @param legalString 有效字符串
   */
  public static void getLegalString(View relativeLayout, TextView view, String legalString) {
    if (Strings.isNullOrEmpty(legalString)) {
      relativeLayout.setVisibility(View.GONE);
    } else {
      relativeLayout.setVisibility(View.VISIBLE);
      if (legalString.contains(":")) {
        String[] showString = legalString.split(":");
        for (int i = 0; i < showString.length; i++) {
          if (i == showString.length - 1) {
            view.setText(showString[i]);
          }
        }
      } else if (legalString.contains("：")) {
        String[] showString = legalString.split("：");
        for (int i = 0; i < showString.length; i++) {
          if (i == showString.length - 1) {
            view.setText(showString[i]);
          }
        }
      } else {
        view.setText(legalString);
      }
    }
  }

  /**
   * 检测是否包含两个 ¥
   */
  public static boolean checkContainText(String text) {
    int num = 0;
    char[] c = text.toCharArray();
    for (int i = 0; i < text.length(); i++) {
      if ("￥".equals(String.valueOf(c[i]))) {
        num++;
      }
    }

    if (num > 1) {
      return true;
    }

    return false;
  }

  /**
   * 设置粘贴板内容
   */
  public static void setClipboardText(Activity activity, String text) {
    ClipboardManager clipboardManager =
        (ClipboardManager) activity.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
    clipboardManager.setText(text);
  }

  /**
   * 取出粘贴板中的内容
   */
  public static String getClipboardText(Activity activity) {
    ClipboardManager clipboardManager =
        (ClipboardManager) activity.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
    return String.valueOf(clipboardManager.getText());
  }

  /**
   * 获取状态栏高度
   */
  public static int getBarHeight(Activity activity) {
    Class<?> c;
    Object obj;
    Field field;
    int x, sbar = 38;//默认为38，貌似大部分是这样的

    try {
      c = Class.forName("com.android.internal.R$dimen");
      obj = c.newInstance();
      field = c.getField("status_bar_height");
      x = Integer.parseInt(field.get(obj).toString());
      sbar = activity.getResources().getDimensionPixelSize(x);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return sbar;
  }

  /**
   * 获取控件高度
   */
  public static int getViewHeight(final View view) {
    final int[] height = { 0 };
    ViewTreeObserver vto = view.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        height[0] = view.getHeight();
      }
    });
    return height[0];
  }

  /**
   * 获取控件宽度
   */
  public static int getViewWidth(final View view) {
    final int[] width = { 0 };
    ViewTreeObserver vto = view.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        width[0] = view.getWidth();
      }
    });
    return width[0];
  }
}