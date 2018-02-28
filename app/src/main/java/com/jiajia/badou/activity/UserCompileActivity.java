package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.util.ChongLeConfig;
import com.jiajia.badou.util.ToastUtil;
import com.jiajia.badou.view.BigAvatarView;
import com.jiajia.badou.view.CommonPopWindow;
import com.jiajia.badou.view.SingleExitDialog;
import com.jiajia.badou.view.UpLoadAvatarView;
import com.jph.takephoto.model.TResult;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.Arrays;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by Lei on 2018/2/28.
 * 个人编辑界面
 */
public class UserCompileActivity extends TakePhotoActivity {

  @BindView(R.id.yq_base_back_arrow_iv) RelativeLayout backArrowIv;
  @BindView(R.id.super_tv_user_compile_user_name) SuperTextView superTvUserName;
  @BindView(R.id.img_user_compile_avatar) RoundedImageView imgAvatar;
  @BindView(R.id.relat_user_compile_avatar) RelativeLayout relatAvatar;
  @BindView(R.id.super_tv_user_compile_user_gender) SuperTextView superTvUserGender;
  @BindView(R.id.edit_user_compile_sign) EditText editSign;
  @BindView(R.id.super_tv_user_compile_save) SuperTextView superTvSave;

  private UpLoadAvatarView upLoadAvatarView;
  private CommonPopWindow commonPopWindow;
  private SingleExitDialog singleExitDialog;
  private BigAvatarView bigAvatarView;

  private String gender;

  public static Intent callIntent(Context context) {
    return new Intent(context, UserCompileActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_compile);
    ButterKnife.bind(this);

    initUpLoadAvatarView();
    initCommonPopWindow();
    initSingleExitDialog();
    initBigAvatarView();
  }

  private void initUpLoadAvatarView() {
    upLoadAvatarView = new UpLoadAvatarView(UserCompileActivity.this, getTakePhoto());
    upLoadAvatarView.setUpLoadAvatarViewCallBack(new UpLoadAvatarView.UpLoadAvatarViewCallBack() {
      @Override public void onTakeSuccess(String path) {
        // todo 上传到头像至服务器
        Glide.with(UserCompileActivity.this).load(path).error(R.mipmap.ic_launcher).into(imgAvatar);
      }
    });
  }

  private void initCommonPopWindow() {
    commonPopWindow = new CommonPopWindow(UserCompileActivity.this);
    commonPopWindow.setSureOnClickCallBackListener(new CommonPopWindow.SureOnClickCallBack() {
      @Override public void sureFinish(String selectText, int position) {
        gender = Arrays.asList(getResources().getStringArray(R.array.gender)).get(position);
        superTvUserGender.setRightString(gender);
        superTvUserGender.setRightTextColor(
            ContextCompat.getColor(UserCompileActivity.this, R.color.yc_black));
      }
    });
    commonPopWindow.setPopWindowListViewAdapter(
        Arrays.asList(getResources().getStringArray(R.array.gender)),
        superTvUserGender.getRightString());
  }

  private void initSingleExitDialog() {
    singleExitDialog = new SingleExitDialog(UserCompileActivity.this);
    singleExitDialog.setEditHint("请输入姓名");
    singleExitDialog.setSingleEditDialogCallBack(new SingleExitDialog.SingleEditDialogCallBack() {
      @Override public void getText(String text) {
        superTvUserName.setRightString(text);
        superTvUserName.setRightTextColor(
            ContextCompat.getColor(UserCompileActivity.this, R.color.yc_black));
      }
    });
  }

  private void initBigAvatarView() {
    bigAvatarView = new BigAvatarView(UserCompileActivity.this);
  }

  @OnClick({
      R.id.yq_base_back_arrow_iv, R.id.super_tv_user_compile_user_name,
      R.id.img_user_compile_avatar, R.id.relat_user_compile_avatar,
      R.id.super_tv_user_compile_user_gender, R.id.super_tv_user_compile_save
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.yq_base_back_arrow_iv:
        finish();
        break;
      case R.id.super_tv_user_compile_user_name:
        singleExitDialog.showDialog();
        break;
      case R.id.img_user_compile_avatar:
        upLoadAvatarView.showPopView();
        break;
      case R.id.relat_user_compile_avatar:
        upLoadAvatarView.showPopView();
        break;
      case R.id.super_tv_user_compile_user_gender:
        commonPopWindow.showDialog();
        break;
      case R.id.super_tv_user_compile_save:

        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (upLoadAvatarView != null) {
      upLoadAvatarView.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (upLoadAvatarView != null) {
      upLoadAvatarView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @PermissionSuccess(requestCode = ChongLeConfig.TAKE_PHOTO_CODE) public void doSomething() {
    if (upLoadAvatarView != null) {
      upLoadAvatarView.doSomething();
    }
  }

  @PermissionFail(requestCode = ChongLeConfig.TAKE_PHOTO_CODE) public void doFailSomething() {
    ToastUtil.showToast(getApplicationContext(), "拒绝拍照权限，打开摄像头失败", false);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (upLoadAvatarView != null) {
      upLoadAvatarView.setUpLoadAvatarViewCallBack(null);
    }
    if (commonPopWindow != null) {
      commonPopWindow.setSureOnClickCallBackListener(null);
    }
    if (singleExitDialog != null) {
      singleExitDialog.setSingleEditDialogCallBack(null);
    }
  }

  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    // todo 上传到头像至服务器
    Glide.with(UserCompileActivity.this)
        .load(result.getImage().getOriginalPath())
        .error(R.mipmap.ic_launcher)
        .into(imgAvatar);
  }
}
