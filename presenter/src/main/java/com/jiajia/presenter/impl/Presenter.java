package com.jiajia.presenter.impl;

import android.app.Activity;

/**
 * Created by Lei on 2018/3/1.
 */
public interface Presenter<V extends MvpView> {
  void attachView(V mvpView);

  void attachActivity(Activity activity);
}
