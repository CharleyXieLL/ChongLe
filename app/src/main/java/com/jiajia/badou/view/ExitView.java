package com.jiajia.badou.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.jiajia.badou.R;
import com.jiajia.badou.util.InterfaceUtil;
import com.jiajia.badou.util.NetStateUtil;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2017/2/24 0024.
 */

public class ExitView implements View.OnClickListener {

  private Dialog dialog;

  private Context sContext;

  private String tips;

  private InterfaceUtil.ExitViewCallBack exitViewCallBack;
  private TextView positive;
  private TextView negative;
  private TextView quit_message;

  public ExitView(Context sContext) {
    this.sContext = sContext;
    createDialog();
  }

  private void createDialog() {
    dialog = new Dialog(sContext);
    dialog.setCancelable(false);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(R.layout.yq_cusview_exit);

    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.getDecorView().setPadding(0, 0, 0, 0);

    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    window.setAttributes(lp);

    quit_message = dialog.findViewById(R.id.quit_message);

    positive = dialog.findViewById(R.id.positive);
    negative = dialog.findViewById(R.id.negative);


    positive.setOnClickListener(this);
    negative.setOnClickListener(this);
  }

  public void setMsg(String text){
    this.tips = text;
    quit_message.setText(tips);
  }

  public void showDialog() {
    if (dialog != null && !dialog.isShowing()) {
      dialog.show();
    }
  }

  public void dismissDialog() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  public void setNegativeText(String text) {
    negative.setText(text);
  }

  public void setPositiveText(String text) {
    positive.setText(text);
  }

  @Override public void onClick(View v) {
    int viewId = v.getId();
    if (viewId == R.id.negative) {
      dialog.dismiss();
      exitViewCallBack.exitNegative();
    }
    if (!NetStateUtil.isConnected(sContext)) {
      ToastUtil.showToast(sContext, R.string.yq_network_error, false);
      return;
    }
    if (viewId == R.id.positive) {
      exitViewCallBack.exitViewLoginOut();
    }
  }

  public void setExitViewCallBack(InterfaceUtil.ExitViewCallBack exitViewCallBack) {
    this.exitViewCallBack = exitViewCallBack;
  }
}