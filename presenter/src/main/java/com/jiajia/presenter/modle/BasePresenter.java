package com.jiajia.presenter.modle;

import android.app.Activity;
import com.jiajia.presenter.impl.MvpView;
import com.jiajia.presenter.impl.Presenter;

/**
 * Created by Lei on 2018/3/1.
 */
public abstract class BasePresenter<V extends MvpView> implements Presenter<V> {

  private V view;

  protected V getView() {
    return view;
  }

  private Activity activity;

  protected Activity getActivity() {
    return activity;
  }

  @Override public void attachView(V mvpView) {
    view = mvpView;
  }

  @Override public void attachActivity(Activity a) {
    activity = a;
  }
}
