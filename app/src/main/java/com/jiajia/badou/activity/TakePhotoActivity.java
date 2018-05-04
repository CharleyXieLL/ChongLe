package com.jiajia.badou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import com.jiajia.presenter.util.ToastUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

/**
 * Created by Lei on 2018/2/28.
 */
public abstract class TakePhotoActivity extends BaseActivity
    implements TakePhoto.TakeResultListener, InvokeListener {

  private static final String TAG = TakePhotoActivity.class.getName();
  private TakePhoto takePhoto;
  private InvokeParam invokeParam;

  @Override protected void onCreate(Bundle savedInstanceState) {
    getTakePhoto().onCreate(savedInstanceState);
    super.onCreate(savedInstanceState);
  }

  @Override protected int onCreateViewTitleId() {
    return onCreateViewTitle();
  }

  @Override protected int onCreateViewId() {
    return onCreateView();
  }

  @Override protected void init() {
    initUi();
  }

  protected abstract @StringRes int onCreateViewTitle();

  protected abstract @LayoutRes int onCreateView();

  protected abstract void initUi();

  @Override protected void onSaveInstanceState(Bundle outState) {
    getTakePhoto().onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    getTakePhoto().onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.TPermissionType type =
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
  }

  /**
   * 获取TakePhoto实例
   */
  public TakePhoto getTakePhoto() {
    if (takePhoto == null) {
      takePhoto =
          (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
    }
    return takePhoto;
  }

  @Override public void takeSuccess(TResult result) {

  }

  @Override public void takeFail(TResult result, String msg) {
    ToastUtil.showToast(getApplicationContext(), msg, false);
  }

  @Override public void takeCancel() {
  }

  @Override public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
    PermissionManager.TPermissionType type =
        PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
    if (PermissionManager.TPermissionType.WAIT.equals(type)) {
      this.invokeParam = invokeParam;
    }
    return type;
  }

  @Override public void getFailed(String msg, String code) {

  }
}
