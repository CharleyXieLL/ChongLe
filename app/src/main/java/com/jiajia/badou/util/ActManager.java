package com.jiajia.badou.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ActManager {

  private Stack<Activity> activityStack = new Stack<>();
  private static volatile ActManager instance;

  private ActManager() {

  }

  /**
   * 单一实例
   */
  public static synchronized ActManager getAppManager() {
    if (instance == null) {
      instance = new ActManager();
    }
    return instance;
  }

  /**
   * 添加Activity到堆栈
   */
  public void add(Activity activity) {
    if (activityStack == null) {
      activityStack = new Stack<>();
    }
    activityStack.add(activity);
  }

  /**
   * 把 Activity 从堆栈移除
   */
  public void remove(Activity activity) {
    if (activityStack != null) {
      activityStack.remove(activity);
    }
  }

  /**
   * 获取当前Activity（堆栈中最后一个压入的）
   */
  public Activity currentActivity() {
    Activity activity = activityStack.lastElement();
    return activity;
  }

  /**
   * 结束当前Activity（堆栈中最后一个压入的）
   */
  public void finishActivity() {
    Activity activity = activityStack.lastElement();
    finishActivity(activity);
  }

  /**
   * 结束指定的Activity
   */
  public void finishActivity(Activity activity) {
    if (activity != null) {
      if (activityStack.contains(activity)) activityStack.remove(activity);
      activity.finish();
    }
  }

  /**
   * 结束指定类名的Activity
   */
  public void finishActivity(Class<?> cls) {
    if (activityStack.size() <= 0) return;
    for (int i=0;i<activityStack.size();i++) {
      if (activityStack.get(i).getClass().equals(cls)) {
        finishActivity(activityStack.get(i));
      }
    }
  }

  /**
   * 结束所有Activity
   */
  public void finishAllActivity() {
    for (int i = 0, size = activityStack.size(); i < size; i++) {
      if (null != activityStack.get(i)) {
        activityStack.get(i).finish();
      }
    }
    activityStack.clear();
  }

  /**
   * 退出应用程序
   */
  public void AppExit(Context context) {
    try {
      finishAllActivity();
      ActivityManager activityMgr =
          (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      activityMgr.restartPackage(context.getPackageName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}