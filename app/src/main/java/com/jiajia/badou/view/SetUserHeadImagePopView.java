package com.jiajia.badou.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jiajia.badou.R;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class SetUserHeadImagePopView implements View.OnClickListener {

  private Dialog dialog;

  private Activity sActivity;

  private SetHeadImageViewCallBack setHeadImageViewCallBack;

  public SetUserHeadImagePopView(Activity sActivity) {
    this.sActivity = sActivity;
    createPopWindow();
  }

  public void createPopWindow() {
    dialog = new Dialog(sActivity);
    dialog.setCancelable(true);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(R.layout.yq_headimg_dialog);

    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.setWindowAnimations(R.style.yq_mypopwindow_anim_style);
    window.getDecorView().setPadding(0, 0, 0, 0);

    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    window.setAttributes(lp);

    TextView txtv_take_id = dialog.findViewById(R.id.txtv_take_id);
    TextView txtv_img_id = dialog.findViewById(R.id.txtv_img_id);
    TextView txtv_cancle_id = dialog.findViewById(R.id.txtv_cancle_id);

    txtv_take_id.setOnClickListener(this);
    txtv_img_id.setOnClickListener(this);
    txtv_cancle_id.setOnClickListener(this);

    RelativeLayout layout = dialog.findViewById(R.id.rlayout_id);

    layout.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        dismissPop();
        return false;
      }
    });

    layout.setOnKeyListener(new View.OnKeyListener() {
      @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.FLAG_CANCELED) {
          dismissPop();
        }
        return false;
      }
    });
  }

  @Override public void onClick(View view) {
    setHeadImageViewCallBack.setHeadImgCancel();

    if (R.id.txtv_take_id == view.getId()) {
      setHeadImageViewCallBack.setHeadImgTake();
    }
    if (R.id.txtv_img_id == view.getId()) {
      setHeadImageViewCallBack.setHeadImgImg();
    }
  }

  public void showPop() {
    if (dialog != null && !dialog.isShowing()) {
      dialog.show();
    }
  }

  public void dismissPop() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  public interface SetHeadImageViewCallBack {
    void setHeadImgTake();

    void setHeadImgImg();

    void setHeadImgCancel();
  }

  public void setSetHeadImageViewCallBack(SetHeadImageViewCallBack setHeadImageViewCallBack) {
    this.setHeadImageViewCallBack = setHeadImageViewCallBack;
  }
}
