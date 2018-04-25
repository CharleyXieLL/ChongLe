package com.jiajia.badou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.jiajia.badou.bean.event.EventActionUtil;
import com.jiajia.badou.bean.event.EventBusAction;
import com.jiajia.badou.util.ActManager;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.util.InterfaceUtil;
import com.jiajia.badou.view.BigAvatarView;
import com.jiajia.badou.view.CommonPopWindow;
import com.jiajia.badou.view.ExitView;
import com.jiajia.badou.view.ReportLendPeopleDialog;
import com.jiajia.badou.view.SingleExitDialog;
import com.jiajia.badou.view.UpLoadAvatarView;
import com.jiajia.badou.view.wheelview.CalendarView;
import com.jiajia.presenter.bean.AddPetPost;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.mine.PetCompileMvpView;
import com.jiajia.presenter.modle.mine.PetCompilePresenter;
import com.jiajia.presenter.util.ChongLeConfig;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
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
public class PetCompileActivity extends BaseActivity<PetCompilePresenter>
    implements TakePhoto.TakeResultListener, InvokeListener, PetCompileMvpView {

  private static final String TAG = "PetCompileActivity";

  @BindView(R.id.yq_base_back_arrow_iv) RelativeLayout backArrowIv;
  @BindView(R.id.img_pet_compile_avatar) RoundedImageView imgAvatar;
  @BindView(R.id.super_tv_pet_compile_pet_name) SuperTextView superTvPetName;
  @BindView(R.id.relat_pet_compile_avatar) RelativeLayout relatAvatar;
  @BindView(R.id.super_tv_pet_compile_pet_variety) SuperTextView superTvPetVariety;
  @BindView(R.id.super_tv_pet_compile_pet_gender) SuperTextView superTvPetGender;
  @BindView(R.id.super_tv_pet_compile_pet_birthday) SuperTextView superTvPetBirthday;
  @BindView(R.id.super_tv_pet_compile_save) SuperTextView superTvSave;
  @BindView(R.id.api_base_title) TextView apiBaseTitle;
  @BindView(R.id.tv_base_title_right) TextView tvBaseTitleRight;

  private TakePhoto takePhoto;
  private InvokeParam invokeParam;

  private UpLoadAvatarView upLoadAvatarView;
  private ReportLendPeopleDialog reportLendPeopleDialog;
  private CommonPopWindow commonPopWindow;
  private CalendarView calendarView;
  private SingleExitDialog singleExitDialogName;
  private SingleExitDialog singleExitDialogVariety;
  private BigAvatarView bigAvatarView;

  private SelectPetsByOwnerBean selectPetsByOwnerBean;

  private List<String> petGenders = new ArrayList<>();
  private List<String> varietys = new ArrayList<>();

  private String gender;
  private String variety;

  private Handler submitHandler = new Handler();
  private Handler deleteHandler = new Handler();

  public static Intent callIntent(Context context, SelectPetsByOwnerBean selectPetsByOwnerBean) {
    Intent intent = new Intent(context, PetCompileActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(TAG, selectPetsByOwnerBean);
    intent.putExtras(bundle);
    return intent;
  }

  public static Intent callIntent(Context context) {
    return new Intent(context, PetCompileActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    getTakePhoto().onCreate(savedInstanceState);
    super.onCreate(savedInstanceState);
    ActManager.getAppManager().add(this);
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_pet_compile;
  }

  @Override protected void init() {
    getIntentData();
    initView();
    initCalendarView();
    initReportDialog();
    initCommonPopView();
    initSingleEditDialog();
    initBigAvatarView();
  }

  private void getIntentData() {
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      selectPetsByOwnerBean = ((SelectPetsByOwnerBean) bundle.getSerializable(TAG));
    }
    if (selectPetsByOwnerBean != null) {
      apiBaseTitle.setText(selectPetsByOwnerBean.getPet_name());
      tvBaseTitleRight.setText("删除宠物");
      tvBaseTitleRight.setTextColor(ContextCompat.getColor(activity, R.color.yj_warning_red));
      tvBaseTitleRight.setVisibility(View.VISIBLE);
      superTvPetName.setRightString(selectPetsByOwnerBean.getPet_name());
      superTvPetName.setRightTextColor(ContextCompat.getColor(activity, R.color.yc_black));
      Glide.with(PetCompileActivity.this)
          .load(selectPetsByOwnerBean.getPet_img())
          .error(R.mipmap.ic_launcher)
          .into(imgAvatar);
      superTvPetVariety.setRightString(selectPetsByOwnerBean.getPet_type());
      superTvPetVariety.setRightTextColor(ContextCompat.getColor(activity, R.color.yc_black));
      superTvPetGender.setRightString(selectPetsByOwnerBean.getPet_male());
      superTvPetGender.setRightTextColor(ContextCompat.getColor(activity, R.color.yc_black));
      superTvSave.setVisibility(View.GONE);
    } else {
      apiBaseTitle.setText("添加宠物");
      tvBaseTitleRight.setVisibility(View.GONE);
      superTvSave.setVisibility(View.VISIBLE);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    getTakePhoto().onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override protected Presenter returnPresenter() {
    return new PetCompilePresenter();
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
    calendarView.setCalendarType(CalendarView.CALENDAR_BIRTHDAY);
    calendarView.setCalendarViewCallBack(new CalendarView.CalendarViewCallBack() {
      @Override public void setCalendarTime(String time) {
        superTvPetBirthday.setRightString(time);
        superTvPetBirthday.setRightTextColor(
            ContextCompat.getColor(PetCompileActivity.this, R.color.yc_black));
      }
    });
  }

  private void initView() {
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
        ExitView exitView = new ExitView(activity);
        exitView.setMsg("删除当前宠物会导致正在预约中的订单失效，确认删除当前宠物吗？");
        exitView.setExitViewCallBack(new InterfaceUtil.ExitViewCallBack() {
          @Override public void exitViewLoginOut() {
            dismissLoadingDialog();
            if (selectPetsByOwnerBean != null) {
              showLoadingDialog("");
              deleteHandler.postDelayed(new Runnable() {
                @Override public void run() {
                  getPresenter().delPetsById(selectPetsByOwnerBean.getPet_id());
                }
              }, 600);
            }
          }

          @Override public void exitNegative() {

          }
        });
        exitView.showDialog();
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
        final String petName = superTvPetName.getRightString();
        final String petAvatar = BaseSharedDataUtil.getPetAvatar(activity);
        final String petGender = superTvPetGender.getRightString();
        final String petType = superTvPetVariety.getRightString();

        if (Strings.isNullOrEmpty(petName) || petName.equals("请输入")) {
          showToast("请输入宠物名称");
          return;
        }
        if (Strings.isNullOrEmpty(petAvatar)) {
          showToast("请为您的宠物上传一个头像吧");
          return;
        }
        if (Strings.isNullOrEmpty(petType) || petName.equals("请输入")) {
          showToast("请输入宠物品种");
          return;
        }
        if (Strings.isNullOrEmpty(petGender) || petName.equals("请选择")) {
          showToast("请选择宠物性别");
          return;
        }
        showLoadingDialog("");
        submitHandler.postDelayed(new Runnable() {
          @Override public void run() {
            getPresenter().insertPets(new AddPetPost(petGender, petName,
                String.valueOf(BaseSharedDataUtil.getUserId(activity)), petType), petAvatar);
          }
        }, 600);
        break;
      default:
        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    getTakePhoto().onActivityResult(requestCode, resultCode, data);
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
    PermissionManager.TPermissionType type =
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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
    BaseSharedDataUtil.setPetAvatar(activity, result.getImage().getOriginalPath());
    Glide.with(PetCompileActivity.this)
        .load(result.getImage().getOriginalPath())
        .error(R.mipmap.ic_launcher)
        .into(imgAvatar);
  }

  /**
   * 获取TakePhoto实例
   */
  public TakePhoto getTakePhoto() {
    if (takePhoto == null) {
      takePhoto =
          (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
    }
    CompressConfig compressConfig =
        new CompressConfig.Builder().setMaxSize(100 * 1024).setMaxPixel(800).create();
    takePhoto.onEnableCompress(compressConfig, true);
    return takePhoto;
  }

  @Override public void takeFail(TResult result, String msg) {
    ToastUtil.showToast(getApplicationContext(), msg, false);
  }

  @Override public void takeCancel() {
  }

  @Override public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
    PermissionManager.TPermissionType type =
        PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
    if (PermissionManager.TPermissionType.WAIT.equals(type)) {
      this.invokeParam = invokeParam;
    }
    return type;
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }

  @Override public void insertPets() {
    dismissLoadingDialog();
    showToast("添加成功");
    bus.post(new EventBusAction(EventActionUtil.REFRESH_DATA));
    bus.post(new EventBusAction(EventActionUtil.REFRESH_MINE_DATA));
    finish();
  }

  @Override public void delPetsById() {
    dismissLoadingDialog();
    showToast("删除成功");
    bus.post(new EventBusAction(EventActionUtil.REFRESH_DATA));
    bus.post(new EventBusAction(EventActionUtil.REFRESH_MINE_DATA));
    finish();
  }
}
