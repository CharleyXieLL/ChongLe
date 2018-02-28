package com.jiajia.badou.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by chenwei on 2017/6/20.
 */

public class FileUtil {

  public static long getFileSize(String filePath) {
    long size;
    if (TextUtils.isEmpty(filePath)) return 0;
    try {
      if (!new File(filePath).exists()) {
        return 0;
      }
      FileInputStream fis = new FileInputStream(filePath);
      size = fis.available();
      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
    return size;
  }

  public static boolean isFileExist(String filePath) {
    if (TextUtils.isEmpty(filePath)) return false;
    return new File(filePath).exists();
  }

  /**
   * 删除文件或者文件夹
   */
  public static boolean deleteFile(String fileName) {
    if (fileName != null && fileName.length() > 0) {
      File file = new File(fileName);
      if (file.exists()) {
        return file.delete();
      }
    }
    return false;
  }

  public static String getFilePathFromUri(Context context, Uri uri) {
    if (uri == null) return "";
    String filePath = uri.getPath();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(
        context.getApplicationContext(), uri)) {
      String wholeID = DocumentsContract.getDocumentId(uri);
      String[] strings = wholeID.split(":");
      String id = strings.length > 0 ? strings[1] : "";
      String[] column = { MediaStore.Images.Media.DATA };
      String sel = MediaStore.Images.Media._ID + "=?";
      Cursor cursor = context.getContentResolver()
          .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[] { id },
              null);
      int columnIndex = cursor.getColumnIndex(column[0]);
      if (cursor.moveToFirst()) {
        filePath = cursor.getString(columnIndex);
      }
      cursor.close();
    } else {
      String[] filePathColumn = { MediaStore.Images.Media.DATA };
      Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
      if (cursor != null) {
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        filePath = cursor.getString(column_index);

        cursor.close();
      }
    }
    return filePath;
  }
}
