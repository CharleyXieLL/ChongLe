package com.jiajia.badou.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.SimpleTextAdapter;
import java.util.ArrayList;
import java.util.List;

public class CommonPopWindow {
  private Context mContext;
  private RecyclerView mListView;
  private Dialog dialog;
  private SureOnClickCallBack mCallBack;
  private SimpleTextAdapter mAdapter;
  private RelativeLayout relatPop;
  private List<String> datas = new ArrayList<>();

  public CommonPopWindow(Context mContext) {
    this.mContext = mContext;
    createCityWindow();
  }

  /***
   * 设置listViewadapter
   * @Title: setPopWindowListViewAdapter
   * @param @param list
   * @param  defaultStr    默认显示的文字
   */
  public void setPopWindowListViewAdapter(List<String> list, String defaultStr) {
    datas.clear();
    datas.addAll(list);
    if (mAdapter == null) {
      mAdapter = new SimpleTextAdapter(mContext, list, defaultStr);
      mListView.setAdapter(mAdapter);
      mAdapter.setSimpleTextAdapterCallBack(new SimpleTextAdapter.SimpleTextAdapterCallBack() {
        @Override public void onClick(int position) {
          mAdapter.setStr(datas.get(position));
          if (mCallBack != null) {
            mCallBack.sureFinish(mAdapter.getStr(), position);
          }
          dismissDialog();
        }
      });
    }
  }

  private void createCityWindow() {
    if (dialog == null) {
      dialog = new Dialog(mContext);
      dialog.setCancelable(true);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCanceledOnTouchOutside(false);
      dialog.setContentView(R.layout.yq_city_pop);

      Window window = dialog.getWindow();
      window.setGravity(Gravity.CENTER);
      //window.setWindowAnimations(R.style.yq_mypopwindow_anim_style);
      window.getDecorView().setPadding(0, 0, 0, 0);

      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = WindowManager.LayoutParams.MATCH_PARENT;
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);
    }

    mListView = (RecyclerView) dialog.findViewById(R.id.text_list);
    relatPop = ((RelativeLayout) dialog.findViewById(R.id.yj_relat_pop));

    mListView.setHasFixedSize(true);
    mListView.setLayoutManager(new LinearLayoutManager(mContext));

    relatPop.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        dismissDialog();
      }
    });
  }

  public void showDialog() {
    if (dialog != null && !dialog.isShowing()) {
      dialog.show();
    }
  }

  public void dismissDialog() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  public interface SureOnClickCallBack {
    void sureFinish( String selectText, int position);
  }

  public void setSureOnClickCallBackListener(SureOnClickCallBack callback) {
    this.mCallBack = callback;
  }
}