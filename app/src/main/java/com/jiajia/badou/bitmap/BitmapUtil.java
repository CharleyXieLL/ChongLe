package com.jiajia.badou.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.view.View;

public class BitmapUtil {

  //（等比缩放）
  public static Bitmap extractThumbnail(Bitmap bitmap, int width, int height) {
    return ThumbnailUtils.extractThumbnail(bitmap, width, height);
  }

  public static Bitmap scaleImage(Bitmap bm, float scale) {
    if (bm == null || bm.isRecycled()) return bm;
    int width = bm.getWidth();
    int height = bm.getHeight();
    Matrix matrix = new Matrix();
    matrix.postScale(scale, scale);
    return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
  }

  public static Bitmap Bytes2Bitmap(byte[] b) {
    if (b != null && b.length != 0) {
      return BitmapFactory.decodeByteArray(b, 0, b.length);
    } else {
      return null;
    }
  }

  /**
   * 获取bitmap的大小
   *
   * @return int
   */
  public static int getBitmapSize(Bitmap bitmap) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {               //API 19
      return bitmap.getAllocationByteCount();
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {   //API 12
      return bitmap.getByteCount();
    }

    return bitmap.getRowBytes() * bitmap.getHeight();                       //earlier version
  }

  /**
   * 根据图片大小决定存放时缩小比例
   * <p/>
   * 最后默默发现测试时quality和最后图片大小关系略复杂，不是正常意义上的压缩率
   */
  public static int zoomBitmap(Bitmap bitmap, int maxSize) {
    if (maxSize < 1) return 100;
    int currSize = BitmapUtil.getBitmapSize(bitmap);
    if (currSize <= maxSize) return 100;
    int quality = 100 * maxSize / currSize;
    // 90的质量测试发现可以压缩为原来的40%~70%
    return quality > 30 ? 90 : 50;
  }

  public static Bitmap view2Bitmap(final View view, int width, int height) {
    view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED));
    view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    if (view.getMeasuredHeight() == 0 || view.getMeasuredWidth() == 0) {
      return null;
    }
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap cacheBmp = view.getDrawingCache();
    if (cacheBmp == null) return null;
    Bitmap bitmap = cacheBmp.copy(cacheBmp.getConfig(), false);
    view.setDrawingCacheEnabled(false);

    return bitmap;
  }


  /**
   * drawable转bitmap
   */
  public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable == null) return null;

    Bitmap bitmap;
    int w = drawable.getIntrinsicWidth();
    int h = drawable.getIntrinsicHeight();
    if (drawable instanceof BitmapDrawable) {
      bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(), w, h, false);
    } else {
      bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
    }

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, w, h);
    drawable.draw(canvas);
    return bitmap;
  }
}
