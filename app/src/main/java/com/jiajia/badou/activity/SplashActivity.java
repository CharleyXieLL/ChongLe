package com.jiajia.badou.activity;

import android.os.Bundle;
import android.os.Handler;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import com.jiajia.badou.util.BaseSharedDataUtil;

/**
 * Created by Lei on 2018/2/28.
 * 开屏
 */
public class SplashActivity extends BaseActivity {

  private Handler handler = new Handler();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        long id = BaseSharedDataUtil.getUserId(getApplicationContext());
        if (id == 0) {
          startActivity(LoginActivity.callIntent(SplashActivity.this));
        } else {
          startActivity(MainActivity.callIntent(SplashActivity.this));
        }
        finish();
      }
    }, 2500);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    handler.removeCallbacksAndMessages(null);
  }

  @Override public void getFailed(String msg, String code) {

  }
}
