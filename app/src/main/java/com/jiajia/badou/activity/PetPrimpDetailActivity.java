package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.view.GlideImageLoader;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.primp.PetPrimpDetailMvpView;
import com.jiajia.presenter.modle.primp.PetPrimpDetailPresenter;
import com.jiajia.presenter.util.ToastUtil;
import com.jiajia.presenter.util.ViewUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lei on 2018/3/15.
 * 宠物美容详情
 */
public class PetPrimpDetailActivity extends BaseActivity<PetPrimpDetailPresenter>
    implements PetPrimpDetailMvpView {

  @BindView(R.id.banner_pet_primp_detail) Banner banner;
  @BindView(R.id.relat_back_pet_primp_detail) RelativeLayout relatBack;
  @BindView(R.id.tv_title_pet_primp_detail) TextView tvTitle;
  @BindView(R.id.tv_content_title_pet_primp_detail) TextView tvContentTitle;
  @BindView(R.id.tv_address_pet_primp_detail) TextView tvAddress;
  @BindView(R.id.tv_phone_pet_primp_detail) TextView tvPhone;
  @BindView(R.id.relat_have_a_bath_pet_primp_detail) RelativeLayout relatHaveABath;
  @BindView(R.id.relat_primp_pet_primp_detail) RelativeLayout relatPrimp;
  @BindView(R.id.relat_wash_tooth_pet_primp_detail) RelativeLayout relatWashTooth;
  @BindView(R.id.relat_clip_fingernail_pet_primp_detail) RelativeLayout relatClipFingernail;
  @BindView(R.id.relat_spa_pet_primp_detail) RelativeLayout relatSpa;
  @BindView(R.id.relat_out_in_pet_primp_detail) RelativeLayout relatOutIn;
  @BindView(R.id.tv_submit_pet_primp_detail) TextView tvSubmit;
  @BindView(R.id.layout_title_content_pet_primp_detail) LinearLayout layoutTitleContent;

  public static Intent callIntent(Context context) {
    return new Intent(context, PetPrimpDetailActivity.class);
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_pet_primp_detail;
  }

  @Override protected void init() {
    initBanner();
    initBar();
  }

  @Override protected Presenter returnPresenter() {
    return new PetPrimpDetailPresenter();
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

    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutTitleContent.getLayoutParams();
    lp.topMargin = ViewUtil.getBarHeight(activity);
    layoutTitleContent.setLayoutParams(lp);
  }

  private void initBanner() {
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
    banner.setImageLoader(new GlideImageLoader());
    banner.setBannerAnimation(Transformer.Default);
    banner.isAutoPlay(true);
    banner.setDelayTime(4000);
    banner.setIndicatorGravity(BannerConfig.CENTER);
    banner.setOnBannerListener(new OnBannerListener() {
      @Override public void OnBannerClick(int position) {
        startActivity(CommonWebViewActivity.callIntent(activity,
            "https://baike.baidu.com/item/%E5%AE%A0%E7%89%A9%E7%8B%97/6677317?fr=aladdin"));
      }
    });

    List<String> imageUrls =
        Arrays.asList("https://s1.1zoom.ru/big0/453/Dogs_Spitz_Winter_hat_471485.jpg",
            "https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike60%2C5%2C5%2C60%2C20/sign=78bde825a586c9171c0e5a6ba8541baa/63d9f2d3572c11df449bb3fe612762d0f603c2a1.jpg",
            "http://img02.tooopen.com/Downs/images/2010/9/9/sy_20100909075330671012.jpg",
            "http://first-vet.bg/wp-content/uploads/2014/09/dogcat23.jpg",
            "http://www.rainbowbridge-pet.com/index/pics/20170223/201702231487847321740.png");

    banner.setImages(imageUrls);
    banner.start();
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), msg, false);
  }

  @OnClick({
      R.id.relat_back_pet_primp_detail, R.id.relat_have_a_bath_pet_primp_detail,
      R.id.relat_primp_pet_primp_detail, R.id.relat_wash_tooth_pet_primp_detail,
      R.id.relat_clip_fingernail_pet_primp_detail, R.id.relat_spa_pet_primp_detail,
      R.id.relat_out_in_pet_primp_detail, R.id.tv_submit_pet_primp_detail
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.relat_back_pet_primp_detail:
        finish();
        break;
      case R.id.relat_have_a_bath_pet_primp_detail:
        break;
      case R.id.relat_primp_pet_primp_detail:
        break;
      case R.id.relat_wash_tooth_pet_primp_detail:
        break;
      case R.id.relat_clip_fingernail_pet_primp_detail:
        break;
      case R.id.relat_spa_pet_primp_detail:
        break;
      case R.id.relat_out_in_pet_primp_detail:
        break;
      case R.id.tv_submit_pet_primp_detail:
        break;
    }
  }

  @Override public void onResume() {
    super.onResume();
    banner.start();
  }

  @Override public void onPause() {
    super.onPause();
    banner.stopAutoPlay();
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }
}
