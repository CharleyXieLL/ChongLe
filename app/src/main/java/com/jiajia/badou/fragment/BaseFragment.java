package com.jiajia.badou.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import com.jiajia.badou.activity.MainActivity;

/**
 * Created by Lei on 2018/2/7.
 */
public class BaseFragment extends Fragment {

  public static final String SHOW_LOADING = "show_loading";
  public static final String SHOW = "show";
  public static final String DISMISS = "dismiss";
  public static final String LOADING_TEXT = "loading_text";

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
}
