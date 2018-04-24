package com.jiajia.presenter.modle.mine;

import com.jiajia.presenter.bean.mine.SelectAllOrderBean;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionArray;
import com.jiajia.presenter.net.gson.Result;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 */
public class SelectAllOrderPresenter extends BasePresenter<SelectAllOrderMvpView> {

  public SelectAllOrderPresenter() {

  }

  public void selectAllOrderByCustomer(int userId) {
    new OkGoHttpActionArray<SelectAllOrderBean>() {
      @Override
      public void onResponseCodeSuccess(Result<List<SelectAllOrderBean>> result, String msg,
          String code) throws Exception {
        getView().selectAllOrderByCustomer(result.getData());
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGetArray(getActivity(), HttpUtil.selectAllOrderByCustomer(userId),
        SelectAllOrderBean.class);
  }

  public void selectPetsByOwner(int userId) {
    new OkGoHttpActionArray<SelectPetsByOwnerBean>() {
      @Override
      public void onResponseCodeSuccess(Result<List<SelectPetsByOwnerBean>> result, String msg,
          String code) throws Exception {
        getView().selectPetsByOwner(result.getData());
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGetArray(getActivity(), HttpUtil.selectPetsByOwner(userId), SelectPetsByOwnerBean.class);
  }
}
