package com.jiajia.badou.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.PetPrimpDetailAdapter;
import com.jiajia.badou.adapter.PrimpSelectPetAdapter;
import com.jiajia.badou.bean.event.EventActionUtil;
import com.jiajia.badou.bean.event.EventBusAction;
import com.jiajia.badou.util.ActManager;
import com.jiajia.badou.util.BaseSharedDataUtil;
import com.jiajia.badou.view.CommonPopWindow;
import com.jiajia.badou.view.wheelview.CalendarView;
import com.jiajia.presenter.bean.InsertOrderPost;
import com.jiajia.presenter.bean.mine.SelectPetsByOwnerBean;
import com.jiajia.presenter.impl.Presenter;
import com.jiajia.presenter.modle.primp.PrimpSelectPetMvpView;
import com.jiajia.presenter.modle.primp.PrimpSelectPetPresenter;
import com.jiajia.presenter.util.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Lei on 2018/4/24.
 */
public class PrimpSelectPetActivity extends BaseActivity<PrimpSelectPetPresenter>
    implements PrimpSelectPetMvpView {

  private static final String ORDER_TYPE = "order_type";
  private static final String STORE = "store";
  private static final String PHONE = "phone";

  @BindView(R.id.yq_base_back_arrow_iv) RelativeLayout yqBaseBackArrowIv;
  @BindView(R.id.tv_base_title_close) TextView tvBaseTitleClose;
  @BindView(R.id.api_base_title) TextView apiBaseTitle;
  @BindView(R.id.tv_base_title_right) TextView tvBaseTitleRight;
  @BindView(R.id.tv_primp_select_pet) TextView tvPrimpSelectPet;
  @BindView(R.id.recyclerview_primp_select_pet) RecyclerView recyclerviewPrimpSelectPet;
  @BindView(R.id.img_primp_select_pet_add) ImageView imgPrimpSelectPetAdd;
  @BindView(R.id.relat_primp_select_pet_add) RelativeLayout relatPrimpSelectPetAdd;
  @BindView(R.id.tv_primp_select_pet_phone) TextView tvPrimpSelectPetPhone;
  @BindView(R.id.tv_primp_select_pet_time) TextView tvPrimpSelectPetTime;
  @BindView(R.id.tv_primp_select_pet_time_hour) TextView tvPrimpSelectPetTimeHour;
  @BindView(R.id.tv_primp_select_pet_submit) TextView tvPrimpSelectPetSubmit;

  @BindView(R.id.relat_primp_select_pet_time_hour) RelativeLayout relatPrimpSelectPetTimeHour;
  @BindView(R.id.relat_primp_select_pet_time) RelativeLayout relatPrimpSelectPetSubmit;

  private PrimpSelectPetAdapter adapter;

  private List<SelectPetsByOwnerBean> selectPetsByOwnerBeans = new ArrayList<>();

  private CalendarView calendarView;
  private CommonPopWindow commonPopWindow;

  private String orderType;
  private String store;
  private String phone;

  private int clickPosition = PetPrimpDetailAdapter.NO_DATA;

  private Handler handler = new Handler();

  public static Intent callIntent(Context context, String orderType, String store, String phone) {
    Intent intent = new Intent(context, PrimpSelectPetActivity.class);
    intent.putExtra(ORDER_TYPE, orderType);
    intent.putExtra(STORE, store);
    intent.putExtra(PHONE, phone);
    return intent;
  }

  @Override protected int onCreateViewTitleId() {
    return 0;
  }

  @Override protected int onCreateViewId() {
    return R.layout.activity_primp_select_pet;
  }

  @Override protected Presenter returnPresenter() {
    return new PrimpSelectPetPresenter();
  }

  @Override protected void init() {
    getIntentData();
    initRecycleView();
    initCalendarView();
    initCommonPopView();
    getData();
  }

  private void initCalendarView() {
    calendarView = new CalendarView(activity);
    calendarView.createDialog();
    calendarView.setTargetTime("", 0, "请选择预约日期");
    calendarView.setCalendarType(CalendarView.CALENDAR_NORMAL_TIME);
    calendarView.setCalendarViewCallBack(new CalendarView.CalendarViewCallBack() {
      @Override public void setCalendarTime(String time) {
        tvPrimpSelectPetTime.setText(time);
        tvPrimpSelectPetTime.setTextColor(ContextCompat.getColor(activity, R.color.yc_black));
      }
    });
  }

  private void initCommonPopView() {
    commonPopWindow = new CommonPopWindow(activity);
    commonPopWindow.setSureOnClickCallBackListener(new CommonPopWindow.SureOnClickCallBack() {
      @Override public void sureFinish(String selectText, int position) {
        tvPrimpSelectPetTimeHour.setText(selectText);
        tvPrimpSelectPetTimeHour.setTextColor(ContextCompat.getColor(activity, R.color.yc_black));
      }
    });
    commonPopWindow.setPopWindowListViewAdapter(
        Arrays.asList(getResources().getStringArray(R.array.time_hour)),
        tvPrimpSelectPetTimeHour.getText().toString());
  }

  private void initRecycleView() {
    adapter = new PrimpSelectPetAdapter(activity, new ArrayList<SelectPetsByOwnerBean>());
    recyclerviewPrimpSelectPet.setLayoutManager(new GridLayoutManager(activity, 3));
    recyclerviewPrimpSelectPet.setHasFixedSize(true);
    recyclerviewPrimpSelectPet.setAdapter(adapter);

    adapter.setPrimpSelectPetAdapterCallBack(
        new PrimpSelectPetAdapter.PrimpSelectPetAdapterCallBack() {
          @Override public void onClick(int position) {
            clickPosition = position;
          }
        });
  }

  private void getData() {
    showLoadingDialog("");
    getPresenter().selectPetsByOwner(BaseSharedDataUtil.getUserId(activity));
  }

  @SuppressLint("SetTextI18n") private void getIntentData() {
    orderType = getIntent().getStringExtra(ORDER_TYPE);
    store = getIntent().getStringExtra(STORE);
    phone = getIntent().getStringExtra(PHONE);
    apiBaseTitle.setText(orderType + "预约");
    tvPrimpSelectPetPhone.setText(phone);
  }

  @Override public void getFailed(String msg, String code) {
    dismissLoadingDialog();
    showToast(msg);
  }

  @OnClick({
      R.id.img_primp_select_pet_add, R.id.relat_primp_select_pet_time,
      R.id.relat_primp_select_pet_time_hour, R.id.tv_primp_select_pet_submit
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_primp_select_pet_add:
        startActivity(PetCompileActivity.callIntent(activity));
        break;
      case R.id.relat_primp_select_pet_time:
        calendarView.showCalendar();
        break;
      case R.id.relat_primp_select_pet_time_hour:
        commonPopWindow.showDialog();
        break;
      case R.id.tv_primp_select_pet_submit:
        String time = tvPrimpSelectPetTime.getText().toString();
        String timeHour = tvPrimpSelectPetTimeHour.getText().toString();
        if (clickPosition == PetPrimpDetailAdapter.NO_DATA) {
          showToast("请选择一个宠物");
          return;
        }
        if (Strings.isNullOrEmpty(time)) {
          showToast("请选择预约日期");
          return;
        }
        if (Strings.isNullOrEmpty(timeHour)) {
          showToast("请选择预约时间段");
          return;
        }
        showLoadingDialog("");
        handler.postDelayed(new Runnable() {
          @Override public void run() {
            getPresenter().insertOrder(
                new InsertOrderPost(BaseSharedDataUtil.getUserId(activity), orderType, store,
                    selectPetsByOwnerBeans.get(clickPosition).getPet_id()));
          }
        }, 600);
        break;
    }
  }

  @Override public void selectPetsByOwner(List<SelectPetsByOwnerBean> selectPetsByOwnerBeans) {
    dismissLoadingDialog();
    this.selectPetsByOwnerBeans.clear();
    this.selectPetsByOwnerBeans.addAll(selectPetsByOwnerBeans);

    adapter.clear();
    adapter.addAll(selectPetsByOwnerBeans);

    if (selectPetsByOwnerBeans.size() > 0) {
      recyclerviewPrimpSelectPet.setVisibility(View.VISIBLE);
      relatPrimpSelectPetAdd.setVisibility(View.GONE);
    } else {
      recyclerviewPrimpSelectPet.setVisibility(View.GONE);
      relatPrimpSelectPetAdd.setVisibility(View.VISIBLE);
    }

    if (selectPetsByOwnerBeans.size() == 1) {
      clickPosition = 0;
    }
  }

  @Override public void insertOrder() {
    dismissLoadingDialog();
    showToast("预约成功");
    ActManager.getAppManager().finishActivity(PetPrimpDetailActivity.class);
    startActivity(SelectAllOrderActivity.callIntent(activity));
    finish();
  }

  @Override protected void onStart() {
    super.onStart();
    if (!bus.isRegistered(this)) {
      bus.register(this);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (bus.isRegistered(this)) {
      bus.unregister(this);
    }
  }

  @Subscribe public void onEventAction(EventBusAction event) {
    String action = event.getAction();
    if (!Strings.isNullOrEmpty(action)) {
      switch (action) {
        case EventActionUtil.REFRESH_DATA:
          getPresenter().selectPetsByOwner(BaseSharedDataUtil.getUserId(activity));
          break;
      }
    }
  }
}
