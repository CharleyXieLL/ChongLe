package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.ReportLendPeopleAdapter;
import com.jiajia.badou.util.ActManager;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.view.BigAvatarView;
import com.jiajia.badou.view.CommonPopWindow;
import com.jiajia.badou.view.ReportLendPeopleDialog;
import com.jiajia.badou.view.SingleExitDialog;
import com.jiajia.badou.view.UpLoadAvatarView;
import com.jiajia.badou.view.wheelview.CalendarView;
import com.jiajia.presenter.util.ChongLeConfig;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;
import com.jph.takephoto.model.TResult;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by Lei on 2018/2/27.
 * 宠物编辑界面
 */
public class PetCompileActivity extends TakePhotoActivity {

  @BindView(R.id.yq_base_back_arrow_iv) RelativeLayout backArrowIv;
  @BindView(R.id.tv_base_title_right) TextView tvTitleRight;
  @BindView(R.id.img_pet_compile_avatar) RoundedImageView imgAvatar;
  @BindView(R.id.super_tv_pet_compile_pet_name) SuperTextView superTvPetName;
  @BindView(R.id.relat_pet_compile_avatar) RelativeLayout relatAvatar;
  @BindView(R.id.super_tv_pet_compile_pet_variety) SuperTextView superTvPetVariety;
  @BindView(R.id.super_tv_pet_compile_pet_gender) SuperTextView superTvPetGender;
  @BindView(R.id.super_tv_pet_compile_pet_birthday) SuperTextView superTvPetBirthday;
  @BindView(R.id.super_tv_pet_compile_save) SuperTextView superTvSave;

  private UpLoadAvatarView upLoadAvatarView;
  private ReportLendPeopleDialog reportLendPeopleDialog;
  private CommonPopWindow commonPopWindow;
  private CalendarView calendarView;
  private SingleExitDialog singleExitDialogName;
  private SingleExitDialog singleExitDialogVariety;
  private BigAvatarView bigAvatarView;

  private List<String> petGenders = new ArrayList<>();
  private List<String> varietys = new ArrayList<>();

  private String gender;
  private String variety;

  public static Intent callIntent(Context context) {
    return new Intent(context, PetCompileActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActManager.getAppManager().add(this);
  }

  @Override protected int onCreateViewTitle() {
    return 0;
  }

  @Override protected int onCreateView() {
    return R.layout.activity_pet_compile;
  }

  @Override protected void initUi() {
    initView();
    initCalendarView();
    initReportDialog();
    initCommonPopView();
    initSingleEditDialog();
    initBigAvatarView();
  }

  private void initSingleEditDialog() {
    singleExitDialogName = new SingleExitDialog(PetCompileActivity.this);
    singleExitDialogName.setEditHint("请输入宠物名");
    singleExitDialogName.setSingleEditDialogCallBack(
        new SingleExitDialog.SingleEditDialogCallBack() {
          @Override public void getText(String text) {
            superTvPetName.setRightString(text);
            superTvPetName.setRightTextColor(
                ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
          }
        });

    singleExitDialogVariety = new SingleExitDialog(PetCompileActivity.this);
    singleExitDialogVariety.setEditHint("请输入宠物品种");
    singleExitDialogVariety.setSingleEditDialogCallBack(
        new SingleExitDialog.SingleEditDialogCallBack() {
          @Override public void getText(String text) {
            superTvPetVariety.setRightString(text);
            superTvPetVariety.setRightTextColor(
                ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
          }
        });
  }

  private void initCommonPopView() {
    commonPopWindow = new CommonPopWindow(PetCompileActivity.this);
    commonPopWindow.setSureOnClickCallBackListener(new CommonPopWindow.SureOnClickCallBack() {
      @Override public void sureFinish(String selectText, int position) {
        gender = Arrays.asList(getResources().getStringArray(R.array.pet_gender)).get(position);
        superTvPetGender.setRightString(gender);
        superTvPetGender.setRightTextColor(
            ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
      }
    });
    commonPopWindow.setPopWindowListViewAdapter(
        Arrays.asList(getResources().getStringArray(R.array.pet_gender)),
        superTvPetGender.getRightString());
  }

  private void initCalendarView() {
    calendarView = new CalendarView(PetCompileActivity.this);
    calendarView.createDialog();
    calendarView.setTargetTime("", 0, "请选择出生日期");
    calendarView.setCalendarViewCallBack(new CalendarView.CalendarViewCallBack() {
      @Override public void setCalendarTime(String time) {
        superTvPetBirthday.setRightString(time);
        superTvPetBirthday.setRightTextColor(
            ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
      }
    });
  }

  private void initView() {
    String imgPath = BaseSharedDataUtil.getPetAvatar(activity);
    if (!Strings.isNullOrEmpty(imgPath)) {
      Glide.with(PetCompileActivity.this).load(imgPath).error(R.mipmap.ic_launcher).into(imgAvatar);
    }
    tvTitleRight.setText("添加宠物");
    tvTitleRight.setTextColor(
        ContextCompat.getColor(PetCompileActivity.this, R.color.main_check_true));

    if (upLoadAvatarView == null) {
      upLoadAvatarView = new UpLoadAvatarView(PetCompileActivity.this, getTakePhoto());
      upLoadAvatarView.setUpLoadAvatarViewCallBack(new UpLoadAvatarView.UpLoadAvatarViewCallBack() {
        @Override public void onTakeSuccess(String path) {
          Glide.with(PetCompileActivity.this)
              .load(path)
              .error(R.mipmap.ic_launcher)
              .into(imgAvatar);
        }
      });
    }
  }

  private void initReportDialog() {
    if (reportLendPeopleDialog == null) {
      reportLendPeopleDialog = new ReportLendPeopleDialog(PetCompileActivity.this);
      reportLendPeopleDialog.setData(varietys);
      reportLendPeopleDialog.setReportLendPeopleCallBack(
          new ReportLendPeopleAdapter.ReportLendPeopleCallBack() {
            @Override public void onClick(int position) {
              String text = varietys.get(position);
              reportLendPeopleDialog.setSelectedText(text);
              superTvPetVariety.setRightString(text);
              superTvPetVariety.setRightTextColor(
                  ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
              variety = text;
            }
          });
    }
  }

  private void initBigAvatarView() {
    bigAvatarView = new BigAvatarView(PetCompileActivity.this);
  }

  @OnClick({
      R.id.yq_base_back_arrow_iv, R.id.tv_base_title_right, R.id.super_tv_pet_compile_pet_name,
      R.id.relat_pet_compile_avatar, R.id.super_tv_pet_compile_pet_variety,
      R.id.super_tv_pet_compile_pet_gender, R.id.super_tv_pet_compile_pet_birthday,
      R.id.super_tv_pet_compile_save
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.yq_base_back_arrow_iv:
        finish();
        break;
      case R.id.tv_base_title_right:
        break;
      case R.id.super_tv_pet_compile_pet_name:
        singleExitDialogName.showDialog();
        break;
      case R.id.relat_pet_compile_avatar:
        if (upLoadAvatarView != null) {
          upLoadAvatarView.showPopView();
        }
        break;
      case R.id.super_tv_pet_compile_pet_variety:
        singleExitDialogVariety.showDialog();
        break;
      case R.id.super_tv_pet_compile_pet_gender:
        commonPopWindow.showDialog();
        break;
      case R.id.super_tv_pet_compile_pet_birthday:
        calendarView.showCalendar();
        break;
      case R.id.super_tv_pet_compile_save:

        break;
      default:
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
    ActManager.getAppManager().remove(this);
    if (upLoadAvatarView != null) {
      upLoadAvatarView.setUpLoadAvatarViewCallBack(null);
    }
    if (calendarView != null) {
      calendarView.setCalendarViewCallBack(null);
    }
    if (reportLendPeopleDialog != null) {
      reportLendPeopleDialog.setReportLendPeopleCallBack(null);
    }
    if (singleExitDialogName != null) {
      singleExitDialogName.setSingleEditDialogCallBack(null);
    }
    if (singleExitDialogVariety != null) {
      singleExitDialogVariety.setSingleEditDialogCallBack(null);
    }
  }

  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    BaseSharedDataUtil.setPetAvatar(activity, result.getImage().getOriginalPath());
    Glide.with(PetCompileActivity.this)
        .load(result.getImage().getOriginalPath())
        .error(R.mipmap.ic_launcher)
        .into(imgAvatar);
  }
}
