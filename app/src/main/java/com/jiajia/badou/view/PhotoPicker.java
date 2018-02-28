package com.jiajia.badou.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.jiajia.badou.bitmap.BitmapReader;
import com.jiajia.badou.bitmap.BitmapSaver;
import com.jiajia.badou.util.FileHelper;
import com.jiajia.badou.util.FileUtil;
import com.jiajia.badou.util.SubscriberExtend;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lei on 2017/6/20.
 */

public class PhotoPicker {

  public static final int REQUEST_CODE_PICK_CAMERA = 113;
  private static final int REQUEST_CODE_PICK_GALLERY = 114;

  private TakeListener takeListener;

  private String path;

  private Activity activity;

  private String imageType;

  public PhotoPicker(Activity activity) {
    this.activity = activity;
  }

  /**
   * 打开系统相机
   */
  public void pickFromCamera() {
    imageType = UpLoadAvatarView.TAKE_PHOTO;
    Uri uri = FileHelper.getInstance().getImageUri();
    path = uri.getPath();
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    takePictureIntent.setType("image/*");
    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);// 该照片的绝对路径
    takePictureIntent.putExtra("return-data", true);
    takePictureIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    takePictureIntent.addFlags(
        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
      activity.startActivityForResult(takePictureIntent, REQUEST_CODE_PICK_CAMERA);
    }
  }

  /**
   * 从系统相册选取
   */
  public void pickFromGallery() {
    imageType = UpLoadAvatarView.SELECT_IMAGE;
    Uri uri = FileHelper.getInstance().getImageUri();
    path = uri.getPath();
    Intent intent;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Intent intenttemp = new Intent();
      intenttemp.setType("image/*");
      intenttemp.setAction(Intent.ACTION_GET_CONTENT);
      intent = intenttemp.createChooser(intenttemp, "请选择图片");
      intent.putExtra("return-data", true);
    } else {
      intent = new Intent(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
    }
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    activity.startActivityForResult(intent, REQUEST_CODE_PICK_GALLERY);
  }

  public void handleActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case REQUEST_CODE_PICK_CAMERA:
        case REQUEST_CODE_PICK_GALLERY: {
          String filePath =
              FileUtil.getFilePathFromUri(activity, data != null ? data.getData() : null);
          if (FileUtil.isFileExist(filePath)) {
            handleFile(filePath);
            return;
          }
          if (FileUtil.isFileExist(path)) {
            handleFile(path);
            return;
          }
          if (takeListener != null) {
            takeListener.onTakeFailure("找不到图片");
          }
        }
        break;
      }
    }
  }

  /**
   * 处理图片
   */
  private void handleFile(final String path) {
    Observable.create(new Observable.OnSubscribe<String>() {
      @Override public void call(Subscriber<? super String> subscriber) {
        Bitmap bitmapPhoto = BitmapReader.readBitmap(path, 100, 300, 300); //质量最大512kb
        String pictureDir = BitmapSaver.create()
            .saveBitmap(FileHelper.getInstance().getTempDir(), "thumbnail.jpg", bitmapPhoto);
        if (bitmapPhoto != null) {
          bitmapPhoto.recycle();
        }
        if (TextUtils.isEmpty(pictureDir)) {
          pictureDir = path;
        }
        subscriber.onNext(pictureDir);
        subscriber.onCompleted();
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SubscriberExtend<String>() {
          @Override public void onNext(String s) {
            if (takeListener != null) {
              takeListener.onTakeSuccess(s);
            }
          }
        });
  }

  public void setTakeListener(TakeListener takeListener) {
    this.takeListener = takeListener;
  }

  public interface TakeListener {
    void onTakeSuccess(String path);

    void onTakeFailure(String msg);
  }
}
