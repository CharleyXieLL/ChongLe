package com.jiajia.badou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.jiajia.badou.activity.MainActivity;
import com.jiajia.presenter.impl.MvpView;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lei on 2018/2/7.
 */
public abstract class BaseFragment<P extends Presenter> extends Fragment implements MvpView {

  public static final String SHOW_LOADING = "show_loading";
  public static final String SHOW = "show";
  public static final String DISMISS = "dismiss";
  public static final String LOADING_TEXT = "loading_text";

  protected Activity activity;

  protected EventBus bus;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(onCreateViewId(), container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setPresenter((P) returnPresenter());
    activity = getActivity();
    bus = EventBus.getDefault();
    init();
  }

  protected abstract @LayoutRes int onCreateViewId();

  protected abstract void init();

  public void showLoadingDialog(String text) {
    LocalBroadcastManager localBroadcastManagerKePu =
        LocalBroadcastManager.getInstance(getActivity());
    localBroadcastManagerKePu.sendBroadcast(MainActivity.getShowLoadingIntent(SHOW, text));
  }

  public void dismissLoadingDialog() {
    LocalBroadcastManager localBroadcastManagerKePu =
        LocalBroadcastManager.getInstance(getActivity());
    localBroadcastManagerKePu.sendBroadcast(MainActivity.getShowLoadingIntent(DISMISS, ""));
  }

  public void setPresenter(P presenter) {
    if (this.presenter == null) {
      this.presenter = presenter;
      presenter.attachActivity(activity);
      presenter.attachView(this);
      presenter.attachView(this);
    } else {
      throw new RuntimeException("Never call this method manually!!!");
    }
  }

  protected abstract Presenter returnPresenter();

  private P presenter;

  protected P getPresenter() {
    return presenter;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    presenter = null;
  }

  public void showToast(String text) {
    ToastUtil.showToast(activity.getApplicationContext(), text, false);
  }
}
