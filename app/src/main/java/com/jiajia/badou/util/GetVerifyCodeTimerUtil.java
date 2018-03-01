package com.jiajia.badou.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.jiajia.badou.R;

/**
 * Created by Lei on 2017/6/16.
 * 获取验证码按钮 Timer
 */

public class GetVerifyCodeTimerUtil implements View.OnClickListener {

  private final static int MINUTE = 60000;

  private final static int SECOND = 1000;

  private GetVerifyCodeCallBack getVerifyCodeCallBack;

  private TextView tvGetVerifyCode;

  private Activity activity;

  public GetVerifyCodeTimerUtil(TextView tvGetVerifyCode, Activity activity) {
    this.tvGetVerifyCode = tvGetVerifyCode;
    this.activity = activity;
    tvGetVerifyCode.setOnClickListener(this);
  }

  /**
   * 开始倒计时
   */
  public void start() {
    cuntDown.start();
    tvGetVerifyCode.setEnabled(false);
    tvGetVerifyCode.setTextColor(ContextCompat.getColor(activity, R.color.yq_color_a0a0a0));
    tvGetVerifyCode.setTextSize(18);
  }

  /**
   * 设置获取验证码60s倒计时
   */
  private CountDownTimer cuntDown = new CountDownTimer(MINUTE, SECOND) {
    @SuppressLint("SetTextI18n") @Override public void onTick(long millisUntilFinished) {
      if (tvGetVerifyCode != null) {
        tvGetVerifyCode.setText(millisUntilFinished / SECOND + "");
      }
    }

    @Override public void onFinish() {
      cutDownFinish();
    }
  };

  public void cutDownFinish() {
    tvGetVerifyCode.setText("获取验证码");
    tvGetVerifyCode.setEnabled(true);
    tvGetVerifyCode.setTextSize(16);
    tvGetVerifyCode.setTextColor(ContextCompat.getColor(activity, R.color.main_check_true));
    cutFinish();
  }

  /**
   * 结束倒计时
   */
  private void cutFinish() {
    if (cuntDown != null) {
      cuntDown.cancel();
    }
  }

  @Override public void onClick(View v) {
    getVerifyCodeCallBack.onClickListener();
  }

  public interface GetVerifyCodeCallBack {
    void onClickListener();
  }

  public void setGetVerifyCodeCallBack(GetVerifyCodeCallBack getVerifyCodeCallBack) {
    this.getVerifyCodeCallBack = getVerifyCodeCallBack;
  }
}
