package com.jiajia.badou.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.PetPrimpAdapter;
import com.jiajia.badou.util.ActManager;
import com.jiajia.badou.view.hfrecycler.HeaderAndFooterRecyclerView;
import com.jiajia.presenter.bean.PetPrimpBean;
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

  private static final String FROM = "from";

  @BindView(R.id.relat_pet_primp_address_type) RelativeLayout relatAddressType;
  @BindView(R.id.relat_pet_primp_intelligent_sort) RelativeLayout relatIntelligentSort;
  @BindView(R.id.recycler_pet_primp) HeaderAndFooterRecyclerView recyclerView;
  @BindView(R.id.api_base_title) TextView apiBaseTitle;

  private List<PetPrimpBean> petPrimpBeans = new ArrayList<>();

  private PetPrimpAdapter petPrimpAdapter;

  private String from;

  public static Intent callIntent(Context context, String type) {
    Intent intent = new Intent(context, PetPrimpActivity.class);
    intent.putExtra(FROM, type);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActManager.getAppManager().add(this);
  }

  @Override protected int onCreateViewTitleId() {
    return R.string.cl_title_pet_primp;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_pet_primp;
  }

  @Override protected void init() {
    getIntentData();
  }

  @SuppressLint("SetTextI18n") private void getIntentData() {
    from = getIntent().getStringExtra(FROM);
    apiBaseTitle.setText("宠物" + from);
    initListView();
  }

  @Override protected Presenter returnPresenter() {
    return new PetPrimpPresenter();
  }

  private void initListView() {
    petPrimpAdapter = new PetPrimpAdapter(activity, new ArrayList<PetPrimpBean>(), from);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(activity));

    recyclerView.setAdapter(petPrimpAdapter);

    petPrimpAdapter.setPetPrimpAdapterCallBack(new PetPrimpAdapter.PetPrimpAdapterCallBack() {
      @Override public void onClick(int position) {
        startActivity(
            PetPrimpDetailActivity.callIntent(activity, from, petPrimpBeans.get(position)));
      }
    });

    showLoadingDialog("");
    getPresenter().selectAllStoreMess();
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

  @Override public void selectAllStoreMess(List<PetPrimpBean> petPrimpBeans) {
    dismissLoadingDialog();
    this.petPrimpBeans.clear();
    this.petPrimpBeans.addAll(petPrimpBeans);
    petPrimpAdapter.clear();
    petPrimpAdapter.addAll(petPrimpBeans);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ActManager.getAppManager().remove(this);
  }
}
