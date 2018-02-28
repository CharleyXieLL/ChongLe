package com.jiajia.badou.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;
import java.util.Set;

/**
 * 保存SharedPreferences工具类
 *
 * @author JohnWatson
 * @version 1.0
 * @date 2013-11-19 下午3:19:40
 */
public class SharedPrefUtil {

  private final Editor mEditor;
  private final SharedPreferences mSharedPreferences;

  private SharedPrefUtil(Context context, String name) {
    if (context == null || TextUtils.isEmpty(name)) {
      throw new IllegalArgumentException("Constructor parameters cannot be null or empty !!!");
    }
    mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    mEditor = mSharedPreferences.edit();
  }

  public static SharedPrefUtil getNewInstance(Context context, String name) {
    return new SharedPrefUtil(context, name);
  }

  public Editor putInt(String key, int value) {
    return mEditor.putInt(key, value);
  }

  public Editor putFloat(String key, float value) {
    return mEditor.putFloat(key, value);
  }

  public Editor putLong(String key, long value) {
    return mEditor.putLong(key, value);
  }

  public Editor putBoolean(String key, boolean value) {
    return mEditor.putBoolean(key, value);
  }

  public Editor putString(String key, String value) {
    return mEditor.putString(key, value);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public Editor putStringSet(String key, Set<String> defaultValue) {
    return mEditor.putStringSet(key, defaultValue);
  }

  public int getInt(String key, int defaultValue) {
    return mSharedPreferences.getInt(key, defaultValue);
  }

  public float getFloat(String key, float defaultValue) {
    return mSharedPreferences.getFloat(key, defaultValue);
  }

  public long getLong(String key, long defaultValue) {
    return mSharedPreferences.getLong(key, defaultValue);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return mSharedPreferences.getBoolean(key, defaultValue);
  }

  public String getString(String key, String defaultValue) {
    return mSharedPreferences.getString(key, defaultValue);
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  public Set<String> getStringSet(String key, Set<String> defaultValue) {
    return mSharedPreferences.getStringSet(key, defaultValue);
  }

  public void commit() {
    mEditor.commit();
  }

  public void apply() {
    mEditor.apply();
  }

  public void clear() {
    mEditor.clear().commit();
  }
}