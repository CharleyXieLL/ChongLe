package com.jiajia.badou.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Lei on 2017/10/9.
 * 获取手机设备信息
 */
public class PhoneInfo {

  private static TelephonyManager tm;

  /**
   * 获取SIM硬件信息
   */
  public static TelephonyManager getTelephonyManager(Context context) {
    if (tm == null) tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    //        StringBuffer sb = new StringBuffer();
    //        sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
    //        sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
    //        sb.append("\nLine1Number = " + tm.getLine1Number());
    //        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
    //        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
    //        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
    //        sb.append("\nNetworkType = " + tm.getNetworkType());
    //        sb.append("\nPhoneType = " + tm.getPhoneType());
    //        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
    //        sb.append("\nSimOperator = " + tm.getSimOperator());
    //        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
    //        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
    //        sb.append("\nSimState = " + tm.getSimState());
    //        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
    //        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
    //        LogUtils.i(sb.toString());
    return tm;
  }

  /**
   * 获取屏幕分辨率
   */
  public static int[] getMetrics(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    int width = point.x;
    int height = point.y;
    int[] metrics = { width, height };
    return metrics;
  }

  /**
   * 设备厂商
   */
  public static String getPhoneBrand() {
    return Build.BOARD + "  " + Build.MANUFACTURER;
  }

  /**
   * 设备名称
   */
  public static String getPhoneModel() {
    return Build.MODEL;
  }

  /**
   * 获得APP名称
   */
  public static String getAppName(Context context) {
    String appName = "";
    try {
      PackageManager packageManager = context.getPackageManager();
      ApplicationInfo applicationInfo =
          packageManager.getApplicationInfo(context.getPackageName(), 0);
      appName = (String) packageManager.getApplicationLabel(applicationInfo);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return appName;
  }

  /**
   * 获得IMEI
   */
  @SuppressLint("HardwareIds") public static String getIMEI(Context context) {
    return getTelephonyManager(context).getDeviceId();
  }
}
