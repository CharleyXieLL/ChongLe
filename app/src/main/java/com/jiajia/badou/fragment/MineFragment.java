package com.jiajia.badou.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.activity.PetCompileActivity;
import com.jiajia.badou.activity.UserCompileActivity;
import com.jiajia.badou.mine.Align;
import com.jiajia.badou.mine.Config;
import com.jiajia.badou.mine.StackAdapter;
import com.jiajia.badou.mine.StackLayoutManager;
import com.jiajia.badou.view.BigAvatarView;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/1/30.
 * 个人中心
 */
public class MineFragment extends BaseFragment {

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

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main_mine, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initUi();
    initBigAvatarView();
  }

  private void initBigAvatarView() {
    bigAvatarView = new BigAvatarView(getActivity());
  }

  private void initUi() {
    List<String> datas = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      datas.add(String.valueOf(i));
    }
    Config config = new Config();
    config.secondaryScale = 0.8f;
    config.scaleRatio = 0.4f;
    config.maxStackCount = 4;
    config.initialStackCount = 2;
    config.space = 15;
    config.align = Align.LEFT;
    recyclerView.setLayoutManager(new StackLayoutManager(config));
    recyclerView.setAdapter(new StackAdapter(datas));
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
}
