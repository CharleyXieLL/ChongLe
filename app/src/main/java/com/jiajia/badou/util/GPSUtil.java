package com.jiajia.badou.util;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import com.jiajia.badou.R;
import com.jiajia.badou.util.baidu.QtLocationClient;
import com.jiajia.badou.view.MessageView;
import com.jiajia.badou.view.PermissionUtil;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * Created by Lei on 2017/10/27.
 * 获取地理位置工具类
 */
public class GPSUtil {

  public static final int RC_PERM_READ_CONTACTS = 122;
  public static final int RC_PERM_TWO_LOCATION = 123;

  private Activity activity;

  public GPSUtil(Activity activity) {
    this.activity = activity;
  }

  /**
   * 开始尝试获取GPS定位信息
   */
  public void start() {
    if (!PermissionUtil.isPermissionGranted(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        || !PermissionUtil.isPermissionGranted(activity,
        Manifest.permission.ACCESS_COARSE_LOCATION)) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // 尝试去请求开放权限
        //checkReadLocationPermission();
        String[] perms = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        };
        PermissionGen.needPermission(activity, RC_PERM_TWO_LOCATION, perms);
      } else {
        showPopWarningDialog(R.string.yq_pop_authorization_location_tips);
      }
    } else {
      trySubmitRequest();
    }
  }

  private Handler handler = new Handler(Looper.getMainLooper());

  QtLocationClient.QtLocationListener qtLocationListener =
      new QtLocationClient.QtLocationListener() {
        @Override public void onReceive(QtLocationClient.Location location, boolean success) {
          QtLocationClient.getInstance(activity).stop();
          gpsUtilCallBack.onReceive(location, success);
        }
      };

  public void trySubmitRequest() {
    QtLocationClient.getInstance(activity).start(qtLocationListener);
  }

  private void showPopWarningDialog(@StringRes int msgRes) {
    MessageView messageView = new MessageView(activity, activity.getString(msgRes));
    messageView.createDialog();
  }

  public void showErrorDialog(int type) {
    if (type == RC_PERM_TWO_LOCATION) {
      handler.post(showWarningDialogWhenGetLocationFailed);
    }
    if (type == RC_PERM_READ_CONTACTS) {
      handler.post(showWarningDialogWhenGetContactsFailed);
    }
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    PermissionGen.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
  }

  private final Runnable showWarningDialogWhenGetContactsFailed = new Runnable() {
    @Override public void run() {
      QtLocationClient.getInstance(activity).stop();
      showPopWarningDialog(R.string.yq_pop_authorization_contact_null_tips);
    }
  };

  private final Runnable showWarningDialogWhenGetLocationFailed = new Runnable() {
    @Override public void run() {
      QtLocationClient.getInstance(activity).stop();
      showPopWarningDialog(R.string.yq_pop_authorization_location_error_tips);
    }
  };

  public void onDestroy() {
    QtLocationClient.getInstance(activity).stop();
    qtLocationListener = null;
    handler.removeCallbacksAndMessages(null);
  }

  public interface GPSUtilCallBack {
    void onReceive(QtLocationClient.Location location, boolean success);
  }

  private GPSUtilCallBack gpsUtilCallBack;

  public void setGpsUtilCallBack(GPSUtilCallBack gpsUtilCallBack) {
    this.gpsUtilCallBack = gpsUtilCallBack;
  }
}
