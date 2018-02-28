package com.jiajia.badou.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.CommonWebViewActivity;
import com.jiajia.badou.adapter.MainPageFragmentAdapter;
import com.jiajia.badou.util.InputMethodUtil;
import com.jiajia.badou.util.Strings;
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
 * 首页
 */
public class MainPageFragment extends BaseFragment {

  @BindView(R.id.fragment_main_page_recycler_view) HeaderAndFooterRecyclerView recyclerView;
  @BindView(R.id.edit_main_page_search) EditText editText;
  @BindView(R.id.tv_main_page_location) TextView tvLocation;

  private Banner banner;
  private MainPageFragmentAdapter adapter;

  private List<String> texts;
  private InputMethodUtil inputMethodUtil;

  private Handler handler = new Handler();

  private Handler showLocationHandler = new Handler();

  public static MainPageFragment newInstance() {
    return new MainPageFragment();
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main_page, container, false);
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
          inputMethodUtil.hidenInputMethodWithEditText(editText);
        }
      }
    }, 500);
  }

  private void initView() {
    View headerView = LayoutInflater.from(getActivity())
        .inflate(R.layout.layout_header_fragment_main_page, recyclerView.getHeaderContainer(),
            false);
    banner = headerView.findViewById(R.id.fragment_main_page_banner);

    initBanner();

    adapter = new MainPageFragmentAdapter(getActivity(), new ArrayList<String>());

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.addHeaderView(headerView);

    texts = new ArrayList<>();
    texts.add("第一行");
    texts.add("第二行");
    texts.add("第三行");
    texts.add("第四行");
    texts.add("第五行");
    texts.add("第六行");
    texts.add("第七行");
    texts.add("第八行");
    texts.add("第九行");
    texts.add("第十行");
    texts.add("第十一行");
    texts.add("第十二行");

    adapter.addAll(texts);

    adapter.setMainPageFragmentAdapterCallBack(
        new MainPageFragmentAdapter.MainPageFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            ToastUtil.showToast(getActivity(), texts.get(position), false);
          }
        });

    tvLocation.setVisibility(View.GONE);
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

  public void setLocation(final String text) {
    if (!Strings.isNullOrEmpty(text)) {
      showLocationHandler.postDelayed(new Runnable() {
        @Override public void run() {
          tvLocation.setText(text);
          tvLocation.setVisibility(View.VISIBLE);
        }
      }, 500);
    }
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

  @Override public void onDestroy() {
    super.onDestroy();
  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
