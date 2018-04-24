package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.InsertOrderPost;
import com.jiajia.presenter.bean.PetPrimpDetailListBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionArray;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;
import com.jiajia.presenter.net.gson.Result;
import java.util.List;

/**
 * Created by Lei on 2018/3/15.
 */
public class PetPrimpDetailPresenter extends BasePresenter<PetPrimpDetailMvpView> {

  public PetPrimpDetailPresenter() {

  }

  public void selectAllService() {
    new OkGoHttpActionArray<PetPrimpDetailListBean>() {
      @Override
      public void onResponseCodeSuccess(Result<List<PetPrimpDetailListBean>> result, String msg,
          String code) throws Exception {
        getView().selectAllService(result.getData());
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGetArray(getActivity(), HttpUtil.selectAllService(), PetPrimpDetailListBean.class);
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
