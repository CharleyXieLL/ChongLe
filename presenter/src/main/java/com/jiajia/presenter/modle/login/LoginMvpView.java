package com.jiajia.presenter.modle.login;

import com.jiajia.presenter.bean.login.LoginSuccessBean;
import com.jiajia.presenter.impl.MvpView;

/**
 * Created by Lei on 2018/3/1.
 */
public interface LoginMvpView extends MvpView {
  void loginSuccess(LoginSuccessBean loginSuccessBean);
  void checkAccountSuccess();
  void checkAccountFailed();
}
