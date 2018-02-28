package com.jiajia.badou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import java.util.List;

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

  private List<String> mDatas;
  private LayoutInflater mInflater;
  private Context context;
  private String defoultText;

  public SimpleTextAdapter(Context mContext, List<String> mDatas, String text) {
    this.mDatas = mDatas;
    this.context = mContext;
    this.defoultText = text;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<String> getDatas() {
    return mDatas;
  }

  public SimpleTextAdapter setDatas(List<String> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.yq_simple_city_item, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.tvContent.setText(mDatas.get(position));
    if (position % 2 == 0) {
      holder.tvContent.setBackgroundColor(Color.WHITE);
    } else {
      holder.tvContent.setBackgroundColor(
          ContextCompat.getColor(context, R.color.yq_simpletextadapter));
    }
    if (mDatas.get(position).equals(defoultText)) {
      holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.yq_low_blue));
    } else {
      holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.yq_818d94));
    }
    holder.tvContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        simpleTextAdapterCallBack.onClick(position);
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

  public void setStr(String defoultText) {
    this.defoultText = defoultText;
    notifyDataSetChanged();
  }

  public String getStr() {
    return defoultText;
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.simple_text) TextView tvContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface SimpleTextAdapterCallBack {
    void onClick(int position);
  }

  SimpleTextAdapterCallBack simpleTextAdapterCallBack;

  public void setSimpleTextAdapterCallBack(SimpleTextAdapterCallBack simpleTextAdapterCallBack) {
    this.simpleTextAdapterCallBack = simpleTextAdapterCallBack;
  }
}
