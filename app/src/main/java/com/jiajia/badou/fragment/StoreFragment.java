package com.jiajia.badou.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.CommonWebViewActivity;
import com.jiajia.badou.adapter.StoreFragmentAdapter;
import com.jiajia.badou.util.InputMethodUtil;
import com.jiajia.badou.util.ToastUtil;
import com.jiajia.badou.view.GlideImageLoader;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
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
public class StoreFragment extends BaseFragment implements View.OnClickListener {

  @BindView(R.id.edit_store_search) EditText editSearch;
  @BindView(R.id.layout_store_title_goods_car) LinearLayout layoutTitleGoodsCar;
  @BindView(R.id.fragment_store_recycler_view) HeaderAndFooterRecyclerView recyclerView;

  private Banner banner;
  private StoreFragmentAdapter adapter;

  private List<String> texts;
  private InputMethodUtil inputMethodUtil;

  private Handler handler = new Handler();

  public static StoreFragment newInstance() {
    return new StoreFragment();
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main_store, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initView();
    initInputMethod();
    initEdit();
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

    adapter = new StoreFragmentAdapter(getActivity(), new ArrayList<String>());

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.addHeaderView(headerView);

    texts = new ArrayList<>();
    texts.add("第一个");
    texts.add("第二个");
    texts.add("第三个");
    texts.add("第四个");
    texts.add("第五个");
    texts.add("第六个");
    texts.add("第七个");
    texts.add("第八个");
    texts.add("第九个");
    texts.add("第十个");
    texts.add("第十一个");
    texts.add("第十二个");

    adapter.addAll(texts);

    adapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            ToastUtil.showToast(getActivity(), texts.get(position), false);
          }
        });

    initStoreType(headerView);
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
    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
    banner.setImageLoader(new GlideImageLoader());
    banner.setBannerAnimation(Transformer.Default);
    banner.isAutoPlay(true);
    banner.setDelayTime(4000);
    banner.setIndicatorGravity(BannerConfig.RIGHT);
    banner.setOnBannerListener(new OnBannerListener() {
      @Override public void OnBannerClick(int position) {
        startActivity(CommonWebViewActivity.callIntent(getActivity(),
            "https://baike.baidu.com/item/%E5%AE%A0%E7%89%A9%E7%8B%97/6677317?fr=aladdin"));
      }
    });
    List<Integer> imageUrls =
        Arrays.asList(R.mipmap.xm2, R.mipmap.xm3, R.mipmap.xm4, R.mipmap.xm5, R.mipmap.xm6,
            R.mipmap.xm7, R.mipmap.xm1, R.mipmap.xm8, R.mipmap.xm9, R.mipmap.xm1, R.mipmap.xm2,
            R.mipmap.xm3, R.mipmap.xm4, R.mipmap.xm5, R.mipmap.xm6);
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
}
