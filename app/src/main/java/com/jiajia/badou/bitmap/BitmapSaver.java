package com.jiajia.badou.bitmap;

import android.graphics.Bitmap;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chenwei on 16/12/15.
 */

public class BitmapSaver {

  public static BitmapSaver create() {
    return new BitmapSaver();
  }

  /**
   * 指定最大存放大小为maxKB（KB）
   */
  public String saveBitmap(String dir, final String name, final Bitmap bitmap, final int maxSize) {
    File dirFile = new File(dir);
    if (!dirFile.exists()) {
      dirFile.mkdirs();
    }
    String path = dir + File.separator + name;
    return saveBitmap(path, bitmap, maxSize, true);
  }

  public String saveBitmap(String dir, final String name, final Bitmap bitmap) {
    return saveBitmap(dir, name, bitmap, 0);
  }

  /**
   * @param path
   * @param bitmap
   * @param maxSize
   * @param override 是否覆盖原先图片
   * @return
   */
  public String saveBitmap(String path, final Bitmap bitmap, final int maxSize, boolean override) {
    if (bitmap == null) return "";
    File file = new File(path);
    if (override) {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      if (file.exists()) {
        return file.getPath();
      }
    }
    try {
      int options = BitmapUtil.zoomBitmap(bitmap, maxSize * 1024);
      if (options == 100) {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();
      } else {
        compressBitmapToFile(bitmap, file, maxSize, options);
      }
      return file.getPath();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 压缩不改变像素大小，所以读取出来的bitmap大小还是原来的，但是可以节省空间，读取加快
   */
  private void compressBitmapToFile(Bitmap bmp, File file, int maxSize, int initOptions) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int options = initOptions;
    bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
    // 为防止失真严重，理论上压缩不小于50%
    while (options > 50 && baos.toByteArray().length / 1024 > maxSize) {
      baos.reset();
      options -= 20;
      bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
    }
    try {
      BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
      fos.write(baos.toByteArray());
      fos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
