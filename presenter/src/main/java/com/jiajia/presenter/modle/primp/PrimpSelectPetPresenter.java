package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.InsertOrderPost;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionArray;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;
import com.jiajia.presenter.net.gson.Result;
import java.util.List;

/**
 * Created by Lei on 2018/4/24.
 */
public class PrimpSelectPetPresenter extends BasePresenter<PrimpSelectPetMvpView> {

  public PrimpSelectPetPresenter() {

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

  public void insertOrder(InsertOrderPost insertOrderPost) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().insertOrder();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGet(getActivity(), HttpUtil.insertOrder(insertOrderPost));
  }
}
