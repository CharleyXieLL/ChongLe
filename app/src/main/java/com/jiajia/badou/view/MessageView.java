package com.jiajia.badou.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.jiajia.badou.R;

/**
 * Created by LEI on 2016/12/22 0022.
 *
 */
public class MessageView {

  private Dialog dialog;

  private Context sContext;

  private String alertMsg;

  private MessageViewPositionListener messageViewPositionListener;
  private TextView tv_sure;
  private TextView tv_tips;

  public MessageView(Context context, String msg) {
    this.sContext = context;
    this.alertMsg = msg;
  }

  public void createDialog() {
    dialog = new Dialog(sContext);
    dialog.setCancelable(false);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    dialog.setCanceledOnTouchOutside(false);
    dialog.setContentView(R.layout.yq_pop_tips);

    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.getDecorView().setPadding(0, 0, 0, 0);

    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
    window.setAttributes(lp);

    tv_tips = dialog.findViewById(R.id.tv_tips);
    tv_sure = dialog.findViewById(R.id.tv_sure);

    tv_tips.setText(alertMsg);

    tv_sure.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        dialog.dismiss();
        if(messageViewPositionListener!=null){
          messageViewPositionListener.messageViewPositive();
        }
      }
    });

    dialog.show();
  }

  public void setButtonText(String text){
    tv_sure.setText(text);
  }

  public interface MessageViewPositionListener{
    void messageViewPositive();
  }

  public void setMessageViewPositionListener(MessageViewPositionListener messageViewPositionListener){
    this.messageViewPositionListener = messageViewPositionListener;
  }
}