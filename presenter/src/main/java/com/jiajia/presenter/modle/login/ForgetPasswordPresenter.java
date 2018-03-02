package com.jiajia.presenter.modle.login;

import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;

/**
 * Created by Lei on 2018/3/2.
 */
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordMvpView> {

  public ForgetPasswordPresenter() {
  }

  public void updatePassword(String userName) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().updatePassword();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGet(getActivity(), HttpUtil.updatePassword(userName));
  }

  public void checkAccountCanRegister(String userName) {
    new OkGoHttpActionNoBean() {
      @Override public void onResponseCodeSuccess(String msg, String code) throws Exception {
        getView().checkAccountCanRegister();
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().checkAccountCanRegisterFailed();
      }
    }.okGoGet(getActivity(), HttpUtil.checkCanRegister(userName));
  }
}
