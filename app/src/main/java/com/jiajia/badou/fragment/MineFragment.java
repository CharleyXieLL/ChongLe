package com.jiajia.badou.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.PetCompileActivity;
import com.jiajia.badou.activity.SelectAllOrderActivity;
import com.jiajia.badou.activity.UserCompileActivity;
import com.jiajia.badou.mine.Align;
import com.jiajia.badou.mine.Config;
import com.jiajia.badou.mine.StackAdapter;
import com.jiajia.badou.mine.StackLayoutManager;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.view.BigAvatarView;
import com.jiajia.badou.view.UserCenterItemView;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.mine.MineFragmentMvpView;
import com.jiajia.presenter.modle.mine.MineFragmentPresenter;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
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
  @BindView(R.id.tv_mine_fragment_my_pet_add) TextView tvMyPetReset;
  @BindView(R.id.relat_mine_fragment_reset) RelativeLayout relatReset;

  @BindView(R.id.relat_mine_add_pet) RelativeLayout relatAddPet;
  @BindView(R.id.img_mine_add_pet) ImageView imgAddPet;
  @BindView(R.id.mine_order_bill) UserCenterItemView mineOrderBill;

  @BindView(R.id.recycler_mine_fragment_pet_card) RecyclerView recyclerView;

  private BigAvatarView bigAvatarView;

  private StackAdapter adapter;

  private List<SelectPetsByOwnerBean> selectPetsByOwnerBeans = new ArrayList<>();

  public static MineFragment newInstance() {
    return new MineFragment();
  }

  @Override protected int onCreateViewId() {
    return R.layout.fragment_main_mine;
  }

  @Override protected void init() {
    initUi();
    initBigAvatarView();
    getData();
  }

  @Override protected Presenter returnPresenter() {
    return new MineFragmentPresenter();
  }

  private void getData() {
    getPresenter().selectById();
    getPresenter().selectPetsByOwner(BaseSharedDataUtil.getUserId(activity));
  }

  private void initBigAvatarView() {
    bigAvatarView = new BigAvatarView(getActivity());
  }

  private void initUi() {
    Config config = new Config();
    config.secondaryScale = 0.9f;
    config.scaleRatio = 0.4f;
    config.maxStackCount = 4;
    config.initialStackCount = 0;
    config.space = 15;
    config.align = Align.LEFT;

    recyclerView.setLayoutManager(new StackLayoutManager(config));

    Glide.with(activity)
        .load(BaseSharedDataUtil.getUserAvatar(activity))
        .error(R.mipmap.yj_defoult_user_head)
        .into(imgAvatar);
  }

  public void onResumeFragment() {
    Glide.with(activity)
        .load(BaseSharedDataUtil.getUserAvatar(activity))
        .error(R.mipmap.yj_defoult_user_head)
        .into(imgAvatar);
  }

  public void getPet() {
    getPresenter().selectPetsByOwner(BaseSharedDataUtil.getUserId(activity));
  }

  @OnClick({
      R.id.img_mine_fragment_avatar, R.id.tv_mine_fragment_my_pet_add,
      R.id.relat_mine_fragment_reset, R.id.mine_order_bill, R.id.img_mine_add_pet
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_mine_fragment_avatar:
        bigAvatarView.setUrl("");
        break;
      case R.id.tv_mine_fragment_my_pet_add:
        startActivity(PetCompileActivity.callIntent(getActivity()));
        break;
      case R.id.relat_mine_fragment_reset:
        startActivity(UserCompileActivity.callIntent(getActivity()));
        break;
      case R.id.img_mine_add_pet:
        startActivity(PetCompileActivity.callIntent(getActivity()));
        break;
      case R.id.mine_order_bill:
        startActivity(SelectAllOrderActivity.callIntent(activity));
        break;
      default:
        break;
    }
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }

  @Override
  public void selectPetsByOwner(final List<SelectPetsByOwnerBean> selectPetsByOwnerBeans) {
    dismissLoadingDialog();
    this.selectPetsByOwnerBeans.clear();
    this.selectPetsByOwnerBeans.addAll(selectPetsByOwnerBeans);
    if (selectPetsByOwnerBeans.size() > 0) {
      relatAddPet.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
      tvMyPetReset.setVisibility(View.VISIBLE);

      adapter = new StackAdapter(selectPetsByOwnerBeans);

      recyclerView.setAdapter(adapter);

      adapter.setStackAdapterCallBack(new StackAdapter.StackAdapterCallBack() {
        @Override public void onStackClick(int position) {
          startActivity(
              PetCompileActivity.callIntent(activity, selectPetsByOwnerBeans.get(position)));
        }
      });
    } else {
      relatAddPet.setVisibility(View.VISIBLE);
      recyclerView.setVisibility(View.GONE);
      tvMyPetReset.setVisibility(View.GONE);
    }
  }
}
