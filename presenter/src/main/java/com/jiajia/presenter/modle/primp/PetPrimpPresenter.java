package com.jiajia.presenter.modle.primp;

import com.jiajia.presenter.bean.PetPrimpBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionArray;
import com.jiajia.presenter.net.gson.Result;
import java.util.List;

/**
 * Created by Lei on 2018/3/13.
 */
public class PetPrimpPresenter extends BasePresenter<PetPrimpMvpView> {

  public PetPrimpPresenter() {
  }

  public void selectAllStoreMess() {
    new OkGoHttpActionArray<PetPrimpBean>() {
      @Override
      public void onResponseCodeSuccess(Result<List<PetPrimpBean>> result, String msg, String code)
          throws Exception {
        getView().selectAllStoreMess(result.getData());
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGetArray(getActivity(), HttpUtil.selectAllStoreMess(), PetPrimpBean.class);
  }
}
