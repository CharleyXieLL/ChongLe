package com.jiajia.badou.util;

import android.net.Uri;
import android.os.Environment;
import com.jiajia.badou.MainApplication;
import java.io.File;

/**
 * Created by chenwei on 2017/6/19.
 */
//统一管理应用外部文件夹
public class FileHelper {

  private static FileHelper instance;

  private String baseDir; //应用数据目录

  private String cacheDir; //应用数据缓存目录，.cache (隐藏目录)，清除数据不会对用户产生影响的

  public static FileHelper getInstance() {
    if (instance == null) {
      synchronized (FileHelper.class) {
        if (instance == null) {
          instance = new FileHelper();
        }
      }
    }
    return instance;
  }

  private FileHelper() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      File baseFile = new File(Environment.getExternalStorageDirectory(),
          MainApplication.getInstance().getPackageName());
      if (!baseFile.exists()) {
        baseFile.mkdirs();
      }
      baseDir = baseFile.getPath();
      createCacheDir(baseDir);
    } else {
      baseDir = MainApplication.getInstance().getCacheDir().getPath();
      createCacheDir(baseDir);
    }
  }

  public String getBaseDir() {
    return baseDir;
  }

  public String getCacheDir() {
    return cacheDir;
  }

  private void createCacheDir(String baseDir) {
    File cache = new File(baseDir, ".cache");
    if (!cache.exists()) cache.mkdirs();
    cacheDir = cache.getPath();
  }

  /**
   * 拍照地址
   */
  public Uri getTakePhotoUri() {
    File cameraDir = new File(baseDir, "Camera");
    if (!cameraDir.exists()) {
      cameraDir.mkdirs();
    }
    File mediaFile = new File(cameraDir, "IMG_" + "20180226" + ".jpg");

    return Uri.fromFile(mediaFile);
  }

  /**
   * 相片地址
   */
  public Uri getImageUri() {

    File cameraDir = new File(baseDir, "Camera");
    if (!cameraDir.exists()) {
      cameraDir.mkdirs();
    }
    File mediaFile = new File(cameraDir, "IMG_" + System.currentTimeMillis() + ".jpg");

    return Uri.fromFile(mediaFile);
  }

  public String getDownloadDir() {
    File file = new File(baseDir, "Download");
    if (!file.exists()) {
      file.mkdirs();
    }
    return file.getPath();
  }

  public String getTempDir() {
    File file = new File(cacheDir, "temp");
    if (!file.exists()) {
      file.mkdirs();
    }
    return file.getPath();
  }
}
