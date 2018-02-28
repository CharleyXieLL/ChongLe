package com.jiajia.badou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.fragment.BaseFragment;
import com.jiajia.badou.fragment.LookFragment;
import com.jiajia.badou.fragment.MainPageFragment;
import com.jiajia.badou.fragment.MineFragment;
import com.jiajia.badou.fragment.StoreFragment;
import com.jiajia.badou.util.GPSUtil;
import com.jiajia.badou.util.Strings;
import com.jiajia.badou.util.ToastUtil;
import com.jiajia.badou.util.baidu.QtLocationClient;

public class MainActivity extends BaseActivity {

  public static final String LOOK_TITLE_TYPE = "look_title_type";
  public static final int BACKPRESSED_DURATION = 2000;

  @BindView(R.id.main_fragment_content) FrameLayout mainFragmentContent;

  @BindView(R.id.tv_main_tab_main) TextView tvTabMain;
  @BindView(R.id.tv_main_tab_store) TextView tvTabStore;
  @BindView(R.id.tv_main_tab_look) TextView tvTabLook;
  @BindView(R.id.tv_main_tab_mine) TextView tvTabMine;

  @BindView(R.id.layout_main_check_main_page) LinearLayout layoutCheckMainPage;
  @BindView(R.id.layout_main_check_store) LinearLayout layoutCheckStore;
  @BindView(R.id.layout_main_check_look) LinearLayout layoutCheckLook;
  @BindView(R.id.layout_main_check_mine) LinearLayout layoutCheckMine;

  @BindView(R.id.img_main_check_main_page) ImageView imgMainCheckMainPage;
  @BindView(R.id.img_main_check_store) ImageView imgMainCheckStore;
  @BindView(R.id.img_main_check_look) ImageView imgMainCheckLook;
  @BindView(R.id.img_main_check_mine) ImageView imgMainCheckMine;

  private String lookTitleType;

  public static Intent getMsgIntent(String type) {
    Intent intent = new Intent(LOOK_TITLE_TYPE);
    intent.putExtra(LOOK_TITLE_TYPE, type);
    return intent;
  }

  public static Intent getShowLoadingIntent(String type, String text) {
    Intent intent = new Intent(BaseFragment.SHOW_LOADING);
    intent.putExtra(BaseFragment.SHOW_LOADING, type);
    intent.putExtra(BaseFragment.LOADING_TEXT, text);
    return intent;
  }

  private MainPageFragment mainPageFragment;
  private StoreFragment storeFragment;
  private LookFragment lookFragment;
  private MineFragment mineFragment;

  private Fragment currentFragment;

  private GPSUtil gpsUtil;

  /**
   * 是否处于 onSaveInstanceState 状态期间
   */
  private boolean mStateSaved = false;

  private String locationText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    mainPageFragment = MainPageFragment.newInstance();
    storeFragment = StoreFragment.newInstance();
    lookFragment = LookFragment.newInstance();
    mineFragment = MineFragment.newInstance();

    tarGetFragment(mainPageFragment);

    tvTabMain.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.main_check_true));
    imgMainCheckMainPage.setBackgroundResource(R.mipmap.check_main_page_true);

    initGpsUtil();

    LocalBroadcastManager.getInstance(this)
        .registerReceiver(broadcastReceiver, new IntentFilter(LOOK_TITLE_TYPE));

    LocalBroadcastManager.getInstance(this)
        .registerReceiver(broadcastReceiverShowLoading,
            new IntentFilter(BaseFragment.SHOW_LOADING));
  }

  private void initGpsUtil() {
    if (gpsUtil == null) {
      gpsUtil = new GPSUtil(MainActivity.this);
      gpsUtil.setGpsUtilCallBack(new GPSUtil.GPSUtilCallBack() {
        @Override public void onReceive(QtLocationClient.Location location, boolean success) {
          if (success) {
            locationText = location.getCity().replaceAll("市", "").replaceAll("镇", "");
            if (mainPageFragment != null) {
              mainPageFragment.setLocation(locationText);
            }
          }
        }
      });
    }
    gpsUtil.start();
  }

  /**
   * 进入目标页面
   */
  public void tarGetFragment(Fragment fragment) {
    if (currentFragment == fragment) return;
    try {
      if (!mStateSaved) {
        setCurrentFragment(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_content, fragment, "").commitAllowingStateLoss();
      }
    } catch (IllegalStateException e) {
      setCurrentFragment(fragment);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.main_fragment_content, fragment, "").commitAllowingStateLoss();
    }
  }

  public void setCurrentFragment(Fragment fragment) {
    this.currentFragment = fragment;
  }

  @Override protected void onResume() {
    super.onResume();
    mStateSaved = false;
  }

  @Override protected void onStop() {
    mStateSaved = true;
    super.onStop();
  }

  @Override protected void onPause() {
    mStateSaved = true;
    super.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (gpsUtil != null) {
      gpsUtil.onDestroy();
    }
    LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverShowLoading);
  }

  /**
   * 上一次按下back键的时间戳
   */
  private long mLastClickBackKeyTime;

  @Override public void onBackPressed() {
    boolean isProcessed = false;
    if ((System.currentTimeMillis() - mLastClickBackKeyTime) > BACKPRESSED_DURATION) {
      ToastUtil.showToast(MainActivity.this, "再按一次退出程序", false);
      mLastClickBackKeyTime = System.currentTimeMillis();
    } else {
      isProcessed = true;
    }
    if (isProcessed) {
      super.onBackPressed();
    }
  }

  @OnClick({
      R.id.layout_main_check_main_page, R.id.layout_main_check_store, R.id.layout_main_check_look,
      R.id.layout_main_check_mine
  }) public void onViewClicked(View view) {
    dismissLoadingDialog();
    switch (view.getId()) {
      case R.id.layout_main_check_main_page:
        tarGetFragment(mainPageFragment);
        tvTabMain.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.main_check_true));
        tvTabStore.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabLook.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabMine.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        imgMainCheckMainPage.setBackgroundResource(R.mipmap.check_main_page_true);
        imgMainCheckStore.setBackgroundResource(R.mipmap.check_store_false);
        imgMainCheckLook.setBackgroundResource(R.mipmap.check_look_false);
        imgMainCheckMine.setBackgroundResource(R.mipmap.check_mine_false);

        if (mainPageFragment != null) {
          mainPageFragment.setLocation(locationText);
        }
        break;
      case R.id.layout_main_check_store:
        tarGetFragment(storeFragment);
        tvTabMain.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabStore.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.main_check_true));
        tvTabLook.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabMine.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        imgMainCheckMainPage.setBackgroundResource(R.mipmap.check_main_page_false);
        imgMainCheckStore.setBackgroundResource(R.mipmap.check_store_true);
        imgMainCheckLook.setBackgroundResource(R.mipmap.check_look_false);
        imgMainCheckMine.setBackgroundResource(R.mipmap.check_mine_false);
        break;
      case R.id.layout_main_check_look:
        tarGetFragment(lookFragment);
        tvTabMain.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabStore.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabLook.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.main_check_true));
        tvTabMine.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        imgMainCheckMainPage.setBackgroundResource(R.mipmap.check_main_page_false);
        imgMainCheckStore.setBackgroundResource(R.mipmap.check_store_false);
        imgMainCheckLook.setBackgroundResource(R.mipmap.check_look_true);
        imgMainCheckMine.setBackgroundResource(R.mipmap.check_mine_false);

        if (lookFragment != null) {
          lookFragment.setTitleType(lookTitleType);
        }
        break;
      case R.id.layout_main_check_mine:
        tarGetFragment(mineFragment);
        tvTabMain.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabStore.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabLook.setTextColor(
            ContextCompat.getColor(MainActivity.this, R.color.yq_text_color_blank));
        tvTabMine.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.main_check_true));
        imgMainCheckMainPage.setBackgroundResource(R.mipmap.check_main_page_false);
        imgMainCheckStore.setBackgroundResource(R.mipmap.check_store_false);
        imgMainCheckLook.setBackgroundResource(R.mipmap.check_look_false);
        imgMainCheckMine.setBackgroundResource(R.mipmap.check_mine_true);
        break;
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (gpsUtil != null) {
      gpsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

    @Override public void onReceive(Context context, Intent intent) {
      String type = intent.getStringExtra(LOOK_TITLE_TYPE);
      lookTitleType = type;
    }
  };

  private BroadcastReceiver broadcastReceiverShowLoading = new BroadcastReceiver() {

    @Override public void onReceive(Context context, Intent intent) {
      String type = intent.getStringExtra(BaseFragment.SHOW_LOADING);
      String text = intent.getStringExtra(BaseFragment.LOADING_TEXT);
      if (type.equals(BaseFragment.SHOW)) {
        showLoadingDialog(Strings.isNullOrEmpty(text) ? "" : text);
      } else {
        dismissLoadingDialog();
      }
    }
  };
}
