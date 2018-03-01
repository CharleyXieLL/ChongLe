package com.jiajia.badou.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.jiajia.badou.R;
import com.jiajia.presenter.impl.MvpView;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.util.StatusBarUtils;
import com.jiajia.badou.view.LoadingProgress;

/**
 * Created by Lei on 2018/1/26.
 * 基类
 */
public abstract class BaseActivity<P extends Presenter> extends AppCompatActivity
    implements MvpView {

  private Dialog dialog;

  protected Activity activity;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStatusBar();
    activity = this;
  }

  private LoadingProgress loadingProgress = null;

  private void createProgressDialog() {
    if (loadingProgress == null) {
      loadingProgress = new LoadingProgress();
    }
    dialog = loadingProgress.createDialog(BaseActivity.this);
    dialog.setOwnerActivity(BaseActivity.this);
    dialog.setCanceledOnTouchOutside(false);
    dialog.setCancelable(true);
  }

  public void showLoadingDialog(String text) {
    if (null == dialog) {
      createProgressDialog();
    }
    dialog.setCancelable(true);
    loadingProgress.setMessage(text);
    if (!dialog.isShowing()) {
      loadingProgress.onStartCircle();
      dialog.show();
    }
  }

  public void dismissLoadingDialog() {
    if (null != dialog && dialog.isShowing()) {
      loadingProgress.onStopCircle();
      dialog.dismiss();
      dialog = null;
    }
  }

  @Override public void onBackPressed() {
    if (processBackPressed()) return;
    super.onBackPressed();
  }

  /**
   * process the return back logic
   * return true if back pressed event has been consumed and should stay in current view
   */
  protected boolean processBackPressed() {
    return false;
  }

  private void setStatusBar() {
    setStatusColor(R.color.yq_white);
    StatusBarUtils.StatusBarLightMode(this);
  }

  private void setStatusColor(int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
      if (StatusBarUtils.FlymeSetStatusBarLightMode(this.getWindow(), true)
          || StatusBarUtils.FlymeSetStatusBarLightMode(this.getWindow(), true)
          || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(color));
      } else {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.yc_black));
      }
    }
  }

  public void setPresenter(P presenter) {
    if (this.presenter == null) {
      this.presenter = presenter;
      presenter.attachActivity(this);
      presenter.attachView(this);
      presenter.attachView(this);
    } else {
      throw new RuntimeException("Never call this method manually!!!");
    }
  }

  private P presenter;

  protected P getPresenter() {
    return presenter;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter = null;
  }
}
