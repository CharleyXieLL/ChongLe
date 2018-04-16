package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.jiajia.badou.view.MessageView;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.login.RegisterMvpView;
import com.jiajia.presenter.modle.login.RegisterPresenter;
import com.jiajia.presenter.util.StatusBarUtils;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2018/2/28.
 * 注册界面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterMvpView {

  private static final String PHONE_NUMBER = "phone_number";

  @BindView(R.id.layout_register_back) RelativeLayout layoutBack;
  @BindView(R.id.edit_register_phone) EditText editPhone;
  @BindView(R.id.edit_register_verify_code) EditText editVerifyCode;
  @BindView(R.id.tv_register_verify_code) TextView tvVerifyCode;
  @BindView(R.id.edit_register_password) EditText editPassword;
  @BindView(R.id.super_tv_register_submit) SuperTextView superTvSubmit;
  @BindView(R.id.img_register_agreement_check) ImageView imgAgreementCheck;
  @BindView(R.id.layout_register_agreement_check) RelativeLayout layoutAgreementCheck;

  @BindView(R.id.img_register_delete_password) ImageView imgDeletePassword;
  @BindView(R.id.img_register_delete_phone) ImageView imgDeletePhoneNumber;

  @BindView(R.id.relat_register_show_close_password) RelativeLayout layoutClosePassword;
  @BindView(R.id.img_show_close_password) ImageView imgClosePassword;

  private GetVerifyCodeTimerUtil getVerifyCodeTimerUtil;

  private boolean isCheckAgreement = false;
  private boolean isShowPassword = false;
  private boolean isPassVerify;

  private String phoneNumber;
  private String verifyCode;
  private String password;

  private Handler verifyCodeHandler = new Handler();

  public static Intent callIntent(Context context) {
    return new Intent(context, RegisterActivity.class);
  }

  public static Intent callIntent(Context context, String phoneNumber) {
    Intent intent = new Intent(context, RegisterActivity.class);
    intent.putExtra(PHONE_NUMBER, phoneNumber);
    return intent;
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_register;
  }

  @Override protected void init() {
    setPresenter(new RegisterPresenter());
    setStatusBar();
    initGetVerifyCodeTimeUtil();
    initEditText();
    initIntentData();
  }

  @Override protected Presenter returnPresenter() {
    return new RegisterPresenter();
  }

  private void initIntentData() {
    phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
    if (!Strings.isNullOrEmpty(phoneNumber)) {
      editPhone.setText(phoneNumber);
      editPhone.setSelection(phoneNumber.length());
    }
  }

  private void initEditText() {
    EditTextUtil.setDeleteEditTextAddTextChanged(imgDeletePhoneNumber, editPhone);
    EditTextUtil.setDeleteEditTextAddTextChanged(imgDeletePassword, editPassword);
    editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_NEXT
            || i == EditorInfo.IME_ACTION_DONE
            || i == EditorInfo.IME_ACTION_GO) {
          if (registerSubmit()) {
            showLoadingDialog("");
            getPresenter().register(phoneNumber, password);
          }
        }
        return true;
      }
    });
  }

  private void initGetVerifyCodeTimeUtil() {
    getVerifyCodeTimerUtil = new GetVerifyCodeTimerUtil(tvVerifyCode, RegisterActivity.this);
    getVerifyCodeTimerUtil.setGetVerifyCodeCallBack(
        new GetVerifyCodeTimerUtil.GetVerifyCodeCallBack() {
          @Override public void onClickListener() {
            phoneNumber = editPhone.getText().toString();
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
    phoneNumber = editPhone.getText().toString();
    if (Strings.isNullOrEmpty(phoneNumber)) {
      ToastUtil.showToast(getApplicationContext(), "请将手机号输入完整", false);
      return false;
    }
    verifyCode = editVerifyCode.getText().toString();
    if (Strings.isNullOrEmpty(verifyCode)) {
      ToastUtil.showToast(getApplicationContext(), "请填写验证码", false);
      return false;
    }
    if (!verifyCode.equals("1234")) {
      ToastUtil.showToast(getApplicationContext(), "请填写正确的验证码", false);
      return false;
    }
    password = editPassword.getText().toString();
    if (Strings.isNullOrEmpty(password)) {
      ToastUtil.showToast(getApplicationContext(), "请设置您的密码", false);
      return false;
    }
    if (!isPassVerify) {
      ToastUtil.showToast(getApplicationContext(), "请获取验证码", false);
      return false;
    }
    if (!isCheckAgreement) {
      ToastUtil.showToast(getApplicationContext(), "请认真阅读并同意宠乐服务协议条款", false);
      return false;
    }
    return true;
  }

  @OnClick({
      R.id.super_tv_register_submit, R.id.layout_register_agreement_check,
      R.id.layout_register_back, R.id.relat_register_show_close_password
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.super_tv_register_submit:
        if (registerSubmit()) {
          showLoadingDialog("");
          getPresenter().register(phoneNumber, password);
        }
        break;
      case R.id.layout_register_agreement_check:
        if (isCheckAgreement) {
          isCheckAgreement = false;
          imgAgreementCheck.setBackgroundResource(R.drawable.circle_ring_register_check_false);
        } else {
          isCheckAgreement = true;
          imgAgreementCheck.setBackgroundResource(R.mipmap.register_main_check_true);
        }
        break;
      case R.id.relat_register_show_close_password:
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
      case R.id.layout_register_back:
        finish();
        break;
    }
  }

  @Override public void getFailed(String msg, String code) {

  }

  @Override public void checkAccountSuccess() {
    dismissLoadingDialog();
  }

  @Override public void checkAccountFailed() {
    dismissLoadingDialog();
    MessageView messageView = new MessageView(activity, "当前账号已注册");
    messageView.createDialog();
  }

  @Override public void register() {
    dismissLoadingDialog();
    showToast("注册成功");
    finish();
  }
}
