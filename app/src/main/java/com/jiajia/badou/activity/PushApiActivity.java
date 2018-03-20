package com.jiajia.badou.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jiajia.badou.bean.PushBean;

/**
 * Created by Lei on 2018/1/9.
 */
public class PushApiActivity extends BaseActivity {

  public static final String PUSHBEAN = "push";
  private PushBean pushBean;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      pushBean = ((PushBean) bundle.getSerializable(PUSHBEAN));
    }
    if (pushBean != null) {
      startActivity(CommonWebViewActivity.callIntent(PushApiActivity.this, pushBean.getUrl()));
      finish();
    }
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return 0;
  }

  @Override protected void init() {

  }

  @Override public void getFailed(String msg, String code) {

  }
}
