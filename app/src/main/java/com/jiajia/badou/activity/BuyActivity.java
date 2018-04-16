package com.jiajia.badou.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.bean.main.StoreHotRecommendBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.store.BuyMvpView;
import com.jiajia.presenter.modle.store.BuyPresenter;
import com.jiajia.presenter.util.ViewUtil;

/**
 * Created by Lei on 2018/4/16.
 * 购买
 */
public class BuyActivity extends BaseActivity<BuyPresenter> implements BuyMvpView {

  @BindView(R.id.img_buy_title) ImageView imgBuyTitle;
  @BindView(R.id.cl_buy_return_back) RelativeLayout clBuyReturnBack;
  @BindView(R.id.tv_buy_title) TextView tvBuyTitle;
  @BindView(R.id.tv_buy_account) TextView tvAccount;
  @BindView(R.id.tv_buy_old_account) TextView tvOldAccount;
  @BindView(R.id.tv_buy_surplus) TextView tvBuySurplus;
  @BindView(R.id.tv_buy_sales_volume_every_month) TextView tvBuySalesVolumeEveryMonth;
  @BindView(R.id.tv_buy_address) TextView tvBuyAddress;
  @BindView(R.id.tv_buy_put_in_car) TextView tvBuyPutInCar;
  @BindView(R.id.tv_buy_submit) TextView tvBuySubmit;
  @BindView(R.id.layout_buy_title) RelativeLayout layoutTitle;

  private StoreHotRecommendBean storeHotRecommendBean;

  private Handler handler = new Handler();

  public static Intent callIntent(Context context, StoreHotRecommendBean storeHotRecommendBean) {
    Intent intent = new Intent(context, BuyActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(BuyActivity.class.getSimpleName(), storeHotRecommendBean);
    intent.putExtras(bundle);
    return intent;
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.ycjt_activity_buy;
  }

  @Override protected Presenter returnPresenter() {
    return new BuyPresenter();
  }

  @Override protected void init() {
    getIntentData();
    initView();
    initBar();
  }

  @SuppressLint("SetTextI18n") private void initView() {
    if (storeHotRecommendBean != null) {
      Glide.with(activity).load(storeHotRecommendBean.getImg()).into(imgBuyTitle);
      tvBuyTitle.setText(storeHotRecommendBean.getTip());
      SpannableString spannableString = new SpannableString(storeHotRecommendBean.getOldAccount());
      spannableString.setSpan(new StrikethroughSpan(), 0,
          storeHotRecommendBean.getOldAccount().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      tvAccount.setText(storeHotRecommendBean.getAccount());
      tvOldAccount.setText(spannableString);
      tvBuySurplus.setText("库存  3795");
      tvBuySalesVolumeEveryMonth.setText("月销量：24284");
      tvBuyAddress.setText("浙江杭州");
    }
  }

  private void getIntentData() {
    storeHotRecommendBean = ((StoreHotRecommendBean) getIntent().getSerializableExtra(
        BuyActivity.class.getSimpleName()));
  }

  private void initBar() {
    //4.4 全透明状态栏（有的机子是过渡形式的透明）
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    //5.0 全透明实现
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(
          Color.TRANSPARENT);// calculateStatusColor(Color.WHITE, (int) alphaValue)
    }

    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutTitle.getLayoutParams();
    lp.topMargin = ViewUtil.getBarHeight(activity);
    layoutTitle.setLayoutParams(lp);
  }

  @OnClick({ R.id.cl_buy_return_back, R.id.tv_buy_put_in_car, R.id.tv_buy_submit })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.cl_buy_return_back:
        finish();
        break;
      case R.id.tv_buy_put_in_car:
        showLoadingDialog("");
        handler.postDelayed(new Runnable() {
          @Override public void run() {
            dismissLoadingDialog();
            showToast("已添加进您的购物车");
          }
        }, 1500);
        break;
      case R.id.tv_buy_submit:
        showToast("敬请期待");
        break;
    }
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }
}
