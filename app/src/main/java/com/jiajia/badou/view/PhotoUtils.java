package com.jiajia.badou.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import com.bumptech.glide.Glide;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class PhotoUtils {

  public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
  public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
  public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
  public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

  //static final String TAG = "PhotoIntentUtils";

  public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
    FileInputStream fis = null;
    Bitmap bitmap = null;
    try {
      File file = new File(filePath);
      fis = new FileInputStream(file);
      bitmap = BitmapFactory.decodeStream(fis, null, opts);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (OutOfMemoryError e) {
      e.printStackTrace();
    } finally {
      try {
        fis.close();
      } catch (Exception e) {
      }
    }
    return bitmap;
  }

  public static Bitmap getBitmapByPath(String imgUrl) {
    Bitmap bitmap = null;
    InputStream in = null;
    BufferedOutputStream out = null;
    try {
      in = new BufferedInputStream(new URL(imgUrl).openStream(), 1024);
      final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
      out = new BufferedOutputStream(dataStream, 1024);
      copy(in, out);
      out.flush();
      byte[] data = dataStream.toByteArray();
      bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
      data = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  private static void copy(InputStream in, OutputStream out) throws IOException {
    byte[] b = new byte[1024];
    int read;
    while ((read = in.read(b)) != -1) {
      out.write(b, 0, read);
    }
  }

  public static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
    int roundedSize;
    if (initialSize <= 8) {
      roundedSize = 1;
      while (roundedSize < initialSize) {
        roundedSize <<= 1;
      }
    } else {
      roundedSize = (initialSize + 7) / 8 * 8;
    }
    return roundedSize;
  }

  private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
      int maxNumOfPixels) {
    double w = options.outWidth;
    double h = options.outHeight;

    int lowerBound =
        (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
    int upperBound = (minSideLength == -1) ? 128
        : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

    if (upperBound < lowerBound) {
      // return the larger one when there is no overlapping zone.
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
   * 根据图片地址,按图片大小压缩
   *
   * @Date 2015年2月28日
   */
  public static Bitmap thumbnail(String srcPath, float width, float height) {
    if (null == srcPath || srcPath.length() == 0) return null;
    File file = new File(srcPath);
    if (file.exists()) {
      BitmapFactory.Options newOpts = new BitmapFactory.Options();
      newOpts.inJustDecodeBounds = false;
      // 计算图片缩放比例

      final int minSideLength = Math.min((int) width, (int) height);
      newOpts.inSampleSize = computeSampleSize(newOpts, minSideLength,
          (int) (width * height));//width，hight设为原来的几分一,即图片为原来的几分之一

      newOpts.inInputShareable = true;
      newOpts.inPurgeable = true;
      newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//16位
      Bitmap bitmap = getBitmapByPath(srcPath, newOpts);//BitmapFactory.decodeFile(srcPath,newOpts);
      return thumbnail(bitmap, width, height);
    }
    return null;
  }

  /***
   * 根据图片大小压缩图片
   * @Date 2015年2月28日
   * @param srcBitmap
   * @param width
   * @param height
   * @return
   */
  public static Bitmap thumbnail(Bitmap srcBitmap, float width, float height) {
    if (srcBitmap == null) {
      return null;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    if (baos.toByteArray().length / 1024 > 1024) {
      baos.reset();
      srcBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
    }
    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
    BitmapFactory.Options newOpts = new BitmapFactory.Options();
    newOpts.inJustDecodeBounds = true;
    newOpts.inInputShareable = true;
    newOpts.inPurgeable = true;
    //图片压缩质量参数
    newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
    newOpts.inJustDecodeBounds = false;
    int w = newOpts.outWidth;
    int h = newOpts.outHeight;
    if (width <= 0) width = 480f;
    if (height <= 0) height = 800f;
    int be = 1;
    if (w > h && w > width) {
      be = (int) (newOpts.outWidth / width);
    } else if (w < h && h > height) {
      be = (int) (newOpts.outHeight / height);
    }
    if (be <= 0) {
      be = 1;
    }
    newOpts.inSampleSize = be;
    isBm = new ByteArrayInputStream(baos.toByteArray());
    try {
      bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
    } catch (Exception e) {
      return null;
    }
    return bitmap;
  }

  public static Bitmap getBitmapByBytes(Context contexts, String filePath) {
    Bitmap srcBitmap = thumbnail(filePath);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    if (baos.toByteArray().length / 1024 > 1024) {
      baos.reset();
      srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    }
    BitmapFactory.Options newOpts = new BitmapFactory.Options();
    newOpts.inJustDecodeBounds = true;
    newOpts.inInputShareable = true;
    newOpts.inPurgeable = true;
    //图片压缩质量参数
    newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
    newOpts.inJustDecodeBounds = false;
    float w = newOpts.outWidth;
    float h = newOpts.outHeight;
    Bitmap bitmap = null;
    float yNew = 0;
    int wFault = 0;
    if (w > h) {
      wFault = 1200;
      yNew = (h / w) * 1200;
    }
    if (w == h) {
      wFault = 1000;
      yNew = 1000;
    }
    if (w < h) {
      wFault = 750;
      yNew = (h / w) * 750;
    }
    try {
      bitmap = Glide.with(contexts)
          .load(filePath)
          .asBitmap()
          .centerCrop()
          .into(wFault, (int) yNew)
          .get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  /**
   * 根据图片质量压缩图片
   *
   * @param imageSize 压缩大小(单位:kb)
   * @Date 2015年2月28日
   */
  public static Bitmap compressImage(Bitmap image, int imageSize) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
    return bitmap;
  }

  /**
   * 根据图片地址,按图片大小压缩
   *
   * @Date 2015年2月28日
   */
  public static Bitmap thumbnail(String srcPath) {
    if (null == srcPath || srcPath.length() == 0) return null;
    File file = new File(srcPath);
    if (file.exists()) {
      BitmapFactory.Options newOpts = new BitmapFactory.Options();
      newOpts.inJustDecodeBounds = false;
      // 计算图片缩放比例
      newOpts.inSampleSize = 8;//width，hight设为原来的几分一,即图片为原来的几分之一
      newOpts.inInputShareable = true;
      newOpts.inPurgeable = true;
      newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//16位
      Bitmap bitmap = getBitmapByPath(srcPath, newOpts);//BitmapFactory.decodeFile(srcPath,newOpts);
      return bitmap;
    }
    return null;
  }

  /**
   * @param filePath 路径
   * @param fileName 文件名称
   * @return String
   * @Description <pre>保存bitmap 到文件</pre>
   */
  public static String saveBitmapNew(Context context, String filePathJpg, String filePath,
      String fileName) {
    File f = new File(filePath, fileName);
    if (f.exists()) {
      f.delete();
    }
    FileOutputStream out = null;
    Bitmap thumbnail = null;
    try {
      out = new FileOutputStream(f);
      thumbnail = getBitmapByBytes(context, filePathJpg);
      thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
      out.flush();
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
        }
      }
      if (null != thumbnail) {
        thumbnail.recycle();
      }
    }
    return f.getAbsolutePath();
  }

  /**
   * @param filePath 路径
   * @param fileName 文件名称
   * @return String
   * @Description <pre>保存bitmap 到文件</pre>
   */
  public static String saveBitmapNew(Bitmap bitmap, String filePath, String fileName) {
    File f = new File(filePath, fileName);
    if (f.exists()) {
      f.delete();
    }
    FileOutputStream out = null;
    Bitmap thumbnail = null;
    try {
      out = new FileOutputStream(f);
      thumbnail = bitmap;
      thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != thumbnail) {
        thumbnail.recycle();
      }
    }
    return f.getAbsolutePath();
  }

  public static File getImageFile(String imgName, Activity bActivity) {
    String state = Environment.getExternalStorageState();
    if (state.equals(Environment.MEDIA_MOUNTED)) {
      File file = new File(bActivity.getExternalCacheDir(), imgName);
      if (!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
        }
      } else if (file.exists()) {
        file.delete();
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return file;
    } else {
      File file = new File(bActivity.getCacheDir(), imgName);
      if (!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return file;
    }
  }

  /**
   * 获取文件指定文件的指定单位的大小
   *
   * @param filePath 文件路径
   * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
   * @return double值的大小
   */
  public static double getFileOrFilesSize(String filePath, int sizeType) {
    File file = new File(filePath);
    long blockSize = 0;
    try {
      if (file.isDirectory()) {
        blockSize = getFileSizes(file);
      } else {
        blockSize = getFileSize(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return FormatFileSize(blockSize, sizeType);
  }

  /**
   * 调用此方法自动计算指定文件或指定文件夹的大小
   *
   * @param filePath 文件路径
   * @return 计算好的带B、KB、MB、GB的字符串
   */
  public static String getAutoFileOrFilesSize(String filePath) {
    File file = new File(filePath);
    long blockSize = 0;
    try {
      if (file.isDirectory()) {
        blockSize = getFileSizes(file);
      } else {
        blockSize = getFileSize(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return FormatFileSize(blockSize);
  }

  /**
   * 获取指定文件大小
   *
   * @throws Exception
   */
  private static long getFileSize(File file) throws Exception {
    long size = 0;
    if (file.exists()) {
      FileInputStream fis = null;
      fis = new FileInputStream(file);
      size = fis.available();
    } else {
      file.createNewFile();
    }
    return size;
  }

  /**
   * 获取指定文件夹
   *
   * @throws Exception
   */
  private static long getFileSizes(File f) throws Exception {
    long size = 0;
    File flist[] = f.listFiles();
    for (int i = 0; i < flist.length; i++) {
      if (flist[i].isDirectory()) {
        size = size + getFileSizes(flist[i]);
      } else {
        size = size + getFileSize(flist[i]);
      }
    }
    return size;
  }

  /**
   * 转换文件大小
   */
  private static String FormatFileSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    String wrongSize = "0B";
    if (fileS == 0) {
      return wrongSize;
    }
    if (fileS < 1024) {
      fileSizeString = df.format((double) fileS) + "B";
    } else if (fileS < 1048576) {
      fileSizeString = df.format((double) fileS / 1024) + "KB";
    } else if (fileS < 1073741824) {
      fileSizeString = df.format((double) fileS / 1048576) + "MB";
    } else {
      fileSizeString = df.format((double) fileS / 1073741824) + "GB";
    }
    return fileSizeString;
  }

  /**
   * 转换文件大小,指定转换的类型
   */
  private static double FormatFileSize(long fileS, int sizeType) {
    DecimalFormat df = new DecimalFormat("#.00");
    double fileSizeLong = 0;
    switch (sizeType) {
      case SIZETYPE_B:
        fileSizeLong = Double.valueOf(df.format((double) fileS));
        break;
      case SIZETYPE_KB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
        break;
      case SIZETYPE_MB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
        break;
      case SIZETYPE_GB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
        break;
      default:
        break;
    }
    return fileSizeLong;
  }

  /**
   * 获取文件的byte
   *
   * @throws IOException
   */
  public static byte[] toByteArray(String filePath, String fileName) throws IOException {
    FileOutputStream out;
    try {
      File f = new File(filePath, fileName);
      if (f.exists()) {
        f.delete();
      }
      out = new FileOutputStream(f);
      byte b[] = new byte[(int) f.length()];     //创建合适文件大小的数组
      out.write(b);    //读取文件中的内容到b[]数组
      out.close();
      return b;
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * 将文件转为byte[]
   *
   * @param filePath 文件路径
   */
  public static byte[] getBytes(String filePath) {
    File file = new File(filePath);
    ByteArrayOutputStream out = null;
    try {
      FileInputStream in = new FileInputStream(file);
      out = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      while (in.read(b) != -1) {
        out.write(b, 0, b.length);
      }
      out.close();
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert out != null;
    return out.toByteArray();
  }

  /**
   * base64字符串转文件
   */
  public static String base64ToFile(Activity activity, String base64) {
    File file;
    String fileName = System.currentTimeMillis() + ".jpg";
    FileOutputStream out = null;
    try {
      // 解码，然后将字节转换为文件
      file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
      if (!file.exists()) file.createNewFile();
      byte[] buffer = Base64.decode(base64, Base64.DEFAULT);
      out = new FileOutputStream(
          activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + fileName);
      out.write(buffer);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + fileName;
  }
}