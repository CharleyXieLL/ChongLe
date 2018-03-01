package com.jiajia.badou.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.jiajia.badou.R;
import com.jiajia.presenter.util.Strings;
import com.jiajia.presenter.util.ToastUtil;

/**
 * Created by Lei on 2018/2/28.
 * 单一的输入框弹窗
 */
public class SingleExitDialog {

  private Activity activity;

  private Dialog dialog;
  private EditText editText;

  private String hintText;

  public SingleExitDialog(Activity activity) {
    this.activity = activity;
    createView();
  }

  private void createView() {
    dialog = new Dialog(activity);
    dialog.setCancelable(true);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(R.layout.dialog_single_edit);

    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.getDecorView().setPadding(0, 0, 0, 0);

    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    window.setAttributes(lp);

    editText = dialog.findViewById(R.id.edit_dialog_single_edit);
    TextView negative = dialog.findViewById(R.id.negative);
    TextView positive = dialog.findViewById(R.id.positive);
    negative.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismissDialog();
      }
    });
    positive.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dismissDialog();
        if (!Strings.isNullOrEmpty(editText.getText())) {
          singleEditDialogCallBack.getText(editText.getText().toString());
        } else {
          ToastUtil.showToast(activity.getApplicationContext(), hintText, false);
        }
      }
    });
  }

  public void setEditHint(String hint) {
    this.hintText = hint;
    editText.setHint(hint);
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

  public interface SingleEditDialogCallBack {
    void getText(String text);
  }

  private SingleEditDialogCallBack singleEditDialogCallBack;

  public void setSingleEditDialogCallBack(SingleEditDialogCallBack singleEditDialogCallBack) {
    this.singleEditDialogCallBack = singleEditDialogCallBack;
  }
}
