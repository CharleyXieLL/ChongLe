package com.jiajia.badou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.allen.library.SuperTextView;
import com.jiajia.badou.R;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.util.EditTextUtil;
import com.jiajia.badou.util.InterfaceUtil;
import com.jiajia.badou.view.ExitView;
import com.jiajia.presenter.bean.login.LoginSuccessBean;
import com.jiajia.presenter.modle.login.LoginMvpView;
import com.jiajia.presenter.modle.login.LoginPresenter;
import com.jiajia.presenter.util.StatusBarUtils;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2018/2/28.
 * 登录界面
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginMvpView {

  public static final String TAG = "LoginActivity";

  public static final String PHONE_NUMBER = "phone_number";

  @BindView(R.id.edit_login_phone) EditText editPhone;
  @BindView(R.id.edit_login_password) EditText editPassword;
  @BindView(R.id.super_tv_login_submit) SuperTextView superTvSubmit;
  @BindView(R.id.tv_login_register) TextView tvRegister;
  @BindView(R.id.img_login_delete_phone) ImageView imgDeletePhone;
  @BindView(R.id.img_login_delete_password) ImageView imgDeletePassword;
  @BindView(R.id.relat_login_show_close_password) RelativeLayout layoutClosePassword;
  @BindView(R.id.img_show_close_password) ImageView imgClosePassword;
  @BindView(R.id.tv_login_forget_password) TextView tvForgetPassword;

  private boolean isShowPassword = false;

  private String phoneNumber;
  private String password;

  public static Intent callIntent(Context context) {
    return new Intent(context, LoginActivity.class);
  }

  public static Intent callIntent(Context context, String phoneNumber) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.putExtra(PHONE_NUMBER, phoneNumber);
    return intent;
  }

  public static Intent getBroadcastIntent(String phoneNumber) {
    Intent intent = new Intent(TAG);
    intent.putExtra(PHONE_NUMBER, phoneNumber);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    setPresenter(new LoginPresenter());
    setStatusBar();
    initEditText();
    initIntentData();
    initBroadcastReceiver();
  }

  private void initBroadcastReceiver() {
    LocalBroadcastManager.getInstance(this)
        .registerReceiver(broadcastReceiver, new IntentFilter(TAG));
  }

  private void initIntentData() {
    phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
    if (!Strings.isNullOrEmpty(phoneNumber)) {
      editPhone.setText(phoneNumber);
      editPhone.setSelection(phoneNumber.length());
    }
  }

  private void initEditText() {
    EditTextUtil.setDeleteEditTextAddTextChanged(imgDeletePhone, editPhone);
    EditTextUtil.setDeleteEditTextAddTextChanged(imgDeletePassword, editPassword);
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

  private boolean login() {
    phoneNumber = editPhone.getText().toString();
    if (Strings.isNullOrEmpty(phoneNumber)) {
      ToastUtil.showToast(getApplicationContext(), "请输入手机号", false);
      return false;
    }
    if (phoneNumber.length() < 11) {
      ToastUtil.showToast(getApplicationContext(), "请将手机号输入完整", false);
      return false;
    }
    if (!EditTextUtil.isChinaPhoneLegal(phoneNumber)) {
      ToastUtil.showToast(getApplicationContext(), "请输入合法的中国大陆手机号", false);
      return false;
    }
    password = editPassword.getText().toString();
    if (Strings.isNullOrEmpty(password)) {
      ToastUtil.showToast(getApplicationContext(), "请输入密码", false);
      return false;
    }
    return true;
  }

  @OnClick({
      R.id.super_tv_login_submit, R.id.tv_login_register, R.id.relat_login_show_close_password,
      R.id.tv_login_forget_password
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.super_tv_login_submit:
        if (login()) {
          getPresenter().checkAccountCanRegister(phoneNumber);
        }
        break;
      case R.id.tv_login_register:
        startActivity(RegisterActivity.callIntent(LoginActivity.this));
        break;
      case R.id.relat_login_show_close_password:
        if (isShowPassword) {
          imgClosePassword.setBackgroundResource(R.mipmap.yq_closepassword);
          editPassword.setInputType(
              InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          editPassword.setKeyListener(new DigitsKeyListener() {
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
          imgClosePassword.setBackgroundResource(R.mipmap.yq_openpassword);
          editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
          editPassword.setKeyListener(new DigitsKeyListener() {
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
      case R.id.tv_login_forget_password:
        startActivity(ResetLoginPasswordActivity.callIntent(LoginActivity.this));
        break;
      default:
        break;
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    LocalBroadcastManager.getInstance(activity).unregisterReceiver(broadcastReceiver);
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    ToastUtil.showToast(getApplicationContext(), msg, false);
  }

  @Override public void loginSuccess(LoginSuccessBean loginSuccessBean) {
    BaseSharedDataUtil.setUserId(getApplicationContext(), loginSuccessBean.getId());
    startActivity(MainActivity.callIntent(activity));
    finish();
  }

  @Override public void checkAccountSuccess() {
    dismissLoadingDialog();
    ExitView exitView = new ExitView(activity);
    exitView.setMsg("当前账号未注册\n是否前去注册？");
    exitView.setExitViewCallBack(new InterfaceUtil.ExitViewCallBack() {
      @Override public void exitViewLoginOut() {
        dismissLoadingDialog();
        startActivity(RegisterActivity.callIntent(activity, phoneNumber));
      }

      @Override public void exitNegative() {
      }
    });
    exitView.showDialog();
  }

  @Override public void checkAccountFailed() {
    dismissLoadingDialog();
    getPresenter().login(phoneNumber, password);
  }

  BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      String phoneNumber = intent.getStringExtra(PHONE_NUMBER);
      if (!Strings.isNullOrEmpty(phoneNumber)) {
        editPhone.setText(phoneNumber);
        editPhone.setSelection(phoneNumber.length());
      }
    }
  };
}
