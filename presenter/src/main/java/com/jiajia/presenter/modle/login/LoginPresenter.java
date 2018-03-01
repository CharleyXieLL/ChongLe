package com.jiajia.presenter.modle.login;

import com.jiajia.presenter.bean.login.LoginSuccessBean;
import com.jiajia.presenter.modle.BasePresenter;
import com.jiajia.presenter.net.HttpUtil;
import com.jiajia.presenter.net.OkGoHttpAction;
import com.jiajia.presenter.net.OkGoHttpActionNoBean;
import com.jiajia.presenter.net.gson.Result;

/**
 * Created by Lei on 2018/3/1.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {

  public LoginPresenter() {

  }

  public void login(String userName, String password) {
    new OkGoHttpAction<LoginSuccessBean>() {
      @Override
      public void onResponseCodeSuccess(Result<LoginSuccessBean> result, String msg, String code)
          throws Exception {
        getView().loginSuccess(result.getData());
      }

      @Override public void onResponseCodeFailed(String msg, String code) throws Exception {
        getView().getFailed(msg, code);
      }
    }.okGoGet(getActivity(), HttpUtil.userLogin(userName, password), LoginSuccessBean.class);
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
}
