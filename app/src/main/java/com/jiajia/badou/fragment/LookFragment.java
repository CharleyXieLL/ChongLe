package com.jiajia.badou.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.MainActivity;
import com.jiajia.badou.adapter.StoreFragmentAdapter;
import com.jiajia.badou.bean.main.StoreHotRecommendBean;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.modle.main.LookFragmentMvpView;
import com.jiajia.presenter.modle.main.LookFragmentPresenter;
import com.jiajia.presenter.util.Strings;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/1/30.
 * 发现
 */
public class LookFragment extends BaseFragment<LookFragmentPresenter>
    implements LookFragmentMvpView {

  public static final String LOOK_TITLE_TYPE_CHONG_QUAN = "look_title_type_chong_quan";
  public static final String LOOK_TITLE_TYPE_KE_PU = "look_title_type_ke_pu";

  @BindView(R.id.tv_fragment_look_title_chong_quan) TextView tvTitleChongQuan;
  @BindView(R.id.line_fragment_look_chong_quan) View lineChongQuan;
  @BindView(R.id.layout_fragment_look_chong_quan) LinearLayout layoutChongQuan;
  @BindView(R.id.tv_fragment_look_title_ke_pu) TextView tvTitleKePu;
  @BindView(R.id.line_fragment_look_ke_pu) View lineKePu;
  @BindView(R.id.layout_fragment_look_ke_pu) LinearLayout layoutKePu;
  @BindView(R.id.recycler_look_fragment_chong_quan) HeaderAndFooterRecyclerView recyclerChongQuan;
  @BindView(R.id.recycler_look_fragment_ke_pu) HeaderAndFooterRecyclerView recyclerKePu;

  private StoreFragmentAdapter chongQuanAdapter;
  private StoreFragmentAdapter kePuAdapter;
  private List<StoreHotRecommendBean> storeHotRecommendBeans = new ArrayList<>();

  private String titleType;

  private Handler handler = new Handler();

  public static LookFragment newInstance() {
    return new LookFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_look;
  }

  @Override protected void init() {

    storeHotRecommendBeans.clear();
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "https://www.coastalsports.co.nz/wp-content/uploads/2016/01/SS12_HeadwaterCollar_OrangeSunset_Zoom.jpg",
        "可拆卸真皮进口狗狗项圈", "¥1998", "¥2498"));
    storeHotRecommendBeans.add(
        new StoreHotRecommendBean("https://sc02.alicdn.com/kf/HTB1qZE3JVXXXXbTXXXXq6xXFXXXL.jpg",
            "纯棉耐撕咬猫咪狗狗通用小窝", "¥259", "¥298"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://pic.qiantucdn.com/58pic/27/12/30/90W58PICSJ4_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center",
        "客厅房间装饰宠物画，纯人工制作", "¥29", "¥39"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://img11.360buyimg.com/n12/jfs/t2833/302/3110918137/123060/4fa3451b/57808ee7N436d87e7.jpg",
        "大体型家宠出门溜圈必备帅气口罩", "¥99", "¥128"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "https://ae01.alicdn.com/kf/HTB1QFeqRVXXXXcTXpXXq6xXFXXXR/-.jpg_640x640.jpg",
        "泰迪专用纯棉马甲带溜圈绳", "¥598", "¥798"));
    storeHotRecommendBeans.add(new StoreHotRecommendBean(
        "http://www.pawfi.com/images/halloween-despicable-me-2-minion-dog-costume.jpg",
        "小黄人萌宠马甲，进口包装", "¥389", "¥499"));

    initChongQuanView();

    initKePuView();
  }

  private void initKePuView() {
    kePuAdapter = new StoreFragmentAdapter(activity, new ArrayList<StoreHotRecommendBean>());

    recyclerKePu.setHasFixedSize(true);
    recyclerKePu.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerKePu.setAdapter(kePuAdapter);

    kePuAdapter.addAll(storeHotRecommendBeans);
    kePuAdapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
          }
        });
  }

  private void initChongQuanView() {
    chongQuanAdapter = new StoreFragmentAdapter(activity, new ArrayList<StoreHotRecommendBean>());

    View headerView = LayoutInflater.from(getActivity())
        .inflate(R.layout.layout_header_fragment_look_chong_quan,
            recyclerChongQuan.getHeaderContainer(), false);

    ImageView imgChongQuanTitle = headerView.findViewById(R.id.img_fragment_look_chong_quan_title);

    Glide.with(getActivity())
        .load(
            "https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike60%2C5%2C5%2C60%2C20/sign=78bde825a586c9171c0e5a6ba8541baa/63d9f2d3572c11df449bb3fe612762d0f603c2a1.jpg")
        .error(R.mipmap.ic_launcher)
        .into(imgChongQuanTitle);

    recyclerChongQuan.setHasFixedSize(true);
    recyclerChongQuan.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerChongQuan.setAdapter(chongQuanAdapter);

    recyclerChongQuan.addHeaderView(headerView);

    chongQuanAdapter.addAll(storeHotRecommendBeans);

    chongQuanAdapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
          }
        });
  }

  public void setTitleType(final String type) {
    showLoadingDialog("");
    titleType = type;
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        dismissLoadingDialog();
        if (Strings.isNullOrEmpty(type)) {
          setChongQuanUi();
        } else if (!Strings.isNullOrEmpty(type) && titleType.equals(LOOK_TITLE_TYPE_CHONG_QUAN)) {
          setChongQuanUi();
        } else {
          setKePuUi();
        }
      }
    }, 500);
  }

  private void setChongQuanUi() {
    tvTitleChongQuan.setTextColor(
        ContextCompat.getColor(getActivity(), R.color.yq_text_color_blank));
    lineChongQuan.setVisibility(View.VISIBLE);

    tvTitleKePu.setTextColor(ContextCompat.getColor(getActivity(), R.color.yq_color_a0a0a0));
    lineKePu.setVisibility(View.GONE);

    recyclerChongQuan.setVisibility(View.VISIBLE);
    recyclerKePu.setVisibility(View.GONE);
  }

  private void setKePuUi() {
    tvTitleChongQuan.setTextColor(ContextCompat.getColor(getActivity(), R.color.yq_color_a0a0a0));
    lineChongQuan.setVisibility(View.GONE);

    tvTitleKePu.setTextColor(ContextCompat.getColor(getActivity(), R.color.yq_text_color_blank));
    lineKePu.setVisibility(View.VISIBLE);

    recyclerChongQuan.setVisibility(View.GONE);
    recyclerKePu.setVisibility(View.VISIBLE);
  }

  @SuppressLint("ResourceType")
  @OnClick({ R.id.layout_fragment_look_chong_quan, R.id.layout_fragment_look_ke_pu })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.layout_fragment_look_chong_quan:
        setChongQuanUi();

        LocalBroadcastManager localBroadcastManagerChongQuan =
            LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManagerChongQuan.sendBroadcast(
            MainActivity.getMsgIntent(LOOK_TITLE_TYPE_CHONG_QUAN));
        break;
      case R.id.layout_fragment_look_ke_pu:
        setKePuUi();

        LocalBroadcastManager localBroadcastManagerKePu =
            LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManagerKePu.sendBroadcast(MainActivity.getMsgIntent(LOOK_TITLE_TYPE_KE_PU));
        break;
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    handler.removeCallbacksAndMessages(null);
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }
}
