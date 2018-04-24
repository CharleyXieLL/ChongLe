package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.SelectAllOrderAdapter;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.presenter.bean.mine.SelectAllOrderBean;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.mine.SelectAllOrderMvpView;
import com.jiajia.presenter.modle.mine.SelectAllOrderPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 * 预约订单
 */
public class SelectAllOrderActivity extends BaseActivity<SelectAllOrderPresenter>
    implements SelectAllOrderMvpView {

  @BindView(R.id.recyclerview) RecyclerView recyclerview;
  @BindView(R.id.tv_select_all_order_no_data) TextView tvNoData;

  private List<SelectAllOrderBean> selectAllOrderBeans = new ArrayList<>();
  private List<SelectPetsByOwnerBean> selectPetsByOwnerBeans = new ArrayList<>();

  private SelectAllOrderAdapter selectAllOrderAdapter;

  public static Intent callIntent(Context context) {
    return new Intent(context, SelectAllOrderActivity.class);
  }

  @Override protected int onCreateViewTitleId() {
    return R.string.cl_title_select_all_order;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_select_all_order;
  }

  @Override protected Presenter returnPresenter() {
    return new SelectAllOrderPresenter();
  }

  @Override protected void init() {
    getData();
  }

  private void getData() {
    showLoadingDialog("");
    getPresenter().selectPetsByOwner(BaseSharedDataUtil.getUserId(activity));
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }

  @Override public void selectAllOrderByCustomer(List<SelectAllOrderBean> selectAllOrderBeans) {
    dismissLoadingDialog();
    this.selectAllOrderBeans.clear();
    this.selectAllOrderBeans.addAll(selectAllOrderBeans);
    if (selectAllOrderBeans.size() > 0) {
      recyclerview.setVisibility(View.VISIBLE);
      tvNoData.setVisibility(View.GONE);
    } else {
      recyclerview.setVisibility(View.GONE);
      tvNoData.setVisibility(View.VISIBLE);
    }
    selectAllOrderAdapter = new SelectAllOrderAdapter(activity, new ArrayList<SelectAllOrderBean>(),
        selectPetsByOwnerBeans);

    recyclerview.setLayoutManager(new LinearLayoutManager(activity));
    recyclerview.setHasFixedSize(true);
    recyclerview.setAdapter(selectAllOrderAdapter);

    selectAllOrderAdapter.clear();
    selectAllOrderAdapter.addAll(selectAllOrderBeans);
  }

  @Override public void selectPetsByOwner(List<SelectPetsByOwnerBean> selectPetsByOwnerBeans) {
    dismissLoadingDialog();
    this.selectPetsByOwnerBeans.clear();
    this.selectPetsByOwnerBeans.addAll(selectPetsByOwnerBeans);
    if (selectPetsByOwnerBeans.size() > 0) {
      recyclerview.setVisibility(View.VISIBLE);
      tvNoData.setVisibility(View.GONE);
      getPresenter().selectAllOrderByCustomer(BaseSharedDataUtil.getUserId(activity));
    } else {
      recyclerview.setVisibility(View.GONE);
      tvNoData.setVisibility(View.VISIBLE);
    }
  }
}
