package com.jiajia.badou.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.CommonWebViewActivity;
import com.jiajia.badou.activity.MainActivity;
import com.jiajia.badou.adapter.ChongQuanAdapter;
import com.jiajia.badou.adapter.MainPageFragmentAdapter;
import com.jiajia.badou.bean.ChongQuanBean;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.bean.main.MainPageFragmentListBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.main.LookFragmentMvpView;
import com.jiajia.presenter.modle.main.LookFragmentPresenter;
import com.jiajia.presenter.util.Strings;
import java.util.ArrayList;
import java.util.Arrays;
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

  private ChongQuanAdapter chongQuanAdapter;
  private MainPageFragmentAdapter kePuAdapter;
  private List<MainPageFragmentListBean> mainPageFragmentListBeans = new ArrayList<>();
  private List<ChongQuanBean> chongQuanBeans = new ArrayList<>();

  private String titleType;

  private Handler handler = new Handler();

  public static LookFragment newInstance() {
    return new LookFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_look;
  }

  @Override protected void init() {

    initKePuData();

    initChongQuanData();

    initChongQuanView();

    initKePuView();
  }

  @Override protected Presenter returnPresenter() {
    return new LookFragmentPresenter();
  }

  private void initKePuData() {
    mainPageFragmentListBeans.clear();
    mainPageFragmentListBeans.add(new MainPageFragmentListBean(
        "http://www.ygaiquan.com/UploadFiles/2017/2/2017022010251082838.jpg", "张姐姐教你三大技巧让宠物按时吃饭。",
        "张姐姐", "253"));
    mainPageFragmentListBeans.add(new MainPageFragmentListBean(
        "http://n.sinaimg.cn/tech/transform/20170127/SZqG-fxzyxnu9471294.jpg", "如何教一只恶霸犬能顺从的吃药。",
        "巴豆粑粑", "163"));
    mainPageFragmentListBeans.add(
        new MainPageFragmentListBean("http://pic.sc.chinaz.com/files/pic/pic9/201401/apic3177.jpg",
            "如何给心爱的宝宝制作精美的住所。", "小楠", "89"));
    mainPageFragmentListBeans.add(
        new MainPageFragmentListBean("http://s1.1zoom.me/big3/945/367740-svetik.jpg",
            "给宝宝吃这些的时候千万要注意以下几点。", "爱上茶", "353"));
    mainPageFragmentListBeans.add(
        new MainPageFragmentListBean("http://vistanews.ru/uploads/posts/2016-01/1451920325_8.jpg",
            "换季宝宝容易生病不用怕，我来支招。", "取个名字", "532"));
    mainPageFragmentListBeans.add(new MainPageFragmentListBean(
        "https://1gr.cz/fotky/idnes/09/104/cl5/MCE2ecb31_shutterstock_23472034.jpg",
        "嫌宝宝太笨？不，是你没用对方法。", "三三两两", "35"));
    mainPageFragmentListBeans.add(new MainPageFragmentListBean(
        "https://avatars.mds.yandex.net/get-pdb/34158/426e4077-628f-4ac0-b0ed-1c3134180c3c/s800",
        "葛大爷手把手教授让宝宝学会自己上厕所。", "葛大爷", "278"));
    mainPageFragmentListBeans.add(
        new MainPageFragmentListBean("http://img1.iyiou.com/Cover/2015-04-07/552338cb710a0.jpg",
            "出门遛狗你必须要知道的几点。", "李爱琴", "137"));
  }

  private void initChongQuanData() {
    chongQuanBeans.clear();
    chongQuanBeans.add(
        new ChongQuanBean(R.mipmap.cl_chongquan_01, "巴豆霸霸", "2017.08.04", R.mipmap.cl_chongquan_01,
            "哇，你竟然敢咬我，等我把手拿出来给你点颜色看看！", 90, 56, true));
    chongQuanBeans.add(
        new ChongQuanBean(R.mipmap.cl_chongquan_02, "我是仙女", "2017.10.23", R.mipmap.cl_chongquan_02,
            "喝水的杯子竟然被你当作游乐场，哎，谁叫你是我的小心肝儿呢。", 560, 498, true));
    chongQuanBeans.add(
        new ChongQuanBean(R.mipmap.cl_chongquan_03, "宠乐佳佳", "2017.11.14", R.mipmap.cl_chongquan_03,
            "请忽略这个标记，没错，我就是当警犬养的，我们家的欧巴。", 399, 370, true));
    chongQuanBeans.add(
        new ChongQuanBean(R.mipmap.cl_chongquan_04, "我叫不帅", "2018.03.17", R.mipmap.cl_chongquan_04,
            "当了妈妈之后才知道生孩子的辛苦，辛苦了，我的小宝贝儿。", 875, 679, true));
  }

  private void initKePuView() {
    final List<String> urls = Arrays.asList(getResources().getStringArray(R.array.kepu_url));
    kePuAdapter = new MainPageFragmentAdapter(activity, new ArrayList<MainPageFragmentListBean>());

    recyclerKePu.setHasFixedSize(true);
    recyclerKePu.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerKePu.setAdapter(kePuAdapter);

    kePuAdapter.addAll(mainPageFragmentListBeans);
    kePuAdapter.setMainPageFragmentAdapterCallBack(
        new MainPageFragmentAdapter.MainPageFragmentAdapterCallBack() {
          @Override public void onClick(int position) {
            startActivity(CommonWebViewActivity.callIntent(activity, urls.get(position)));
          }
        });
  }

  private void initChongQuanView() {
    chongQuanAdapter = new ChongQuanAdapter(activity, new ArrayList<ChongQuanBean>());

    View headerView = LayoutInflater.from(getActivity())
        .inflate(R.layout.layout_header_fragment_look_chong_quan,
            recyclerChongQuan.getHeaderContainer(), false);

    recyclerChongQuan.setHasFixedSize(true);
    recyclerChongQuan.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerChongQuan.setAdapter(chongQuanAdapter);

    recyclerChongQuan.addHeaderView(headerView);

    chongQuanAdapter.addAll(chongQuanBeans);
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
