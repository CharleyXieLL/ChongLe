package com.jiajia.badou.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.allen.library.SuperTextView;
import com.jiajia.badou.R;
import com.jiajia.badou.adapter.ReportLendPeopleAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei on 2017/12/5.
 * 举报出借人弹框
 */
public class ReportLendPeopleDialog {

  public static final String PURPOSE = "purpose";
  public static final String INFORM = "inform";

  private Dialog dialog;
  private RecyclerView recyclerView;

  private ReportLendPeopleAdapter reportLendPeopleAdapter;
  private RelativeLayout relativeLayout;

  private String type;
  private SuperTextView superTvTitle;
  private TextView tvTitle;

  public ReportLendPeopleDialog(Activity activity) {
    onCreateView(activity);
  }

  private void onCreateView(Activity activity) {
    if (dialog == null) {
      dialog = new Dialog(activity);
      dialog.setCancelable(true);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setCanceledOnTouchOutside(false);
      dialog.setContentView(R.layout.ycjt_dialog_borrow_purpose);

      Window window = dialog.getWindow();
      window.setGravity(Gravity.CENTER);
      window.getDecorView().setPadding(0, 0, 0, 0);

      WindowManager.LayoutParams lp = window.getAttributes();
      lp.width = WindowManager.LayoutParams.MATCH_PARENT;
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      window.setAttributes(lp);

      reportLendPeopleAdapter = new ReportLendPeopleAdapter(activity, new ArrayList<String>());
      recyclerView = dialog.findViewById(R.id.recycler_dialog_report_lend_people);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(activity));
      recyclerView.setAdapter(reportLendPeopleAdapter);

      reportLendPeopleAdapter.setReportLendPeopleCallBack(
          new ReportLendPeopleAdapter.ReportLendPeopleCallBack() {
            @Override public void onClick(int position) {
              dismissDialog();
              reportLendPeopleCallBack.onClick(position);
            }
          });

      superTvTitle = dialog.findViewById(R.id.super_dialog_issue_money_title);

      tvTitle = dialog.findViewById(R.id.tv_dialog_issue_money_title2);

      relativeLayout = dialog.findViewById(R.id.layout_dialog_issue_money_purpose);
      relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dismissDialog();
        }
      });
    }
  }

  public void setType(String type) {
    this.type = type;
    if (type.equals(INFORM)) {
      tvTitle.setVisibility(View.GONE);
    } else {
      tvTitle.setVisibility(View.VISIBLE);
    }
  }

  public void setTitle(String text) {
    if (superTvTitle != null) {
      superTvTitle.setCenterString(text);
    }
  }

  public void setSelectedText(String text) {
    if (reportLendPeopleAdapter != null) {
      reportLendPeopleAdapter.setSelectText(text);
    }
  }

  public void setData(List<String> datas) {
    reportLendPeopleAdapter.clear();
    reportLendPeopleAdapter.addAll(datas);
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

  private ReportLendPeopleAdapter.ReportLendPeopleCallBack reportLendPeopleCallBack;

  public void setReportLendPeopleCallBack(
      ReportLendPeopleAdapter.ReportLendPeopleCallBack reportLendPeopleCallBack) {
    this.reportLendPeopleCallBack = reportLendPeopleCallBack;
  }
}
