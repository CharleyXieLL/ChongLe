package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.allen.library.SuperTextView;
import com.jiajia.badou.R;
import com.jiajia.badou.util.EditTextUtil;
import com.jiajia.badou.util.GetVerifyCodeTimerUtil;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.login.ForgetPasswordMvpView;
import com.jiajia.presenter.modle.login.ForgetPasswordPresenter;
import com.jiajia.presenter.util.StatusBarUtils;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2018/3/1.
 * 重置登录密码
 */
public class ResetLoginPasswordActivity extends BaseActivity<ForgetPasswordPresenter>
    implements ForgetPasswordMvpView {

  public static final String PHONE_NUMBER = "phone_number";

  @BindView(R.id.layout_reset_pwd_back) RelativeLayout layoutPwdBack;
  @BindView(R.id.edit_reset_pwd_phone) EditText editPwdPhone;
  @BindView(R.id.img_reset_pwd_delete_phone) ImageView imgPwdDeletePhone;
  @BindView(R.id.edit_reset_pwd_verify_code) EditText editPwdVerifyCode;
  @BindView(R.id.tv_reset_pwd_verify_code) TextView tvPwdVerifyCode;
  @BindView(R.id.edit_reset_pwd_password) EditText editPwdPassword;
  @BindView(R.id.img_reset_pwd_delete_password) ImageView imgPwdDeletePassword;
  @BindView(R.id.img_show_close_password) ImageView imgShowClosePassword;
  @BindView(R.id.relat_reset_pwd_show_close_password) RelativeLayout relatPwdShowClosePassword;
  @BindView(R.id.super_tv_reset_pwd_submit) SuperTextView superTvPwdSubmit;

  private GetVerifyCodeTimerUtil getVerifyCodeTimerUtil;

  private boolean isShowPassword = false;
  private boolean isPassVerify;

  private String phoneNumber;
  private String verifyCode;
  private String password;

  private Handler verifyCodeHandler = new Handler();

  public static Intent callIntent(Context context) {
    return new Intent(context, ResetLoginPasswordActivity.class);
  }

  public static Intent callIntent(Context context, String phoneNumber) {
    Intent intent = new Intent(context, ResetLoginPasswordActivity.class);
    intent.putExtra(PHONE_NUMBER, phoneNumber);
    return intent;
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_forget_password;
  }

  @Override protected void init() {
    setStatusBar();
    initGetVerifyCodeTimeUtil();
    initEditText();
    initIntentData();
  }

  @Override protected Presenter returnPresenter() {
    return new ForgetPasswordPresenter();
  }

  private void initIntentData() {
    phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
    if (!Strings.isNullOrEmpty(phoneNumber)) {
      editPwdPhone.setText(phoneNumber);
      editPwdPhone.setSelection(phoneNumber.length());
    }
  }

  private void initEditText() {
    EditTextUtil.setDeleteEditTextAddTextChanged(imgPwdDeletePhone, editPwdPhone);
    EditTextUtil.setDeleteEditTextAddTextChanged(imgPwdDeletePassword, editPwdPassword);
    editPwdPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_NEXT
            || i == EditorInfo.IME_ACTION_DONE
            || i == EditorInfo.IME_ACTION_GO) {
          if (registerSubmit()) {
            showLoadingDialog("");
            getPresenter().updatePassword(phoneNumber);
          }
        }
        return true;
      }
    });
  }

  private void initGetVerifyCodeTimeUtil() {
    getVerifyCodeTimerUtil =
        new GetVerifyCodeTimerUtil(tvPwdVerifyCode, ResetLoginPasswordActivity.this);
    getVerifyCodeTimerUtil.setGetVerifyCodeCallBack(
        new GetVerifyCodeTimerUtil.GetVerifyCodeCallBack() {
          @Override public void onClickListener() {
            phoneNumber = editPwdPhone.getText().toString();
            if (Strings.isNullOrEmpty(phoneNumber)) {
              ToastUtil.showToast(getApplicationContext(), "请将手机号输入完整", false);
              return;
            }
            isPassVerify = true;
            showLoadingDialog("");
            verifyCodeHandler.postDelayed(new Runnable() {
              @Override public void run() {
                dismissLoadingDialog();
                ToastUtil.showToast(getApplicationContext(), "验证码发送成功", false);
                getVerifyCodeTimerUtil.start();
              }
            }, 500);
          }
        });
  }

  private void setStatusBar() {
    setStatusColor(R.color.main_check_true);
    StatusBarUtils.StatusBarLightMode(this);
  }

  private void setStatusColor(int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
      if (StatusBarUtils.FlymeSetStatusBarLightMode(this.getWindow(), true)
          || StatusBarUtils.FlymeSetStatusBarLightMode(this.getWindow(), true)
          || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(color));
      } else {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.yc_black));
      }
    }
  }

  private boolean registerSubmit() {
    phoneNumber = editPwdPhone.getText().toString();
    if (Strings.isNullOrEmpty(phoneNumber)) {
      ToastUtil.showToast(getApplicationContext(), "请将手机号输入完整", false);
      return false;
    }
    verifyCode = editPwdVerifyCode.getText().toString();
    if (Strings.isNullOrEmpty(verifyCode)) {
      ToastUtil.showToast(getApplicationContext(), "请填写验证码", false);
      return false;
    }
    if (!verifyCode.equals("1234")) {
      ToastUtil.showToast(getApplicationContext(), "请填写正确的验证码", false);
      return false;
    }
    password = editPwdPassword.getText().toString();
    if (Strings.isNullOrEmpty(password)) {
      ToastUtil.showToast(getApplicationContext(), "请设置您的密码", false);
      return false;
    }
    if (!isPassVerify) {
      ToastUtil.showToast(getApplicationContext(), "请获取验证码", false);
      return false;
    }
    return true;
  }

  @OnClick({
      R.id.layout_reset_pwd_back, R.id.relat_reset_pwd_show_close_password,
      R.id.super_tv_reset_pwd_submit
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.layout_reset_pwd_back:
        finish();
        break;
      case R.id.relat_reset_pwd_show_close_password:
        if (isShowPassword) {
          imgShowClosePassword.setBackgroundResource(R.mipmap.yq_closepassword);
          editPwdPassword.setInputType(
              InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          editPwdPassword.setKeyListener(new DigitsKeyListener() {
            @Override public int getInputType() {
              return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @NonNull @Override protected char[] getAcceptedChars() {
              return getResources().getString(R.string.yq_login_only_can_input).toCharArray();
            }
          });
          isShowPassword = false;
          return;
        } else {
          imgShowClosePassword.setBackgroundResource(R.mipmap.yq_openpassword);
          editPwdPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
          editPwdPassword.setKeyListener(new DigitsKeyListener() {
            @Override public int getInputType() {
              return InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            }

            @NonNull @Override protected char[] getAcceptedChars() {
              return getResources().getString(R.string.yq_login_only_can_input).toCharArray();
            }
          });
          isShowPassword = true;
        }
        break;
      case R.id.super_tv_reset_pwd_submit:
        if (registerSubmit()) {
          showLoadingDialog("");
          getPresenter().checkAccountCanRegister(phoneNumber);
        }
        break;
    }
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), msg, false);
  }

  @Override public void updatePassword() {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), "密码设置成功", false);
    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
    localBroadcastManager.sendBroadcast(LoginActivity.getBroadcastIntent(phoneNumber));
    finish();
  }

  @Override public void checkAccountCanRegister() {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), "当前账号未被注册！", false);
  }

  @Override public void checkAccountCanRegisterFailed() {
    getPresenter().updatePassword(phoneNumber);
  }
}
