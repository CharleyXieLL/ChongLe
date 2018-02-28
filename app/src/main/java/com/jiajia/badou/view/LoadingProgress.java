package com.jiajia.badou.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.github.ybq.android.spinkit.style.Circle;
import com.jiajia.badou.R;
import com.jiajia.badou.util.Strings;

public class LoadingProgress {

  private Circle circle;
  private Dialog progress;
  private TextView tvText;

  public LoadingProgress() {

  }

  public Dialog createDialog(Activity activity) {
    if (progress == null) {
      progress = new Dialog(activity, R.style.CustomProgressDialog);
      progress.setContentView(R.layout.loading_pro);
      progress.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    TextView tvLoadingmsg = progress.findViewById(R.id.id_tv_loadingmsg);
    tvText = progress.findViewById(R.id.id_tv_text);

    if (circle == null) {
      circle = new Circle();
      circle.setBounds(0, 0, 100, 100);
      circle.setColor(activity.getResources().getColor(R.color.yq_white));
    }
    tvLoadingmsg.setCompoundDrawables(null, null, circle, null);
    return progress;
  }

  /**
   * [Summary]
   * setTitile 标题
   */
  public LoadingProgress setTitile(LoadingProgress progress, String strTitle) {
    // do set text
    return progress;
  }

  public void onStartCircle() {
    if (circle != null) {
      circle.start();
    }
  }

  public void onStopCircle() {
    if (circle != null) {
      circle.stop();
    }
  }

  /**
   * [Summary]
   * setMessage 提示内容
   */
  public void setMessage(String strMessage) {
    if (tvText != null) {
      if (Strings.isNullOrEmpty(strMessage)) {
        tvText.setVisibility(View.GONE);
      } else {
        tvText.setText(strMessage);
        tvText.setVisibility(View.VISIBLE);
      }
    }
  }
}