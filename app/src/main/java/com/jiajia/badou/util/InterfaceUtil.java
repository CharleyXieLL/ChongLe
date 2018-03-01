package com.jiajia.badou.util;

import android.app.Dialog;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class InterfaceUtil {
  public interface UpDateProgress {
    void progressUpdate(int progress);
  }

  public interface ExitViewCallBack {
    void exitViewLoginOut();
    void exitNegative();
  }

  public interface PopCallBack {
    void popPositiveCallBack(Dialog dialog);
    void popNegativeCallBack(Dialog dialog);
  }

  public interface UserDataCallBack{
    void userDataIntentActivity();
  }

  public interface RecyclerItemOnClick{
    void onClick(int position);
  }
}