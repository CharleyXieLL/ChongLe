package com.jiajia.badou.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import java.util.List;

/**
 * Created by Lei on 2017/12/5.
 */
public class ReportLendPeopleAdapter
    extends RecyclerView.Adapter<ReportLendPeopleAdapter.ViewHolder> {

  private List<String> mDatas;
  private LayoutInflater mInflater;
  private String selectText = "";
  private Context context;

  public ReportLendPeopleAdapter(Context mContext, List<String> mDatas) {
    this.mDatas = mDatas;
    this.context = mContext;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<String> getDatas() {
    return mDatas;
  }

  public ReportLendPeopleAdapter setDatas(List<String> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.ycjt_item_dialog_report_lend, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.tvContent.setText(mDatas.get(position));
    if (selectText.equals(mDatas.get(position))) {
      holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.yj_button_enable_true));
    } else {
      holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.yq_text_color_blank));
    }
    holder.relatContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        reportLendPeopleCallBack.onClick(position);
      }
    });
  }

  public void addAll(List<String> datas) {
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void clear() {
    mDatas.clear();
    notifyDataSetChanged();
  }

  public void setSelectText(String text) {
    this.selectText = text;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_item_report_lend_people) TextView tvContent;
    @BindView(R.id.relat_item_report_lend_people_root) RelativeLayout relatContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface ReportLendPeopleCallBack {
    void onClick(int position);
  }

  private ReportLendPeopleCallBack reportLendPeopleCallBack;

  public void setReportLendPeopleCallBack(ReportLendPeopleCallBack reportLendPeopleCallBack) {
    this.reportLendPeopleCallBack = reportLendPeopleCallBack;
  }
}
