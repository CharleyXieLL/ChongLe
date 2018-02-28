package com.jiajia.badou.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by chenwei on 15/11/13.
 * 图片读取类
 */
public class BitmapReader {

  public static Bitmap readBitmapByKB(String filePath, int maxKB) {
    return readBitmap(filePath, maxKB, 0, 0);
  }

  public static Bitmap readBitmapBySize(String filePath, int width, int height) {
    return readBitmap(filePath, null, width, height);
  }

  /**
   * 根据maxKB对图片质量进行压缩  （压缩图片质量）
   */
  public static Bitmap readBitmap(String filePath, int maxKB, int width, int height) {
    Bitmap bitmap = readBitmap(filePath, null, width, height);
    if (bitmap == null) return null;
    if (maxKB <= 0) return bitmap;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int options = 100;
    do {
      baos.reset();
      bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
      options -= 20;
    } while (options >= 60 && baos.size() / 1024 > maxKB);
    try {
      baos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  /**
   * 根据长宽调整inSampleSize对图片进行大小压缩  （压缩图片内存占用）
   */
  public static Bitmap readBitmap(String filePath, BitmapFactory.Options options, int width,
      int height) {
    if (TextUtils.isEmpty(filePath)) return null;
    if (options == null) options = new BitmapFactory.Options();

    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filePath, options); //此时返回bm为空
    options.inJustDecodeBounds = false;
    //计算缩放比
    if (width * height <= 0) {
      options.inSampleSize = Math.min(computeSampleSize(options, -1, 1080 * 1920), 2);
    } else {
      options.inSampleSize = computeSampleSize(options, -1, width * height);
    }

    options.inPreferredConfig = Bitmap.Config.RGB_565;
    options.inDither = false;
    options.inPurgeable = true;
    options.inInputShareable = true;

    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

    //Bitmap转正
    bitmap = resetRotateBitmap(filePath, bitmap);
    return bitmap;
  }

  public static Bitmap decodeBitmap(Context context, @DrawableRes int resId, int width,
      int height) {
    BitmapFactory.Options op = new BitmapFactory.Options();
    op.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(context.getResources(), resId, op); //获取尺寸信息
    op.inJustDecodeBounds = false;
    op.inSampleSize = Math.min(4, computeSampleSize(op, -1, width * height));
    op.inPreferredConfig = Bitmap.Config.RGB_565;
    op.inDither = false;
    op.inPurgeable = true;
    op.inInputShareable = true;

    try {
      return BitmapFactory.decodeResource(context.getResources(), resId, op);
    } catch (OutOfMemoryError error) {
      return null;
    }
  }

  private static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
    int roundedSize;
    if (initialSize <= 8) {
      roundedSize = 1;
      while (roundedSize < initialSize) {
        roundedSize <<= 1;
      }
    } else {
      roundedSize = 8;
    }
    return roundedSize;
  }

  private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    double w = options.outWidth;
    double h = options.outHeight;
    int lowerBound =
        (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
    int upperBound = (minSideLength == -1) ? 32
        : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
    if (upperBound < lowerBound) {
      return lowerBound;
    }
    if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
      return 1;
    } else if (minSideLength == -1) {
      return lowerBound;
    } else {
      return upperBound;
    }
  }

  /**
   * 三星的一些手机拍摄的图片角度不对
   */
  private static Bitmap resetRotateBitmap(String filePath, Bitmap orgBitmap) {
    if (orgBitmap == null) {
      return null;
    }

    int degree = readPictureDegree(filePath);
    if (degree == 0) {
      return orgBitmap;
    }

    Matrix mtx = new Matrix();
    mtx.postRotate(degree);
    Bitmap bm =
        Bitmap.createBitmap(orgBitmap, 0, 0, orgBitmap.getWidth(), orgBitmap.getHeight(), mtx,
            true);

    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    } finally {
      try {
        if (baos != null) {
          baos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return bm;
  }

  private static int readPictureDegree(String path) {
    int degree = 0;
    try {
      ExifInterface exifInterface = new ExifInterface(path);
      int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_NORMAL);
      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return degree;
  }
}
