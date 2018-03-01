package com.jiajia.badou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import java.util.Locale;

/**
 * @author 超
 * @description 网络工具类
 * @email 364290013@qq.com
 * @date 2014-5-9
 * @change JohnWatson
 */
public class NetStateUtil {

  //private static final String TAG = "NetStateUtil";

  public final static int WIFI = 1;
  public final static int MOBILE = 2;
  public final static int NONE = -1;

  /**
   * 获取当前的网络状态
   */
  public static int getConnectState(Context context) {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (manager == null) return NONE;

    NetworkInfo netWorkInfo = manager.getActiveNetworkInfo();

    if (netWorkInfo == null || !netWorkInfo.isAvailable() || !netWorkInfo.isConnected()) {
      return NONE;
    } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return WIFI;
    } else {
      return MOBILE;
    }
  }

  public static String getConnectedType(Context context) {
    String type = "unknown";
    if (getConnectState(context) == WIFI) {
      type = "wifi";
    } else if (getConnectState(context) == MOBILE) {
      type = "mobile";
    }
    return type;
  }

  /**
   * true if network has connected
   */
  public static boolean isConnected(Context context) {
    return NetStateUtil.getConnectState(context) != NONE;
  }

  ///**
  // * Get IP address from first non-localhost interface
  // *
  // * @param useIPv4 true=return ipv4, false=return ipv6
  // * @return address or empty string
  // */
  //public static String getIPAddress(boolean useIPv4) {
  //  try {
  //    List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
  //    for (NetworkInterface intf : interfaces) {
  //      List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
  //      for (InetAddress addr : addrs) {
  //        if (!addr.isLoopbackAddress()) {
  //          String sAddr = addr.getHostAddress().toUpperCase();
  //          //import org.apache.http.conn.util.InetAddressUtils;
  //          boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
  //          if (useIPv4) {
  //            if (isIPv4) return sAddr;
  //          } else {
  //            if (!isIPv4) {
  //              int delim = sAddr.indexOf('%'); // drop ip6 port suffix
  //              return delim < 0 ? sAddr : sAddr.substring(0, delim);
  //            }
  //          }
  //        }
  //      }
  //    }
  //  } catch (Exception ex) {
  //  } // for now eat exceptions
  //  return "";
  //}

  public static String getWifiIPV4Address(Context context) {
    WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    return ip;
  }

  /**
   * Get the IP of current Wi-Fi connection
   * <br>Credit: http://itekblog.com/android-get-mobile-ip-address/
   *
   * @return IP as string
   */
  public static String getWifiIP(Context context) {
    try {
      WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
      WifiInfo wifiInfo = wifiManager.getConnectionInfo();
      int ipAddress = wifiInfo.getIpAddress();
      return String.format(Locale.getDefault(), "%d.%d.%d.%d", (ipAddress & 0xff),
          (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    } catch (Exception e) {
      e.printStackTrace();
      //Log.e(TAG, e.getMessage());
      return "";
    }
  }

  /**
   * Get IP For mobile
   * <br>Credit: http://itekblog.com/android-get-mobile-ip-address/
   *
   * @param useIPv4 true=return ipv4, false=return ipv6
   * @return address or empty string
   */
  //public static String getMobileIP(boolean useIPv4) {
  //  try {
  //    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
  //        en.hasMoreElements(); ) {
  //      NetworkInterface intf = (NetworkInterface) en.nextElement();
  //      for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
  //          enumIpAddr.hasMoreElements(); ) {
  //        InetAddress inetAddress = enumIpAddr.nextElement();
  //
  //        //import org.apache.http.conn.util.InetAddressUtils;
  //        if (useIPv4) {
  //          if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(
  //              inetAddress.getHostAddress())) {
  //            return inetAddress.getHostAddress();
  //          }
  //        } else {
  //          if (!inetAddress.isLoopbackAddress()) {
  //            return inetAddress.getHostAddress();
  //          }
  //        }
  //      }
  //    }
  //  } catch (SocketException e) {
  //    e.printStackTrace();
  //    Log.e(TAG, "Exception in Get IP Address: " + e.toString());
  //  }
  //  return "";
  //}
}