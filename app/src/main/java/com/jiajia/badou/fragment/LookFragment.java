package com.jiajia.badou.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.MainActivity;
import com.jiajia.badou.adapter.StoreFragmentAdapter;
import com.jiajia.badou.util.Strings;
import com.jiajia.badou.util.ToastUtil;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/1/30.
 * 发现
 */
public class LookFragment extends BaseFragment {

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
  private List<String> texts;

  private String titleType;

  private Handler handler = new Handler();

  public static LookFragment newInstance() {
    return new LookFragment();
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main_look, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

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

    initChongQuanView();

    initKePuView();
  }

  private void initKePuView() {
    kePuAdapter = new StoreFragmentAdapter(getActivity(), new ArrayList<String>());

    recyclerKePu.setHasFixedSize(true);
    recyclerKePu.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerKePu.setAdapter(kePuAdapter);

    kePuAdapter.addAll(texts);
    kePuAdapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            ToastUtil.showToast(getActivity(), texts.get(position), false);
          }
        });
  }

  private void initChongQuanView() {
    chongQuanAdapter = new StoreFragmentAdapter(getActivity(), new ArrayList<String>());

    View headerView = LayoutInflater.from(getActivity())
        .inflate(R.layout.layout_header_fragment_look_chong_quan,
            recyclerChongQuan.getHeaderContainer(), false);

    ImageView imgChongQuanTitle = headerView.findViewById(R.id.img_fragment_look_chong_quan_title);

    Glide.with(getActivity())
        .load("http://img.zcool.cn/community/0105fd554299f20000019ae9bc8c43.jpg@2o.jpg")
        .error(R.mipmap.ic_launcher)
        .into(imgChongQuanTitle);

    recyclerChongQuan.setHasFixedSize(true);
    recyclerChongQuan.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerChongQuan.setAdapter(chongQuanAdapter);

    recyclerChongQuan.addHeaderView(headerView);

    chongQuanAdapter.addAll(texts);

    chongQuanAdapter.setStoreFragmentAdapterCallBack(
        new StoreFragmentAdapter.StoreFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            ToastUtil.showToast(getActivity(), texts.get(position), false);
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
}
