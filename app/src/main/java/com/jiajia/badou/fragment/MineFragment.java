package com.jiajia.badou.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.PetCompileActivity;
import com.jiajia.badou.activity.UserCompileActivity;
import com.jiajia.badou.mine.Align;
import com.jiajia.badou.mine.Config;
import com.jiajia.badou.mine.StackAdapter;
import com.jiajia.badou.mine.StackLayoutManager;
import com.jiajia.badou.view.BigAvatarView;
import com.jiajia.presenter.modle.main.MineFragmentMvpView;
import com.jiajia.presenter.modle.main.MineFragmentPresenter;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lei on 2018/1/30.
 * 个人中心
 */
public class MineFragment extends BaseFragment<MineFragmentPresenter>
    implements MineFragmentMvpView {

  @BindView(R.id.img_mine_fragment_reset) ImageView imgReset;
  @BindView(R.id.img_mine_fragment_avatar) RoundedImageView imgAvatar;
  @BindView(R.id.tv_mine_fragment_my_name) TextView tvMyName;
  @BindView(R.id.tv_mine_fragment_sign) TextView tvSign;
  @BindView(R.id.tv_mine_fragment_order_bill) TextView tvOrderBill;
  @BindView(R.id.tv_mine_fragment_goods_address) TextView tvGoodsAddress;
  @BindView(R.id.tv_mine_fragment_my_pet_reset) TextView tvMyPetReset;
  @BindView(R.id.relat_mine_fragment_reset) RelativeLayout relatReset;

  @BindView(R.id.recycler_mine_fragment_pet_card) RecyclerView recyclerView;

  private BigAvatarView bigAvatarView;

  public static MineFragment newInstance() {
    return new MineFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_mine;
  }

  @Override protected void init() {
    initUi();
    initBigAvatarView();
  }

  private void initBigAvatarView() {
    bigAvatarView = new BigAvatarView(getActivity());
  }

  private void initUi() {
    List<String> imageUrls =
        Arrays.asList("https://s1.1zoom.ru/big0/453/Dogs_Spitz_Winter_hat_471485.jpg",
            "https://gss1.bdstatic.com/-vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike60%2C5%2C5%2C60%2C20/sign=78bde825a586c9171c0e5a6ba8541baa/63d9f2d3572c11df449bb3fe612762d0f603c2a1.jpg",
            "http://img02.tooopen.com/Downs/images/2010/9/9/sy_20100909075330671012.jpg",
            "http://first-vet.bg/wp-content/uploads/2014/09/dogcat23.jpg",
            "http://www.rainbowbridge-pet.com/index/pics/20170223/201702231487847321740.png");
    Config config = new Config();
    config.secondaryScale = 0.8f;
    config.scaleRatio = 0.4f;
    config.maxStackCount = 4;
    config.initialStackCount = 0;
    config.space = 15;
    config.align = Align.LEFT;
    recyclerView.setLayoutManager(new StackLayoutManager(config));
    recyclerView.setAdapter(new StackAdapter(imageUrls));
    Log.i("SSS", "刷新了");
  }

  @OnClick({
      R.id.img_mine_fragment_avatar, R.id.tv_mine_fragment_my_pet_reset,
      R.id.relat_mine_fragment_reset
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_mine_fragment_avatar:
        bigAvatarView.setUrl("");
        break;
      case R.id.tv_mine_fragment_my_pet_reset:
        startActivity(PetCompileActivity.callIntent(getActivity()));
        break;
      case R.id.relat_mine_fragment_reset:
        startActivity(UserCompileActivity.callIntent(getActivity()));
        break;
      default:
        break;
    }
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }
}
