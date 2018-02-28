package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import java.util.List;

/**
 * Created by Lei on 2018/2/6.
 */
public class MainPageFragmentAdapter
    extends RecyclerView.Adapter<MainPageFragmentAdapter.ViewHolder> {

  private List<String> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public MainPageFragmentAdapter(Context mContext, List<String> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<String> getDatas() {
    return mDatas;
  }

  public MainPageFragmentAdapter setDatas(List<String> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_main_page_adapter, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    String text = mDatas.get(position);
    holder.tvTitle.setText(text);
    holder.layoutContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mainPageFragmentAdapterCallBack.onClick(position);
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

  public void addNewAll(List<String> datas) {
    mDatas.clear();
    mDatas.addAll(datas);
    notifyDataSetChanged();
  }

  public void removeTargetItem(int position) {
    if (position < mDatas.size()) {
      mDatas.remove(position);
      notifyDataSetChanged();
    }
  }

  @Override public int getItemCount() {
    return mDatas != null ? mDatas.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_main_page) TextView tvTitle;
    @BindView(R.id.layout_item_main_page_content) LinearLayout layoutContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface MainPageFragmentAdapterCallBack {
    void onClick(int position);
  }

  private MainPageFragmentAdapterCallBack mainPageFragmentAdapterCallBack;

  public void setMainPageFragmentAdapterCallBack(
      MainPageFragmentAdapterCallBack mainPageFragmentAdapterCallBack) {
    this.mainPageFragmentAdapterCallBack = mainPageFragmentAdapterCallBack;
  }
}
