package com.jiajia.badou.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.jiajia.badou.util.ChongLeConfig;
import com.jiajia.badou.util.ToastUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import java.io.File;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * Created by Lei on 2017/8/18.
 * 上传头像
 */

public class UpLoadAvatarView {

  public static final String TAKE_PHOTO = "take_photo";
  public static final String SELECT_IMAGE = "select_image";

  private Activity activity;
  private PhotoPicker photoPicker;
  private UpLoadAvatarViewCallBack upLoadAvatarViewCallBack;

  private SetUserHeadImagePopView setUserHeadImagePopView;

  private String clickType;

  private TakePhoto takePhoto;

  public UpLoadAvatarView(Activity activity, TakePhoto takePhoto) {
    this.activity = activity;
    this.takePhoto = takePhoto;
    createView();
  }

  private void createView() {
    if (setUserHeadImagePopView == null) {
      setUserHeadImagePopView = new SetUserHeadImagePopView(activity);
      setUserHeadImagePopView.setSetHeadImageViewCallBack(
          new SetUserHeadImagePopView.SetHeadImageViewCallBack() {
            @Override public void setHeadImgTake() {
              clickType = TAKE_PHOTO;
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //EasyPermissions.requestPermissions(activity, "", YCJTConfig.TAKE_PHOTO_CODE,
                //    Manifest.permission.CAMERA);
                PermissionGen.needPermission(activity, ChongLeConfig.TAKE_PHOTO_CODE, new String[] {
                    Manifest.permission.CAMERA
                });
              } else {
                takePhoto(takePhoto);
                //photoPicker.pickFromCamera();
              }
            }

            @Override public void setHeadImgImg() {
              clickType = SELECT_IMAGE;
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //EasyPermissions.requestPermissions(activity, "", YCJTConfig.TAKE_PHOTO_CODE,
                //    Manifest.permission.CAMERA);
                PermissionGen.needPermission(activity, ChongLeConfig.TAKE_PHOTO_CODE, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
              } else {
                photoPicker.pickFromGallery();
              }
            }

            @Override public void setHeadImgCancel() {
              setUserHeadImagePopView.dismissPop();
            }
          });
    }

    if (photoPicker == null) {
      photoPicker = new PhotoPicker(activity);
      photoPicker.setTakeListener(new PhotoPicker.TakeListener() {
        @Override public void onTakeSuccess(final String path) {
          upLoadAvatarViewCallBack.onTakeSuccess(path);
        }

        @Override public void onTakeFailure(String msg) {
          ToastUtil.showToast(activity, msg, false);
        }
      });
    }
  }

  private void takePhoto(TakePhoto takePhoto) {
    File file = new File(Environment.getExternalStorageDirectory(),
        "/temp/" + System.currentTimeMillis() + ".jpg");
    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
    Uri imageUri = Uri.fromFile(file);
    configCompress(takePhoto);
    configTakePhotoOption(takePhoto);
    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
  }

  private void configTakePhotoOption(TakePhoto takePhoto) {
    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
    builder.setWithOwnGallery(true);
    builder.setCorrectImage(true);
    takePhoto.setTakePhotoOptions(builder.create());
  }

  private void configCompress(TakePhoto takePhoto) {
    takePhoto.onEnableCompress(null, false);
  }

  private CropOptions getCropOptions() {
    WindowManager manager = activity.getWindowManager();
    DisplayMetrics outMetrics = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(outMetrics);
    int width = outMetrics.widthPixels;
    CropOptions.Builder builder = new CropOptions.Builder();
    builder.setOutputX(width).setOutputY(width);
    builder.setWithOwnCrop(false);
    return builder.create();
  }

  public void showPopView() {
    if (setUserHeadImagePopView != null) {
      setUserHeadImagePopView.showPop();
    }
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (clickType.equals(SELECT_IMAGE)) {
      photoPicker.handleActivityResult(requestCode, resultCode, data);
    }
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    PermissionGen.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
  }

  public void doSomething() {
    if (clickType.equals(TAKE_PHOTO)) {
      takePhoto(takePhoto);
      //photoPicker.pickFromCamera();
    } else {
      photoPicker.pickFromGallery();
    }
  }

  public interface UpLoadAvatarViewCallBack {
    void onTakeSuccess(String path);
  }

  public void setUpLoadAvatarViewCallBack(UpLoadAvatarViewCallBack upLoadAvatarViewCallBack) {
    this.upLoadAvatarViewCallBack = upLoadAvatarViewCallBack;
  }
}