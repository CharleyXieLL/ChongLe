package com.jiajia.badou.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jiajia.badou.R;
import com.jiajia.badou.util.ProgressWebChromeClient;
import com.jiajia.badou.util.StatusBarUtils;
import com.jiajia.badou.util.Strings;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/1/9.
 * 通用化网页
 */
public class CommonWebViewActivity extends BaseActivity {

  public static final String TAG = "CommonWebView";
  public static final String TITLE = "title";
  public static final String URL = "url";
  private static final String BASE_URL = "http://sys.apiins.com/api/app/listMsg?id=";

  private static final String DEFAULT_URL =
      "https://sys.apiins.com/?from=singlemessage&isappinstalled=0";

  private RelativeLayout layoutRoot;
  private TextView tvTitle;
  private ProgressBar progressBar;

  private String url;
  private WebView webView;

  private ProgressWebChromeClient progressWebChromeClient;

  private CookieManager cookieManager;

  private RelativeLayout layoutBack;
  private TextView tvClose;

  public static Intent callIntent(Context context, String url) {
    Intent intent = new Intent(context, CommonWebViewActivity.class);
    intent.putExtra(URL, url);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.api_common_webview);
    initUi();
    initData();
    initWebView();
    setStatusBar();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  private void initUi() {
    layoutRoot = findViewById(R.id.api_relat_title);
    tvTitle = findViewById(R.id.api_base_title);
    tvClose = findViewById(R.id.tv_base_title_close);
    progressBar = findViewById(R.id.common_webview_progressbar);
    layoutBack = findViewById(R.id.yq_base_back_arrow_iv);
    webView = findViewById(R.id.webview);
    webView.requestFocus();

    cookieManager = CookieManager.getInstance();

    layoutBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (webView.canGoBack()) {
          webView.goBack();
        } else {
          finish();
        }
      }
    });
    tvClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });
  }

  private void initData() {
    url = getIntent().getStringExtra(URL);
  }

  @SuppressLint("SetJavaScriptEnabled") private void initWebView() {
    WebSettings set = webView.getSettings();

    //解决webview跨域问题
    try {
      Class<?> clazz = webView.getSettings().getClass();
      Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
      if (method != null) {
        method.invoke(webView.getSettings(), true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //set.setAppCacheMaxSize(1024 * 1024 * 8);
    //set.setAppCacheEnabled(true);
    //set.setAllowFileAccess(true);
    //set.setAppCachePath(getCacheDir().getAbsolutePath());
    //set.setJavaScriptCanOpenWindowsAutomatically(true);
    //set.setRenderPriority(WebSettings.RenderPriority.HIGH);
    //set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

    //设置支持JS
    set.setJavaScriptEnabled(true);
    // 设置支持本地存储
    set.setDatabaseEnabled(true);
    //取得缓存路径
    String path = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
    //设置路径
    set.setDatabasePath(path);
    //设置支持DomStorage
    set.setDomStorageEnabled(true);
    //设置存储模式
    set.setCacheMode(WebSettings.LOAD_DEFAULT);
    //设置适应屏幕
    set.setUseWideViewPort(true);
    set.setLoadWithOverviewMode(true);
    set.setSupportZoom(true);
    set.setBuiltInZoomControls(true);
    set.setDisplayZoomControls(false);
    //设置缓存
    set.setAppCacheEnabled(true);
    //set.setUserAgentString("User-Agent:seeyonCordova");

    setWebChromeClient();

    webView.setDownloadListener(new DownloadListener() {
      @Override public void onDownloadStart(String url, String userAgent, String contentDisposition,
          String mimetype, long contentLength) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(viewIntent);
      }
    });

    webView.setWebViewClient(new WebViewClient() {

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

        // *** NEVER DO THIS!!! ***
        // super.onReceivedSslError(view, handler, error);

        // let's ignore ssl error
        handler.proceed();
      }

      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.endsWith(".apk")) {
          Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(viewIntent);
          return true;
        }
        if (url.startsWith("tel:")) {
          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(intent);
          return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
      }

      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {

      }

      @Override public void onPageFinished(WebView view, String url) {
        if (webView.canGoBack()) {
          tvClose.setVisibility(View.VISIBLE);
        } else {
          tvClose.setVisibility(View.GONE);
        }
      }
    });

    if (!Strings.isNullOrEmpty(url)) {
      webView.loadUrl(url);
    } else {
      webView.loadUrl(DEFAULT_URL);
    }
  }

  @Override protected void onPause() {
    super.onPause();
  }

  private ValueCallback<Uri> mUploadMessage;
  private ValueCallback<Uri[]> mUploadCallbackAboveL;
  private final static int FILECHOOSER_RESULTCODE = 1;
  private Uri imageUri;

  private void setWebChromeClient() {
    if (progressWebChromeClient == null) {
      progressWebChromeClient = new ProgressWebChromeClient(webView, progressBar);
      progressWebChromeClient.setProgressWebChromeClientCallBack(
          new ProgressWebChromeClient.ProgressWebChromeClientCallBack() {
            @Override public void onShowFileChooser(ValueCallback<Uri[]> filePathCallback) {
              mUploadCallbackAboveL = filePathCallback;
              take();
            }

            @Override public void openFileChooser(ValueCallback<Uri> uploadMsg) {
              mUploadMessage = uploadMsg;
              take();
            }

            @Override public void onReceivedTitle(WebView view, final String title) {
              webView.post(new Runnable() {
                @Override public void run() {
                  if (Strings.isNullOrEmpty(title)) {
                    tvTitle.setText(getString(R.string.app_name));
                  } else {
                    tvTitle.setText(title);
                  }
                }
              });
            }
          });
    }
    webView.setWebChromeClient(progressWebChromeClient);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == FILECHOOSER_RESULTCODE) {
      if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
      Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
      if (mUploadCallbackAboveL != null) {
        onActivityResultAboveL(requestCode, resultCode, data);
      } else if (mUploadMessage != null) {
        if (result == null) {
          mUploadMessage.onReceiveValue(imageUri);
          mUploadMessage = null;
        } else {
          mUploadMessage.onReceiveValue(result);
          mUploadMessage = null;
        }
      }
    }
  }

  //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN) private void onActivityResultAboveL(int requestCode,
      int resultCode, Intent data) {
    if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
      return;
    }

    Uri[] results = null;
    if (resultCode == Activity.RESULT_OK) {
      if (data == null) {
        results = new Uri[] { imageUri };
      } else {
        String dataString = data.getDataString();
        ClipData clipData = data.getClipData();
        if (clipData != null) {
          results = new Uri[clipData.getItemCount()];
          for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            results[i] = item.getUri();
          }
        }
        if (dataString != null) results = new Uri[] { Uri.parse(dataString) };
      }
    }

    if (results != null) {
      mUploadCallbackAboveL.onReceiveValue(results);
      mUploadCallbackAboveL = null;
    } else {
      results = new Uri[] { imageUri };
      mUploadCallbackAboveL.onReceiveValue(results);
      mUploadCallbackAboveL = null;
    }
  }

  private void take() {
    File imageStorageDir =
        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "MyApp");
    if (!imageStorageDir.exists()) {
      imageStorageDir.mkdirs();
    }
    File file = new File(imageStorageDir
        + File.separator
        + "IMG_"
        + String.valueOf(System.currentTimeMillis())
        + ".jpg");
    imageUri = Uri.fromFile(file);
    final List<Intent> cameraIntents = new ArrayList<Intent>();
    final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    final PackageManager packageManager = getPackageManager();
    final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
    for (ResolveInfo res : listCam) {
      final String packageName = res.activityInfo.packageName;
      final Intent i = new Intent(captureIntent);
      i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
      i.setPackage(packageName);
      i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
      cameraIntents.add(i);
    }
    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
    i.addCategory(Intent.CATEGORY_OPENABLE);
    i.setType("image/*");
    Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
        cameraIntents.toArray(new Parcelable[] {}));
    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
  }

  private void setStatusBar() {
    setStatusColor(R.color.white);
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

  @Override public void onBackPressed() {
    if (webView.canGoBack()) {
      webView.goBack();
    } else {
      super.onBackPressed();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (webView != null) {
      webView.setWebViewClient(null);
      webView.setWebChromeClient(null);
      // fix WebView.destroy() called while WebView is still attached to window.
      ((ViewGroup) webView.getParent()).removeView(webView);
      webView.removeAllViews();
      webView.destroy();
    }
  }
}
