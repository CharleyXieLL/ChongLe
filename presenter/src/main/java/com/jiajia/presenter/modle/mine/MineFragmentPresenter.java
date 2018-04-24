package com.jiajia.presenter.modle.mine;

import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionArray;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;
import com.jiajia.presenter.net.gson.Result;
import java.util.List;

/**
 * Created by Lei on 2018/3/9.
 */
public class MineFragmentPresenter extends BasePresenter<MineFragmentMvpView> {

  public MineFragmentPresenter() {

  }

  public void selectById() {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {

      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {

      }
    }.okGoGet(getActivity(), HttpUtil.selectById(20));
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
