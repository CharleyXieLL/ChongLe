package com.jiajia.badou.jgpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.PushApiActivity;
import com.jiajia.badou.bean.NotificationEntity;
import com.jiajia.badou.bean.PushBean;
import com.jiajia.presenter.util.Strings;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
  private static final String TAG = "JIGUANG-Example";

  @Override public void onReceive(Context context, Intent intent) {
    try {
      Bundle bundle = intent.getExtras();
      Log.i(TAG,
          "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

      if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
        //send the Registration Id to your server...
      } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        processCustomMessage(context, bundle);
      } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        Log.i(TAG, "[MyReceiver] 接收到推送下来的通知");
        processCustomMessage(context, bundle);
      } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
        processCustomMessage(context, bundle);
      } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
        Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(
            JPushInterface.EXTRA_EXTRA));
        //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
      } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
      } else {
        Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 打印所有的 intent extra 数据
  private static String printBundle(Bundle bundle) {
    StringBuilder sb = new StringBuilder();
    for (String key : bundle.keySet()) {
      if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
      } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
        sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
      } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
        if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
          Log.i(TAG, "This message has no Extra data");
          continue;
        }

        try {
          JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
          Iterator<String> it = json.keys();

          while (it.hasNext()) {
            String myKey = it.next();
            sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]");
          }
        } catch (JSONException e) {
          Log.i(TAG, "Get message extra JSON error!");
        }
      } else {
        sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
      }
    }
    return sb.toString();
  }

  //send msg to MainActivity
  private void processCustomMessage(Context context, Bundle bundle) {
    String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
    if (!Strings.isNullOrEmpty(extras)) {
      try {
        JSONObject extraJson = new JSONObject(extras);
        if (extraJson.length() > 0) {
          String dataUrl = extraJson.get("url").toString();
          NotificationEntity notificationBean = new NotificationEntity(null, null, dataUrl);
          Intent intent = getTargetIntent(context, notificationBean);
          if (intent == null) return;
          Notification.Builder notification;
          notification =
              new Notification.Builder(context).setContentTitle("" + notificationBean.getTitle())
                  .setContentText(notificationBean.getContent())
                  .setLargeIcon(
                      BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
          notification.setAutoCancel(true);
          notification.setContentIntent(getPendingIntent(context, intent));
          NotificationManager notificationManager =
              (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
          notificationManager.notify(0, notification.getNotification());
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  /***
   * 获取对应intent
   * @param context
   * @param notificationBean
   * @return
   */

  private Intent getTargetIntent(Context context, NotificationEntity notificationBean) {
    Intent intent = new Intent(context, PushApiActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(PushApiActivity.PUSHBEAN, new PushBean("", notificationBean.getData()));
    intent.putExtras(bundle);
    return intent;
  }

  /***
   * 构造intent
   * @param context
   * @param intent
   * @return
   */
  public PendingIntent getPendingIntent(Context context, Intent intent) {
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
  }
}
