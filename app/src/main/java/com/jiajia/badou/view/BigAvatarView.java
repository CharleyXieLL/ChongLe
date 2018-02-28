package com.jiajia.badou.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.jiajia.badou.R;

/**
 * Created by Lei on 2018/1/9.
 * 大头像
 */
public class BigAvatarView {

  private Activity activity;
  private ImageView imgAvatar;
  private RelativeLayout layoutClose;
  private Dialog dialog;

  public BigAvatarView(Activity activity) {
    this.activity = activity;
    createView();
  }

  private void createView() {
    dialog = new Dialog(activity);
    dialog.setCancelable(true);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(R.layout.ycjt_dialog_big_avatar);

    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.getDecorView().setPadding(0, 0, 0, 0);

    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
    window.setAttributes(lp);

    imgAvatar = dialog.findViewById(R.id.img_big_avatar);
    layoutClose = dialog.findViewById(R.id.layout_big_avatar_close);
    layoutClose.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dialog.dismiss();
      }
    });
  }

  public void setUrl(String url) {
    Glide.with(activity).load(url).error(R.mipmap.yj_defoult_user_head).into(imgAvatar);
    showDialog();
  }

  public void showDialog() {
    if (dialog != null) {
      dialog.dismiss();
      dialog.show();
    }
  }

  public void dismissDialog() {
    if (dialog != null) {
      dialog.dismiss();
    }
  }
}
