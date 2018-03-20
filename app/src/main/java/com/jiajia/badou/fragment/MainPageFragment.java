package com.jiajia.badou.fragment;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.CommonWebViewActivity;
import com.jiajia.badou.activity.PetPrimpActivity;
import com.jiajia.badou.adapter.MainPageFragmentAdapter;
import com.jiajia.badou.bean.event.JumpStoreEvent;
import com.jiajia.badou.view.GlideImageLoader;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.bean.main.MainPageFragmentListBean;
import com.jiajia.presenter.modle.main.MainPageFragmentPresenter;
import com.jiajia.presenter.util.InputMethodUtil;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lei on 2018/1/30.
 * 首页
 */
public class MainPageFragment extends BaseFragment<MainPageFragmentPresenter> {

  @BindView(R.id.fragment_main_page_recycler_view) HeaderAndFooterRecyclerView recyclerView;
  @BindView(R.id.edit_main_page_search) EditText editText;
  @BindView(R.id.tv_main_page_location) TextView tvLocation;

  private Banner banner;
  private MainPageFragmentAdapter adapter;

  private List<MainPageFragmentListBean> mainPageFragmentListBeans = new ArrayList<>();
  private InputMethodUtil inputMethodUtil;

  private Handler handler = new Handler();

  private Handler showLocationHandler = new Handler();

  public static MainPageFragment newInstance() {
    return new MainPageFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_page;
  }

  @Override protected void init() {
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

    initHeaderViewUi(headerView);

    initBanner();

    adapter = new MainPageFragmentAdapter(getActivity(), new ArrayList<MainPageFragmentListBean>());

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    recyclerView.addHeaderView(headerView);

    initListBean();
  }

  private void initHeaderViewUi(View view) {
    ImageView imgStore = view.findViewById(R.id.img_main_service_store);
    ImageView imgMedicalTreatment = view.findViewById(R.id.img_main_service_yiliao);
    ImageView imgPrimp = view.findViewById(R.id.img_main_service_meirong);

    imgStore.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        EventBus.getDefault().post(new JumpStoreEvent());
      }
    });
    imgMedicalTreatment.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
    imgPrimp.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        activity.startActivity(PetPrimpActivity.callIntent(activity));
      }
    });
  }

  private void initListBean() {
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
    adapter.addAll(mainPageFragmentListBeans);
    adapter.setMainPageFragmentAdapterCallBack(
        new MainPageFragmentAdapter.MainPageFragmentAdapterCallBack() {
          @Override public void onClick(int position) {

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

    List<String> imageUrls =
        Arrays.asList("https://s1.1zoom.ru/big0/453/Dogs_Spitz_Winter_hat_471485.jpg",
            "https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike60%2C5%2C5%2C60%2C20/sign=78bde825a586c9171c0e5a6ba8541baa/63d9f2d3572c11df449bb3fe612762d0f603c2a1.jpg",
            "http://img02.tooopen.com/Downs/images/2010/9/9/sy_20100909075330671012.jpg",
            "http://first-vet.bg/wp-content/uploads/2014/09/dogcat23.jpg",
            "http://www.rainbowbridge-pet.com/index/pics/20170223/201702231487847321740.png");

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

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    ToastUtil.showToast(activity.getApplicationContext(), msg, false);
  }
}
