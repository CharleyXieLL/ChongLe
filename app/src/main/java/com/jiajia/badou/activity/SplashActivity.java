package com.jiajia.badou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.BindView;
import com.jiajia.badou.R;
import com.jiajia.badou.util.BaseSharedDataUtil;

/**
 * Created by Lei on 2018/2/28.
 * 开屏
 */
public class SplashActivity extends BaseActivity {

  @BindView(R.id.img_splash) ImageView imageView;

  private Handler handler = new Handler();

  private Animation animation;

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_splash;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }

  @Override protected void init() {
    animation = AnimationUtils.loadAnimation(activity, R.anim.yj_small2big);
    imageView.startAnimation(animation);
    imageView.setAnimation(animation);
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        imageView.clearAnimation();
        long id = BaseSharedDataUtil.getUserId(getApplicationContext());
        if (id == 0) {
          startActivity(LoginActivity.callIntent(SplashActivity.this));
        } else {
          startActivity(MainActivity.callIntent(SplashActivity.this));
        }
        finish();
      }
    }, 4000);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    handler.removeCallbacksAndMessages(null);
  }

  @Override public void getFailed(String msg, String code) {

  }
}
