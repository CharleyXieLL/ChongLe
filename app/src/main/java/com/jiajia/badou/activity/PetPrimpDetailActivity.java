package com.jiajia.badou.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.PetPrimpAdapter;
import com.jiajia.badou.adapter.PetPrimpDetailAdapter;
import com.jiajia.badou.bean.event.SetPetIdEvent;
import com.jiajia.badou.util.ActManager;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.view.GlideImageLoader;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.bean.InsertOrderPost;
import com.jiajia.presenter.bean.PetPrimpBean;
import com.jiajia.presenter.bean.PetPrimpDetailListBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.primp.PetPrimpDetailMvpView;
import com.jiajia.presenter.modle.primp.PetPrimpDetailPresenter;
import com.jiajia.presenter.util.ToastUtil;
import com.jiajia.presenter.util.ViewUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Lei on 2018/3/15.
 * 宠物美容详情
 */
public class PetPrimpDetailActivity extends BaseActivity<PetPrimpDetailPresenter>
    implements PetPrimpDetailMvpView {

  private static final String TAG = "PetPrimpDetailActivity";

  private static final String FROM = "from";

  @BindView(R.id.tv_submit_pet_primp_detail) TextView tvSubmit;

  @BindView(R.id.recyclerview_pet_primp_detail) HeaderAndFooterRecyclerView recyclerView;

  private PetPrimpDetailAdapter primpDetailAdapter;

  private PetPrimpBean petPrimpBean;

  private String from;
  private Banner banner;
  private RelativeLayout relatBack;
  private TextView tvTitle;
  private TextView tvContentTitle;
  private TextView tvAddress;
  private TextView tvPhone;
  private LinearLayout layoutTitleContent;

  private List<PetPrimpDetailListBean> petPrimpDetailListBeans = new ArrayList<>();

  private int clickPosition = PetPrimpDetailAdapter.NO_DATA;

  private int petId = PetPrimpDetailAdapter.NO_DATA;

  private Handler handler = new Handler();
  private TextView tvPrimpServiceTitle;

  public static Intent callIntent(Context context, String type, PetPrimpBean petPrimpBean) {
    Intent intent = new Intent(context, PetPrimpDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(TAG, petPrimpBean);
    intent.putExtra(FROM, type);
    intent.putExtras(bundle);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActManager.getAppManager().add(this);
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_pet_primp_detail;
  }

  @Override protected void init() {
    getIntentData();
    initRecyclerView();
    initBanner();
    initBar();
    getData();
  }

  private void getData() {
    if (!from.equals(PetPrimpAdapter.JI_YANG)) {
      tvPrimpServiceTitle.setVisibility(View.VISIBLE);
      showLoadingDialog("");
      getPresenter().selectAllService();
    } else {
      tvPrimpServiceTitle.setVisibility(View.GONE);
    }
  }

  @SuppressLint("SetTextI18n") private void initRecyclerView() {
    View headerView = LayoutInflater.from(activity)
        .inflate(R.layout.layout_pet_primp_detail_header, recyclerView.getHeaderContainer(), false);
    banner = headerView.findViewById(R.id.banner_pet_primp_detail);
    relatBack = headerView.findViewById(R.id.relat_back_pet_primp_detail);
    tvTitle = headerView.findViewById(R.id.tv_title_pet_primp_detail);
    tvContentTitle = headerView.findViewById(R.id.tv_content_title_pet_primp_detail);
    tvAddress = headerView.findViewById(R.id.tv_address_pet_primp_detail);
    tvPhone = headerView.findViewById(R.id.tv_phone_pet_primp_detail);
    layoutTitleContent = headerView.findViewById(R.id.layout_title_content_pet_primp_detail);
    tvPrimpServiceTitle = headerView.findViewById(R.id.tv_primp_detail_service_title);

    relatBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });

    primpDetailAdapter =
        new PetPrimpDetailAdapter(activity, new ArrayList<PetPrimpDetailListBean>(), from);

    primpDetailAdapter.setPetPrimpDetailAdapterCallBack(
        new PetPrimpDetailAdapter.PetPrimpDetailAdapterCallBack() {
          @Override public void onClick(int position) {
            clickPosition = position;
          }
        });

    GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(primpDetailAdapter);
    recyclerView.addHeaderView(headerView);

    if (petPrimpBean != null) {
      tvTitle.setText("宠物" + from + "中心");
      tvAddress.setText(petPrimpBean.getLocation());
      tvPhone.setText(petPrimpBean.getQq());
      tvContentTitle.setText(petPrimpBean.getLocation());
    }
  }

  @SuppressLint("SetTextI18n") private void getIntentData() {
    from = getIntent().getStringExtra(FROM);
    petPrimpBean = ((PetPrimpBean) getIntent().getExtras().getSerializable(TAG));
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

    RelativeLayout.LayoutParams lp =
        (RelativeLayout.LayoutParams) layoutTitleContent.getLayoutParams();
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

    List<String> imageUrls = Arrays.asList(
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
      R.id.tv_submit_pet_primp_detail
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_submit_pet_primp_detail:
        if (!from.equals(PetPrimpAdapter.JI_YANG)) {
          if (clickPosition == PetPrimpDetailAdapter.NO_DATA) {
            showToast("请选择一种" + from + "服务");
          } else {
            startActivity(PrimpSelectPetActivity.callIntent(activity, from, petPrimpBean.getStore(),
                petPrimpBean.getQq()));
          }
        } else {
          startActivity(PrimpSelectPetActivity.callIntent(activity, from, petPrimpBean.getStore(),
              petPrimpBean.getQq()));
        }
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

  @Override public void selectAllService(List<PetPrimpDetailListBean> petPrimpDetailListBeans) {
    dismissLoadingDialog();
    this.petPrimpDetailListBeans.clear();
    this.petPrimpDetailListBeans.addAll(petPrimpDetailListBeans);

    primpDetailAdapter.clear();
    primpDetailAdapter.addAll(petPrimpDetailListBeans);
  }

  @Override public void insertOrder() {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), "预约成功", false);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    handler.removeCallbacksAndMessages(null);
    ActManager.getAppManager().remove(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onSetPetIdEvent(SetPetIdEvent event) {
    petId = event.getPetId();
    showLoadingDialog("");
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        getPresenter().insertOrder(new InsertOrderPost(BaseSharedDataUtil.getUserId(activity), from,
            petPrimpBean.getStore(), petId));
      }
    }, 500);
  }
}
