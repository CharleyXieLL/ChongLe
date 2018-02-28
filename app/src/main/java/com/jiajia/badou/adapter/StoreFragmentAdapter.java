package com.jiajia.badou.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jiajia.badou.R;
import java.util.List;

/**
 * Created by Lei on 2018/2/7.
 */
public class StoreFragmentAdapter extends RecyclerView.Adapter<StoreFragmentAdapter.ViewHolder> {

  private List<String> mDatas;
  private LayoutInflater mInflater;
  private Context context;

  public StoreFragmentAdapter(Context mContext, List<String> mDatas) {
    this.context = mContext;
    this.mDatas = mDatas;
    mInflater = LayoutInflater.from(mContext);
  }

  public List<String> getDatas() {
    return mDatas;
  }

  public StoreFragmentAdapter setDatas(List<String> datas) {
    mDatas = datas;
    return this;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.item_store_fragment, parent, false));
  }

  @SuppressLint("SetTextI18n") @Override public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {
    String text = mDatas.get(position);
    holder.tvItemStore.setText(text);
    holder.layoutItemStoreContent.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        storeFragmentAdapterCallBack.onClick(position);
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

    @BindView(R.id.img_item_store) ImageView imgItemStore;
    @BindView(R.id.tv_item_store) TextView tvItemStore;
    @BindView(R.id.layout_item_store_content) LinearLayout layoutItemStoreContent;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public interface StoreFragmentAdapterCallBack {
    void onClick(int position);
  }

  private StoreFragmentAdapterCallBack storeFragmentAdapterCallBack;

  public void setStoreFragmentAdapterCallBack(
      StoreFragmentAdapterCallBack storeFragmentAdapterCallBack) {
    this.storeFragmentAdapterCallBack = storeFragmentAdapterCallBack;
  }
}
