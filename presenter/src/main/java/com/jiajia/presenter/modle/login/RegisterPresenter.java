package com.jiajia.presenter.modle.login;

import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;

/**
 * Created by Lei on 2018/3/2.
 */
public class RegisterPresenter extends BasePresenter<RegisterMvpView> {

  public RegisterPresenter() {
  }

  public void checkAccountCanRegister(String userName) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().checkAccountSuccess();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().checkAccountFailed();
      }
    }.okGoGet(getActivity(), HttpUtil.checkCanRegister(userName));
  }

  public void register(String userName, String password) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().register();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGet(getActivity(), HttpUtil.userRegister(userName, password));
  }
}
