package com.jiajia.badou.util;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by xielei 2017/09/27.
 */
public class ProgressWebChromeClient extends WebChromeClient {
  private int currProgress = -1;
  private Handler mHandler = new Handler();
  private ProgressBar progressbar;

  public ProgressWebChromeClient(WebView webView, ProgressBar progressbar) {
    this.progressbar = progressbar;
    this.mWebView = webView;
  }

  private Runnable runnable = new Runnable() {
    public void run() {
      if (currProgress < 80) {
        progressbar.setProgress(currProgress);
        currProgress++;
        mHandler.postDelayed(this, currProgress < 20 ? 40 : 2 * currProgress);
      } else {
        if (currProgress == 100) {
          AlphaAnimation animation = new AlphaAnimation(1, 0);
          animation.setDuration(250);
          animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
              progressbar.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
          });
          progressbar.startAnimation(animation);
        } else {
          mHandler.postDelayed(this, 10);
        }
      }
    }
  };

  @Override public void onProgressChanged(WebView view, int newProgress) {
    if (currProgress < 0) {
      currProgress = 0;
      if (progressbar.getVisibility() == View.GONE
          || progressbar.getVisibility() == View.INVISIBLE) {
        progressbar.setVisibility(View.VISIBLE);
      }
      mHandler.postDelayed(runnable, 200);
    }
    currProgress = 100 - (int) ((100 - currProgress) * (1.0 - newProgress / 100.0));
    progressbar.setProgress(currProgress);
    super.onProgressChanged(view, newProgress);
  }

  private View myView = null;
  private WebView mWebView;

  @Override
  public void onShowCustomView(View view, CustomViewCallback callback) {
    ViewGroup parent = (ViewGroup) mWebView.getParent();
    parent.removeView(mWebView);
    parent.addView(view);
    myView = view;
    myView.setBackgroundColor(Color.BLACK);
  }

  @Override public void onHideCustomView() {
    if (myView != null) {
      mHandler.removeCallbacksAndMessages(null);
      ViewGroup parent = (ViewGroup) myView.getParent();
      parent.removeView(myView);
      parent.addView(mWebView);
      myView = null;
    }
  }

  @Override public boolean onShowFileChooser(WebView webView,
      ValueCallback<Uri[]> filePathCallback,
      FileChooserParams fileChooserParams) {
    progressWebChromeClientCallBack.onShowFileChooser(filePathCallback);
    return true;
  }

  public void getData(String img) {
    Log.i("SSS", img);
  }

  /**
   * <3.0
   */
  public void openFileChooser(ValueCallback<Uri> uploadMsg) {
    progressWebChromeClientCallBack.openFileChooser(uploadMsg);
  }

  /**
   * >3.0+
   */
  public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
    progressWebChromeClientCallBack.openFileChooser(uploadMsg);
  }

  /**
   * >4.1.1
   */
  public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
    progressWebChromeClientCallBack.openFileChooser(uploadMsg);
  }

  @Override public void onReceivedTitle(WebView view, final String title) {
    super.onReceivedTitle(view, title);
    progressWebChromeClientCallBack.onReceivedTitle(view, title);
  }

  private ProgressWebChromeClientCallBack progressWebChromeClientCallBack;

  public interface ProgressWebChromeClientCallBack {
    void onShowFileChooser(ValueCallback<Uri[]> filePathCallback);

    void openFileChooser(ValueCallback<Uri> uploadMsg);

    void onReceivedTitle(WebView view, final String title);
  }

  public void setProgressWebChromeClientCallBack(
      ProgressWebChromeClientCallBack progressWebChromeClientCallBack) {
    this.progressWebChromeClientCallBack = progressWebChromeClientCallBack;
  }
}
