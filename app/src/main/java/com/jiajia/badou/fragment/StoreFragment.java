package com.jiajia.badou.fragment;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.BuyActivity;
import com.jiajia.badou.activity.CommonWebViewActivity;
import com.jiajia.badou.adapter.StoreFragmentAdapter;
import com.jiajia.badou.adapter.StoreFragmentHotRecommendAdapter;
import com.jiajia.badou.bean.main.StoreHotRecommendBean;
import com.jiajia.badou.view.GlideImageLoader;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.main.StoreFragmentMvpView;
import com.jiajia.presenter.modle.main.StoreFragmentPresenter;
import com.jiajia.presenter.util.InputMethodUtil;
import com.jiajia.presenter.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lei on 2018/1/30.
 * 商城
 */
public class StoreFragment extends BaseFragment<StoreFragmentPresenter>
    implements StoreFragmentMvpView, View.OnClickListener {

  @BindView(R.id.edit_store_search) EditText editSearch;
  @BindView(R.id.layout_store_title_goods_car) LinearLayout layoutTitleGoodsCar;
  @BindView(R.id.fragment_store_recycler_view) HeaderAndFooterRecyclerView recyclerView;

  LinearLayout linearHotRecommendList;

  private Banner banner;
  private StoreFragmentAdapter adapter;
  private StoreFragmentHotRecommendAdapter storeFragmentHotRecommendAdapter;

  private List<StoreHotRecommendBean> storeHotRecommendBeans = new ArrayList<>();

  private InputMethodUtil inputMethodUtil;

  private Handler handler = new Handler();

  public static StoreFragment newInstance() {
    return new StoreFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_store;
  }

  @Override protected void init() {
    initView();
    initInputMethod();
    initEdit();
  }

  @Override protected Presenter returnPresenter() {
    return new StoreFragmentPresenter();
  }

  private void initInputMethod() {
    inputMethodUtil = new InputMethodUtil(getActivity());
  }

  private void initEdit() {
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        if (inputMethodUtil != null) {
          inputMethodUtil.hidenInputMethodWithEditText(editSearch);
        }
      }
    }, 500);
  }

  private void initView() {
    View headerView = LayoutInflater.from(getActivity())
        .inflate(R.layout.layout_header_fragment_store, recyclerView.getHeaderContainer(), false);
    banner = headerView.findViewById(R.id.fragment_store_banner);

    initBanner();

    adapter = new StoreFragmentAdapter(activity, new ArrayList<StoreHotRecommendBean>());

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.addHeaderView(headerView);

    linearHotRecommendList = headerView.findViewById(R.id.linear_store_fragment_hot_recommend);

    adapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            startActivity(BuyActivity.callIntent(activity, storeHotRecommendBeans.get(position)));
          }
        });

    initStoreType(headerView);

    initRecommend();
  }

  private void initRecommend() {
    if (storeFragmentHotRecommendAdapter == null) {
      storeFragmentHotRecommendAdapter = new StoreFragmentHotRecommendAdapter(activity);
      storeFragmentHotRecommendAdapter.setStoreFragmentAdapterCallBack(
          new StoreFragmentHotRecommendAdapter.StoreFragmentHotRecommendCallBack() {
            @Override public void onItemClick(int position) {
              startActivity(BuyActivity.callIntent(activity, storeHotRecommendBeans.get(position)));
            }
          });
    }
    storeHotRecommendBeans.clear();
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "https://www.coastalsports.co.nz/wp-content/uploads/2016/01/SS12_HeadwaterCollar_OrangeSunset_Zoom.jpg",
        "可拆卸真皮进口狗狗项圈", "¥ 1998", "¥ 2498"));
    storeHotRecommendBeans.add(
        new StoreHotRecommendBean("https://sc02.alicdn.com/kf/HTB1qZE3JVXXXXbTXXXXq6xXFXXXL.jpg",
            "纯棉耐撕咬猫咪狗狗通用小窝", "¥ 259", "¥ 298"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://pic.qiantucdn.com/58pic/27/12/30/90W58PICSJ4_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center",
        "客厅房间装饰宠物画，纯人工制作", "¥ 29", "¥ 39"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://img11.360buyimg.com/n12/jfs/t2833/302/3110918137/123060/4fa3451b/57808ee7N436d87e7.jpg",
        "大体型家宠出门溜圈必备帅气口罩", "¥ 99", "¥ 128"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "https://ae01.alicdn.com/kf/HTB1QFeqRVXXXXcTXpXXq6xXFXXXR/-.jpg_640x640.jpg",
        "泰迪专用纯棉马甲带溜圈绳", "¥ 598", "¥ 798"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://www.pawfi.com/images/halloween-despicable-me-2-minion-dog-costume.jpg",
        "小黄人萌宠马甲，进口包装", "¥ 389", "¥ 499"));
    storeFragmentHotRecommendAdapter.addAll(storeHotRecommendBeans, linearHotRecommendList);
    adapter.addAll(storeHotRecommendBeans);
  }

  private void initStoreType(View view) {
    LinearLayout layoutZhuShi = view.findViewById(R.id.layout_store_zhu_liang);
    LinearLayout layoutLingShi = view.findViewById(R.id.layout_store_lingshi);
    LinearLayout layoutWanJu = view.findViewById(R.id.layout_store_wanju);
    LinearLayout layoutYiLiao = view.findViewById(R.id.layout_store_yiliao);
    LinearLayout layoutFuShi = view.findViewById(R.id.layout_store_fushi);
    LinearLayout layoutQingJie = view.findViewById(R.id.layout_store_qingjie);
    LinearLayout layoutJiaJu = view.findViewById(R.id.layout_store_jiaju);
    LinearLayout layoutYingYang = view.findViewById(R.id.layout_store_yingyang);

    layoutZhuShi.setOnClickListener(this);
    layoutLingShi.setOnClickListener(this);
    layoutWanJu.setOnClickListener(this);
    layoutYiLiao.setOnClickListener(this);
    layoutFuShi.setOnClickListener(this);
    layoutQingJie.setOnClickListener(this);
    layoutJiaJu.setOnClickListener(this);
    layoutYingYang.setOnClickListener(this);
  }

  private void initBanner() {
    final List<String> urls = Arrays.asList(getResources().getStringArray(R.array.kepu_url));
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
    banner.setImageLoader(new GlideImageLoader());
    banner.setBannerAnimation(Transformer.Default);
    banner.isAutoPlay(true);
    banner.setDelayTime(4000);
    banner.setIndicatorGravity(BannerConfig.RIGHT);
    banner.setOnBannerListener(new OnBannerListener() {
      @Override public void OnBannerClick(int position) {
        startActivity(CommonWebViewActivity.callIntent(getActivity(), urls.get(position)));
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

  @Override public void onResume() {
    super.onResume();
    if (banner != null) {
      banner.start();
    }
  }

  @Override public void onPause() {
    super.onPause();
    banner.stopAutoPlay();
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.layout_store_zhu_liang:

        break;
      case R.id.layout_store_lingshi:

        break;
      case R.id.layout_store_wanju:

        break;
      case R.id.layout_store_yiliao:

        break;
      case R.id.layout_store_fushi:

        break;
      case R.id.layout_store_qingjie:

        break;
      case R.id.layout_store_jiaju:

        break;
      case R.id.layout_store_yingyang:

        break;
      default:
        break;
    }
  }

  @OnClick(R.id.layout_store_title_goods_car) public void onViewClicked() {
    ToastUtil.showToast(getActivity(), "购物车", false);
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }
}
