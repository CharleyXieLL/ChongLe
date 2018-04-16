package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.PetPrimpAdapter;
import com.jiajia.badou.bean.PetPrimpBean;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.primp.PetPrimpMvpView;
import com.jiajia.presenter.modle.primp.PetPrimpPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/3/13.
 * 宠物美容
 */
public class PetPrimpActivity extends BaseActivity<PetPrimpPresenter> implements PetPrimpMvpView {

  @BindView(R.id.relat_pet_primp_address_type) RelativeLayout relatAddressType;
  @BindView(R.id.relat_pet_primp_intelligent_sort) RelativeLayout relatIntelligentSort;
  @BindView(R.id.recycler_pet_primp) HeaderAndFooterRecyclerView recyclerView;

  private List<PetPrimpBean> petPrimpBeans = new ArrayList<>();

  private PetPrimpAdapter petPrimpAdapter;

  public static Intent callIntent(Context context) {
    return new Intent(context, PetPrimpActivity.class);
  }

  @Override protected int onCreateViewTitleId() {
    return R.string.cl_title_pet_primp;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_pet_primp;
  }

  @Override protected void init() {
    initListView();
  }

  @Override protected Presenter returnPresenter() {
    return new PetPrimpPresenter();
  }

  private void initListView() {
    petPrimpAdapter = new PetPrimpAdapter(activity, new ArrayList<PetPrimpBean>());
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    recyclerView.setAdapter(petPrimpAdapter);

    petPrimpAdapter.setPetPrimpAdapterCallBack(new PetPrimpAdapter.PetPrimpAdapterCallBack() {
      @Override public void onClick(int position) {
        startActivity(PetPrimpDetailActivity.callIntent(activity));
      }
    });

    getData();
  }

  private void getData() {
    petPrimpBeans.clear();
    for (int i = 0; i < 10; i++) {
      PetPrimpBean petPrimpBean =
          new PetPrimpBean("", "宠乐宠物美容中心", "杭州市西湖区紫霞街80号", "西湖区", "13897362748", "1.8km");
      petPrimpBeans.add(petPrimpBean);
    }
    petPrimpAdapter.addAll(petPrimpBeans);
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }

  @OnClick({ R.id.relat_pet_primp_address_type, R.id.relat_pet_primp_intelligent_sort })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.relat_pet_primp_address_type:
        break;
      case R.id.relat_pet_primp_intelligent_sort:
        break;
    }
  }
}
