package com.jiajia.badou.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.jiajia.presenter.util.SharedPrefUtil;

/**
 * Created by Lei on 07/03/2017.
 */
public final class BaseSharedDataUtil {

  /**
   * 保存登录用户的一些信息的 sp 文件名称
   */
  private static final String LOGIN_USER_PREF = "loginUserSharedPref";

  /**
   * 用户登录的凭证
   */
  private static final String TOKEN = "token";

  /**
   * 用户手机号
   */
  private static final String MOBILE_PHONE_NO = "mobile_phone_no";

  /**
   * 用户 id
   */
  private static final String USER_ID = "user_id";

  /**
   * 用户头像
   */
  private static final String USER_AVATAR = "user_avatar";

  /**
   * 宠物头像
   */
  private static final String PET_AVATAR = "pet_avatar";

  /**
   * 保存手机号
   */
  public static void setPhoneNo(Context context, String mobelphoneno) {
    getSharedPrefUtil(context).putString(MOBILE_PHONE_NO, mobelphoneno).apply();
  }

  /**
   * 获取手机号
   */
  public static String getPhoneNo(Context context) {
    return getSharedPrefUtil(context).getString(MOBILE_PHONE_NO, "");
  }

  /**
   * 保存用户登录令牌
   */
  public static void setToken(Context context, String token) {
    getSharedPrefUtil(context).putString(TOKEN, token).apply();
  }

  /**
   * 获取用户登录令牌
   */
  public static String getToken(Context context) {
    return getSharedPrefUtil(context).getString(TOKEN, "");
  }

  public static void setUserAvatar(Context context, String userAvatar) {
    getSharedPrefUtil(context).putString(USER_AVATAR, userAvatar).apply();
  }

  public static String getUserAvatar(Context context) {
    return getSharedPrefUtil(context).getString(USER_AVATAR, "");
  }

  public static void setPetAvatar(Context context, String avatar) {
    getSharedPrefUtil(context).putString(PET_AVATAR, avatar).apply();
  }

  public static String getPetAvatar(Context context) {
    return getSharedPrefUtil(context).getString(PET_AVATAR, "");
  }

  public static void setUserId(Context context, int userId) {
    getSharedPrefUtil(context).putInt(USER_ID, userId).apply();
  }

  public static int getUserId(Context context) {
    return getSharedPrefUtil(context).getInt(USER_ID, 0);
  }

  private BaseSharedDataUtil() {

  }

  private static SharedPrefUtil sharedPrefUtil;

  private static SharedPrefUtil getSharedPrefUtil(Context context) {
    if (sharedPrefUtil == null) {
      synchronized (BaseSharedDataUtil.class) {
        if (sharedPrefUtil == null) {
          sharedPrefUtil =
              SharedPrefUtil.getNewInstance(context.getApplicationContext(), LOGIN_USER_PREF);
        }
      }
    }

    return sharedPrefUtil;
  }

  public static String getAppMetaData(Context ctx, String key) {
    if (ctx == null || TextUtils.isEmpty(key)) {
      return null;
    }
    String resultData = null;
    try {
      PackageManager packageManager = ctx.getPackageManager();
      if (packageManager != null) {
        ApplicationInfo applicationInfo =
            packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
        if (applicationInfo != null) {
          if (applicationInfo.metaData != null) {
            resultData = applicationInfo.metaData.getString(key);
          }
        }
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return resultData;
  }
}